package com.example.outven.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.outven.repository.CommentRecommendRepository;

@RestController
public class CommentRestController {

    @Autowired
    private CommentRecommendRepository commentRecommendRepository;

    @PostMapping("/checkCommentRecommend")
    public boolean checkCommentRecommend(@RequestParam int comment_num,
                                         @RequestParam String member_id) {
        return commentRecommendRepository.existsByCommentNumAndMemberId(comment_num, member_id);
    }
}
