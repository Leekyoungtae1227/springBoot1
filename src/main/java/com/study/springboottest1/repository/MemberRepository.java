package com.study.springboottest1.repository;

import com.study.springboottest1.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {


}
