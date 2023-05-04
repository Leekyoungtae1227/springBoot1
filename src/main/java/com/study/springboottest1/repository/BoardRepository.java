package com.study.springboottest1.repository;


import com.study.springboottest1.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BoardRepository extends JpaRepository<Board, Long>{
}
