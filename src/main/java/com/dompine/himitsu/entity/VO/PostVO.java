package com.dompine.himitsu.entity.VO;


import com.dompine.himitsu.entity.Post;
import com.dompine.himitsu.entity.User;
import lombok.Data;



@Data
public class PostVO {

    private Post post;

    private User user;
}
