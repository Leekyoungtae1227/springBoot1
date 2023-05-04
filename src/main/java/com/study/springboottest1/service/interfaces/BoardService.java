package com.study.springboottest1.service.interfaces;

import com.study.springboottest1.domain.Board;
import com.study.springboottest1.dto.*;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface BoardService {

    ResponseEntity save(PostFormDTO formDTO);
    List<ListDTO> getAll(String title);
    DetailDTO getDetail(Long id, String memberId);
    ResponseEntity remove(Long id);
    UpdateDTO getUpdateDTO(Long id);
    ResponseEntity update(Long id, UpdateFormDTO updateFormDTO);

}
