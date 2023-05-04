package com.study.springboottest1.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {

    private String id;
    private String password;

    public LoginDTO() {
    }

    public LoginDTO(String id, String password) {
        this.id = id;
        this.password = password;
    }
}
