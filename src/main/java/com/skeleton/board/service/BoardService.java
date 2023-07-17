package com.skeleton.board.service;

import com.skeleton.board.repository.BoardRepository;
import com.skeleton.board.vo.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {
    @Autowired
    BoardRepository boardRepository;

    public Board detail(long id){
        Optional<Board> boardRepositoryById = boardRepository.findById(id);
        return boardRepositoryById.orElse(null);
    }

    public List<Board> list() {
        List<Board> boards = boardRepository.findAll();

        System.out.println("boards = " + boards);

        return boards;
    }

    public Board add(Board board) {
        return boardRepository.save(board);
    }
}
