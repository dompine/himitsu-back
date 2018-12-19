package com.dompine.himitsu.service;

import com.dompine.himitsu.dao.*;
import com.dompine.himitsu.entity.*;
import com.dompine.himitsu.entity.VO.CommentVO;
import com.dompine.himitsu.entity.VO.PostVO;
import com.dompine.himitsu.entity.VO.RequestVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostDao postDao;

    @Autowired
    private HasReadDao hasReadDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CommentDao commentDao;
    @Autowired
    private UserService userService;
    @Autowired
    private TodayReadDao todayReadDao;

    @Override
    public User save(String content,String userOpenid,String formId) {
        Post post = new Post();
        post.setContents(content);
        post.setFormId(formId);
        User user = userDao.findByUserOpenid(userOpenid).get();
        if(user.getSendTimes()<=0){       //检查发送次数
            return user;
        }
        post.setUserId(user.getUserId());
        postDao.save(post);        //帖子入库
        user.setSendTimes(user.getSendTimes()-1);    //每次写剩余发送次数减1
        user = userService.getTickets(user,2);
        userDao.save(user);
        return user;
    }

    @Override
    public PostVO getPostRandom(long userId) {
        long max = postDao.count();
        long random = 0;
        /*三个条件其中一个为真进入循环：
          1，已读。
          2，post不存在
          3，帖子为自己发的
         */
        do {
            random = (long) (Math.ceil(Math.random() * max));
        }
        while (this.findByUserIdAndPostId(userId, random) || !postDao.findById(random).isPresent() || postDao.findById(random).get().getUserId() == userId );
        User user = userDao.findById(userId).get();
        user.setTimes(user.getTimes() - 1);
        userDao.save(user);
        //操作read表
        HasRead hasRead = new HasRead();
        hasRead.setUserId(userId);
        hasRead.setPostId(random);
        hasReadDao.save(hasRead);
        //装填今日read表
        TodayRead todayRead = new TodayRead();
        todayRead.setUserId(userId);
        todayRead.setPostId(random);
        todayReadDao.save(todayRead);
        //装填postvo
        PostVO postVO =new PostVO();
        Post post = postDao.findById(random).get();
        postVO.setPost(post);
        User pUser = userDao.findById(post.getUserId()).get();
        postVO.setUser(pUser);
        return postVO;
    }

    public Boolean findByUserIdAndPostId(long userId, long postId) {
        return hasReadDao.findByUserIdAndPostId(userId, postId).isPresent();
    }

    @Override
    public List<Post> getPostHistory(long userId) {
        List<Post> posts = postDao.findAllByUserIdOrderByCreateTimeDesc(userId);
        return posts;
    }

    @Override
    public RequestVO getPostDetailed(long postId,long userId) {
        RequestVO requestVO = new RequestVO();
        PostVO postVO = new PostVO();
        List<CommentVO> commentVOS = new ArrayList<>();
        //组装postVO
        Post post = postDao.findById(postId).get();
        User user = userDao.findById(post.getUserId()).get();
        postVO.setPost(post);
        postVO.setUser(user);
        requestVO.setPostVO(postVO);
        //根据postId在评论表查询该post下的评论
        List<Comment> comments = commentDao.findAllByPostIdOrderByCreateTimeDesc(postId);
        if(!comments.isEmpty()) {   //不为空
            for(Comment c : comments){
                if(c.getUserId()==userId) {//如果其中有一个评论是自己发的，那么只拼装该条回复（我回复的只能看到自己回复的）
                    CommentVO commentVO = new CommentVO();
                    commentVO.setComment(c);
                    commentVO.setUser(userDao.findById(c.getUserId()).get());
                    commentVOS.add(commentVO);
                    //拼装commentVO
                    requestVO.setCommentVO(commentVOS);
                    return requestVO;
                }
            }
                for (Comment c : comments) {  //没有自己发的回复则是我发布的，则显示该post下所有回复
                    CommentVO commentVO = new CommentVO();
                    commentVO.setComment(c);
                    commentVO.setUser(userDao.findById(c.getUserId()).get());
                    commentVOS.add(commentVO);
                }
        }else {   //没有评论直接拼装
            requestVO.setCommentVO(commentVOS);
        }
        //拼装commentVO
        requestVO.setCommentVO(commentVOS);
        return requestVO;
    }


}
