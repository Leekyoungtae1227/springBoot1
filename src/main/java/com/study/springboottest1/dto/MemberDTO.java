package com.study.springboottest1.dto;

import com.study.springboottest1.domain.MemberRole;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDTO {

    private Long id;
    private String name;
    private MemberRole role;

    public MemberDTO(String id, String name, MemberRole role) {
    }
}

