package com.study.springboottest1.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class ListDTO {
    private Long id;
    private String title;
    private LocalDateTime createdAt;
    private int userViews;
    private int adminViews;
    private String memberName;
    private String memberId;

    public String getFormattedCreatedAt() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분");
        return createdAt.format(formatter);

    }
}
