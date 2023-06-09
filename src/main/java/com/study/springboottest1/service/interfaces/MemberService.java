package com.study.springboottest1.service.interfaces;

import com.study.springboottest1.dto.LoginDTO;
import com.study.springboottest1.dto.SignUpFormDTO;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpSession;

public interface MemberService {
    ResponseEntity signup(SignUpFormDTO formDTO);
    ResponseEntity login(LoginDTO loginDTO, HttpSession httpSession);
}
