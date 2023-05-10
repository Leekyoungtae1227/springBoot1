package com.study.springboottest1.service;


import com.querydsl.core.BooleanBuilder;
import com.study.springboottest1.domain.Board;
import com.study.springboottest1.domain.Member;
import com.study.springboottest1.domain.MemberRole;
import com.study.springboottest1.domain.QBoard;
import com.study.springboottest1.dto.*;
import com.study.springboottest1.repository.BoardRepository;
import com.study.springboottest1.repository.MemberRepository;
import com.study.springboottest1.service.interfaces.BoardService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;



    @Override
    public ResponseEntity save(PostFormDTO formDTO) {

        Optional<Member> member = memberRepository.findById(formDTO.getMemberId());

        if (member.isPresent()) {
            Member memberEntity = member.get();

            Board post = Board.builder()
                    .title(formDTO.getTitle())
                    .content(formDTO.getContent())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .adminViews(0)
                    .userViews(0)
                    .likes(0)
                    .member(memberEntity)
                    .build();

            boardRepository.save(post);

            return new ResponseEntity("success", HttpStatus.OK);
        } else {
            return new ResponseEntity("Member not found", HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public List<ListDTO> getAll(String title) {
        ModelMapper modelMapper = new ModelMapper();
        List<Board> posts = boardRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
//        List<Board> posts = boardRepository.findAllByTitleContains(title);
        List<ListDTO> list = new ArrayList<>();


        for (Board post : posts) {
            Member member = post.getMember();

            ListDTO dto = modelMapper.map(post, ListDTO.class);

            list.add(dto);
        }

        return list;
    }

    @Override
    public DetailDTO getDetail(Long id, String memberId) {

        Optional<Board> board = boardRepository.findById(id);
        Board boardEntity = board.orElse(null);

        Member member = boardEntity.getMember();

        if (boardEntity != null) {
            Member viewer = null;
            // 조회자 ID를 이용하여 조회자 객체 생성
            if (memberId != null) {
                Optional<Member> viewerOptional = memberRepository.findById(memberId);
                viewer = viewerOptional.orElse(null);
            }
            // 조회자 객체를 이용하여 조회수 증가 처리
            if (viewer != null) {
                if (viewer.getRole().equals(MemberRole.ADMIN)) {
                    boardEntity.countAdmin();
                } else {
                    boardEntity.countUser();
                }
            } else {
                boardEntity.countUser();
            }
        }

        DetailDTO detailDTO = DetailDTO.builder()
                .id(boardEntity.getId())
                .title(boardEntity.getTitle())
                .content(boardEntity.getContent())
                .createdAt(boardEntity.getCreatedAt())
                .updatedAt(boardEntity.getUpdatedAt())
                .userViews(boardEntity.getUserViews())
                .adminViews(boardEntity.getAdminViews())
                .memberId(member.getId())
                .memberName(member.getName())
                .build();

        return detailDTO;
    }

    @Override
    public ResponseEntity remove(Long id) {
        boardRepository.deleteById(id);
        return new ResponseEntity("success", HttpStatus.OK);

    }

    @Override
    public UpdateDTO getUpdateDTO(Long id) {
        Optional<Board> board = boardRepository.findById(id);
        Board boardEntity = board.orElseGet(null);

        UpdateDTO updateDTO = UpdateDTO.builder()
                .id(boardEntity.getId())
                .title(boardEntity.getTitle())
                .content(boardEntity.getContent())
                .build();

        return updateDTO;
    }

    @Override
    public ResponseEntity update(Long id, UpdateFormDTO updateFormDTO) {

        Optional<Board> board = boardRepository.findById(id);
        Board boardEntity = board.orElseGet(null);

        boardEntity.update(updateFormDTO);

        return new ResponseEntity("success", HttpStatus.OK);
    }

    @Override
    public List<ListDTO> search(BoardSearchVO searchVO) {
        ModelMapper modelMapper = new ModelMapper();

        // QuerydslPredicateExecutor를 이용한 검색 기능 구현
        QBoard qBoard = QBoard.board;
        BooleanBuilder builder = new BooleanBuilder();

        if (StringUtils.hasText(searchVO.getTitle())) {
            builder.and(qBoard.title.containsIgnoreCase(searchVO.getTitle()));
        }

        if (StringUtils.hasText(searchVO.getMemberName())) {
            builder.and(qBoard.member.name.containsIgnoreCase(searchVO.getMemberName()));
        }

        if (StringUtils.hasText(searchVO.getMemberId())) {
            builder.and(qBoard.member.id.eq(searchVO.getMemberId()));
        }

        List<Board> boards = (List<Board>) boardRepository.findAll(builder, Sort.by(Sort.Direction.DESC, "id"));
        List<ListDTO> list = new ArrayList<>();

        for (Board board : boards) {
            Member member = board.getMember();
            ListDTO dto = modelMapper.map(board, ListDTO.class);
            dto.setMemberName(member.getName());
            dto.setMemberId(member.getId());
            list.add(dto);
        }

        return list;
    }
}
