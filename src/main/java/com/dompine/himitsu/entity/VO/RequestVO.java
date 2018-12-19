package com.dompine.himitsu.entity.VO;

import lombok.Data;

import java.util.List;

@Data
public class RequestVO {

    private PostVO postVO;

    private List<CommentVO> commentVO;
}
