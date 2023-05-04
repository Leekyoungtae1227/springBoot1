package com.study.springboottest1.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdateDTO {
    private Long id;
    private String title;
    private String content;

}
