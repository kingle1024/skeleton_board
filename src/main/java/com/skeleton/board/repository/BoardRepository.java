package com.skeleton.board.repository;

import com.skeleton.board.vo.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
