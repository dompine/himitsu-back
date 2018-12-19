package com.dompine.himitsu.service;

import com.dompine.himitsu.entity.Post;
import com.dompine.himitsu.entity.User;
import com.dompine.himitsu.entity.VO.PostVO;
import com.dompine.himitsu.entity.VO.RequestVO;

import java.util.List;

public interface PostService {
    User save(String content, String userOpenid,String formId);

    PostVO getPostRandom(long userId);

    List<Post> getPostHistory(long userId);

    RequestVO getPostDetailed(long postId,long userId);
}
