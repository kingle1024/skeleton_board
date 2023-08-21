package com.skeleton.board;

import com.skeleton.board.repository.BoardRepository;
import com.skeleton.board.vo.Board;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class BoardApplicationTests {

	@Autowired
	BoardRepository boardRepository;

	@Test
	void test(){
		Long id = 1L;
		Optional<Board> result = boardRepository.findById(id);

		if(result.isPresent()) {
			Board memo = result.get();
			Assertions.assertThat(memo).isNotNull();
		}
	}
}
