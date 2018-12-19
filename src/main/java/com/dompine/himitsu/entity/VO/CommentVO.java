package com.dompine.himitsu.entity.VO;

import com.dompine.himitsu.entity.Comment;
import com.dompine.himitsu.entity.User;
import lombok.Data;

@Data
public class CommentVO {

    private Comment comment;

    private User user;
}
