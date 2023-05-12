package com.study.springboottest1.service;

import com.study.springboottest1.domain.Member;
import com.study.springboottest1.domain.MemberRole;
import com.study.springboottest1.dto.LoginDTO;
import com.study.springboottest1.dto.MemberDTO;
import com.study.springboottest1.dto.SignUpFormDTO;
import com.study.springboottest1.repository.MemberRepository;
import com.study.springboottest1.service.interfaces.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServicelmpl implements MemberService {

    private final MemberRepository memberRepository;

    public ResponseEntity signup(SignUpFormDTO formDTO) {

        Optional<Member> member = memberRepository.findById(formDTO.getId());

        if (member.isPresent()) {
            return new ResponseEntity("fail", HttpStatus.OK);
        }

        Member newMember = Member.builder()
                .id(formDTO.getId())
                .password(formDTO.getPassword())
                .name(formDTO.getName())
                .role(MemberRole.USER)
                .build();

        memberRepository.save(newMember);
        return new ResponseEntity("success", HttpStatus.OK);
    }

    @Override
    public ResponseEntity login(LoginDTO loginDTO, HttpSession session) {

        Optional<Member> member = memberRepository.findById(loginDTO.getId());
        Member memberEntity = member.orElse(null);

        if (memberEntity == null) {
            return new ResponseEntity("해당 아이디를 가진 회원이 존재하지 않습니다.", HttpStatus.OK);
        }

        if (memberEntity.getPassword().equals(loginDTO.getPassword())) {
            session.setAttribute("memberId", memberEntity.getId());
            session.setAttribute("memberRole", memberEntity.getRole().name());
            return new ResponseEntity("success", HttpStatus.OK);
        } else {
            return new ResponseEntity("비밀번호가 일치하지 않습니다.", HttpStatus.OK);
        }

    }
}
