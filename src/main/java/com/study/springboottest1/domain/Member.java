package com.study.springboottest1.domain;


import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Data
@Entity(name="Member")
@Table(name="member")
@Builder
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Member {

    @Id
    @Column(name = "member_id", length = 20, nullable=false)
    private String id;

      @Column(name = "member_password", nullable = false)
    private String password;

    @Column(name = "member_name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_role", nullable = false)
    private MemberRole role;
}
