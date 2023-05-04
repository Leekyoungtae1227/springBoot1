package com.study.springboottest1.controller;

import com.study.springboottest1.domain.Board;
import com.study.springboottest1.dto.*;
import com.study.springboottest1.service.BoardServiceImpl;
import com.study.springboottest1.service.interfaces.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class WebController {

    private final BoardService boardService;

//    @GetMapping("/")
//    public String index(Model model) {
//        List<ListDTO> posts = boardService.getAll();
//        model.addAttribute("posts", posts);
//        return "home";
//    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model, @CookieValue("id") String memberId) {
        DetailDTO post = boardService.getDetail(id, memberId);
        model.addAttribute("post", post);

        return "detail";
    }

//    @GetMapping("/hello")
//    public String hello(Model model) {
//        model.addAttribute("name", "디트로네");
//        model.addAttribute("img", "image/jj.png");
//        return "hello";
//    }

    @GetMapping("/")
    public String index(Model model, @SessionAttribute(value = "memberId", required = false) String memberId, @SessionAttribute(value = "memberRole", required = false) String memberRole) {
        model.addAttribute("name", "경태 홈페이지");
        model.addAttribute("img", "image/jj.png");
        model.addAttribute("memberId", memberId);
        model.addAttribute("memberRole", memberRole);
        return "home";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("/home")
    public String home(Model model, @SessionAttribute(value = "memberId", required = false) String memberId, @SessionAttribute(value = "memberRole", required = false) String memberRole) {
        model.addAttribute("name", "경태 홈페이지");
        model.addAttribute("img", "image/jj.png");
        model.addAttribute("memberId", memberId);
        model.addAttribute("memberRole", memberRole);
        return "home";
    }

    @GetMapping("/signin")
    public String signin() {
        return "signin";
    }

    @GetMapping("/new")
    public String newPost() {
        return "new";
    }

    @GetMapping("/list")
    public String list(Model model, @RequestParam(required = false) String title) {
        List<ListDTO> posts = boardService.getAll(title);
        model.addAttribute("posts", posts);
        return "list";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id, Model model) {
        UpdateDTO post = boardService.getUpdateDTO(id);
        model.addAttribute("post", post);
        return "update";
    }

//    @GetMapping("/search")
//    public String search(@ModelAttribute("searchVO") BoardSearchVO searchVO, Model model) {
//        List<Board> boards = boardService.searchBoards(searchVO);
//        model.addAttribute("boards", boards);
//        return "board/search";
//    }
}
