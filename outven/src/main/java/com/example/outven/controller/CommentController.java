package com.example.outven.controller;

import com.example.outven.entity.Board_comment;
import com.example.outven.entity.Member;
import com.example.outven.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class CommentController {

    @Autowired
    private BoardService boardService;

    @PostMapping("/board/commentWrite")
    public String commentWrite(@RequestParam int board_num,
                               @RequestParam String comment_content,
                               @RequestParam(required = false) String category,
                               @RequestParam(required = false, defaultValue = "0") int page,
                               @RequestParam(required = false) Integer parent_comment_num,
                               HttpServletRequest request) {
        HttpSession session = request.getSession();
        Member member = (Member) session.getAttribute("member");

        if (member == null) {
            return "redirect:/loginForm"; // 비로그인 시 로그인 페이지로
        }

        Board_comment comment = new Board_comment();
        
        if (parent_comment_num != null) {
            comment.setParentCommentNum(parent_comment_num);
        }
        
        comment.setBoardNum(board_num);
        comment.setComment_content(comment_content);
        comment.setMember_id(member.getMemberId());
        comment.setNick_name(member.getNickName());
        comment.setComment_logtime(LocalDateTime.now());

        boardService.saveComment(comment);

        return "redirect:/board/view?board_num=" + board_num + "&category=" + category + "&page=" + page;
    }
    
    @GetMapping("/board/commentDelete")
    public String commentDelete(@RequestParam int comment_num,
                                @RequestParam int board_num,
                                @RequestParam String category,
                                @RequestParam(defaultValue = "0") int page,
                                HttpServletRequest request) {

        HttpSession session = request.getSession();
        Member member = (Member) session.getAttribute("member");

        if (member == null || !boardService.isCommentAuthor(comment_num, member.getMemberId())) {
            return "redirect:/error/unauthorized"; // 권한 없음 페이지 또는 메시지 처리
        }

        boardService.deleteComment(comment_num);
        return "redirect:/board/view?board_num=" + board_num + "&category=" + category + "&page=" + page;
    }

    @GetMapping("/board/commentRecommend")
    public String recommendComment(@RequestParam int comment_num,
                                   @RequestParam String member_id,
                                   @RequestParam int board_num,
                                   @RequestParam String category,
                                   @RequestParam(defaultValue = "0") int page) {
        boardService.recommendComment(comment_num, member_id);
        return "redirect:/board/view?board_num=" + board_num + "&category=" + category + "&page=" + page;
    }

}
