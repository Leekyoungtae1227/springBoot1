package com.study.springboottest1.controller;

import com.study.springboottest1.domain.Board;
import com.study.springboottest1.dto.*;
import com.study.springboottest1.service.BoardServiceImpl;
import com.study.springboottest1.service.interfaces.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

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
    public String list(Model model, @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, @RequestParam(required = false) String title) {
        Page<Board> boardPage = boardService.getAll(title, pageable);
        List<ListDTO> posts = boardPage.getContent().stream().map(ListDTO::from).collect(Collectors.toList());
        model.addAttribute("boardList", posts);
        model.addAttribute("currentPage", boardPage.getNumber());
        model.addAttribute("totalPages", boardPage.getTotalPages());
        return "List";
    }


    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id, Model model) {
        UpdateDTO post = boardService.getUpdateDTO(id);
        model.addAttribute("post", post);
        return "update";
    }

    @GetMapping("/search")
    public ModelAndView search(BoardSearchVO searchVO, @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ListDTO> boardPage = boardService.search(searchVO, pageable);
        List<ListDTO> posts = boardPage.getContent();
        ModelAndView mav = new ModelAndView("list");
        mav.addObject("boardList", posts);
        mav.addObject("searchVO", searchVO);
        mav.addObject("currentPage", boardPage.getNumber());
        mav.addObject("totalPages", boardPage.getTotalPages());
        return mav;
    }
}