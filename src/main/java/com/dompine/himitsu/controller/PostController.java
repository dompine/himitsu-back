package com.dompine.himitsu.controller;


import com.dompine.himitsu.dao.PostDao;
import com.dompine.himitsu.dao.TodayReadDao;
import com.dompine.himitsu.dao.UserDao;
import com.dompine.himitsu.entity.Comment;
import com.dompine.himitsu.entity.Post;
import com.dompine.himitsu.entity.TodayRead;
import com.dompine.himitsu.entity.User;
import com.dompine.himitsu.entity.VO.PostVO;
import com.dompine.himitsu.entity.VO.RequestVO;
import com.dompine.himitsu.service.CommentService;
import com.dompine.himitsu.service.PostService;
import com.dompine.himitsu.util.WeixinCommenUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private CommentService commentService;
    @Autowired
    private WeixinCommenUtil weixinCommenUtil;

    @Autowired
    private PostDao postDao;
    @Autowired
    private TodayReadDao todayReadDao;



    //上传帖子API
    @RequestMapping(value = "submit", method = RequestMethod.POST)
    @ResponseBody
    public User saveContent(@RequestParam("content") String content, @RequestParam("userOpenid") String userOpenid,@RequestParam("formId") String formId) {
        return postService.save(content,userOpenid,formId);
    }

    //随机get帖子API
    @RequestMapping(value = "post")
    @ResponseBody
    public PostVO getPost(@RequestParam("userId")long userId) {
      return postService.getPostRandom(userId);
    }

    //回复帖子API
    @RequestMapping(value = "commentSubmit")
    @ResponseBody
    public String commentSubmit(@RequestParam("comments")String comments,@RequestParam("userId")long userId,@RequestParam("postId")long postId){
        User user = userDao.findById(userId).get();
        if(user.getCommentTimes()<=0){
            return "failed";
        }
        Comment comment = new Comment();
        comment.setComments(comments);
        comment.setUserId(userId);
        comment.setPostId(postId);
        commentService.save(comment);
        user.setCommentTimes(user.getCommentTimes()-1);
        userDao.save(user);
       //向被回复用户发送模板信息
        Post post =postDao.findById(postId).get();
        User toUser = userDao.findById(post.getUserId()).get();
        weixinCommenUtil.sendTMSG(toUser.getUserOpenid(),post.getFormId(),post.getContents());

        return "success";
    }

    //历史回复帖子API
    @RequestMapping(value = "commentHistory")
    @ResponseBody
    public List<Post> commentHistory(@RequestParam("userId")long userId){
       List<Post> posts = commentService.getCommentHistory(userId);
        return posts;
    }

    //历史发布帖子API
    @RequestMapping(value = "postHistory")
    @ResponseBody
    public List<Post> postHistory(@RequestParam("userId")long userId){
        List<Post> posts = postService.getPostHistory(userId);
        return posts;
    }

    //查看帖子详情API
    @RequestMapping(value = "getPostDetailed")
    @ResponseBody
    public RequestVO getPostDetailed(@RequestParam("postId")long postId,@RequestParam("userId")long userId){
        return postService.getPostDetailed(postId,userId);
    }
    //查看今日帖子
    @RequestMapping(value = "todayRead")
    @ResponseBody
    public List<Post> todayRead(@RequestParam("userId")long userId){
       List<TodayRead> trs = todayReadDao.findAllByUserId(userId);
       List<Post> posts =new ArrayList<>();
       for(TodayRead tr : trs){
           posts.add(postDao.findById(tr.getPostId()).get());
       }
       return posts;
    }
}
