package com.study.springboottest1.repository;


import com.study.springboottest1.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;


public interface BoardRepository extends JpaRepository<Board, Long> , QuerydslPredicateExecutor<Board> {
    List<Board> findByTitleContaining(String title);

    @Override
    Page<Board> findAll(Pageable pageable);

    Page<Board> findByTitleContaining(String title, Pageable pageable);


}
