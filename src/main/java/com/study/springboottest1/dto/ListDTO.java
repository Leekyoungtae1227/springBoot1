package com.study.springboottest1.dto;

import com.study.springboottest1.domain.Board;
import com.study.springboottest1.domain.Member;
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

    public ListDTO(Long id, String title, LocalDateTime createdAt, int userViews, int adminViews, String memberName, String memberId) {
        this.id = id;
        this.title = title;
        this.createdAt = createdAt;
        this.userViews = userViews;
        this.adminViews = adminViews;
        this.memberName = memberName;
        this.memberId = memberId;
    }

    public static ListDTO from(Board board) {
        Member member = board.getMember(); // 게시물의 작성자 정보 가져오기
        return new ListDTO(board.getId(), board.getTitle(), board.getCreatedAt(), board.getUserViews(), board.getAdminViews(), member.getName(), member.getId());
    }



        public String getFormattedCreatedAt() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분");
        return createdAt.format(formatter);
    }



}
