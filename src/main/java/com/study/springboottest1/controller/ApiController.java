package com.study.springboottest1.controller;

import com.study.springboottest1.dto.LoginDTO;
import com.study.springboottest1.dto.PostFormDTO;
import com.study.springboottest1.dto.SignUpFormDTO;
import com.study.springboottest1.dto.UpdateFormDTO;
import com.study.springboottest1.service.interfaces.BoardService;
import com.study.springboottest1.service.interfaces.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

    private final MemberService memberService;
    private final BoardService boardService;


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDTO loginDTO) {
        return memberService.login(loginDTO);
    }

    @PostMapping("/signup")
    public ResponseEntity userSignup(@RequestBody SignUpFormDTO formDTO) {
        return memberService.signup(formDTO);
    }

    @ResponseBody
    @GetMapping("/name")
    public String name() {
        return "이경태";
    }

    @PostMapping("/posts")
    public ResponseEntity save(@RequestBody PostFormDTO formDTO) {
        ResponseEntity responseEntity = boardService.save(formDTO);
        return responseEntity;
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity remove(@PathVariable Long id) {
        ResponseEntity responseEntity = boardService.remove(id);
        return responseEntity;
    }

    @PatchMapping("/posts/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody UpdateFormDTO updateFormDTO) {
        ResponseEntity responseEntity = boardService.update(id, updateFormDTO);
        return responseEntity;
    }
}