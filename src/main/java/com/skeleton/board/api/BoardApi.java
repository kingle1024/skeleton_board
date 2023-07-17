package com.skeleton.board.api;

import com.skeleton.board.service.BoardService;
import com.skeleton.board.vo.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class BoardApi {
    @Autowired
    BoardService boardService;

    @GetMapping("/detail/{id}")
    public ResponseEntity<Board> detail(@PathVariable Long id) {
        Board detail = boardService.detail(id);

        if(detail == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(detail);
    }

    @GetMapping("/list")
    public Map<String, List<Board>> list() {
        Map<String, List<Board>> result = new HashMap<>();
        result.put("list", boardService.list());

        return result;
    }

    @PostMapping("/add")
    public Board add(Board board){
        return boardService.add(board);
    }
}
