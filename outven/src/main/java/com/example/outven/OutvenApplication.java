package com.example.outven;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import com.example.outven.entity.Board;
import com.example.outven.repository.BoardRepository;

@SpringBootApplication
public class OutvenApplication extends SpringBootServletInitializer {
	
	@Bean
	CommandLineRunner init(BoardRepository boardRepository) {
	    return args -> {
	        if (boardRepository.count() == 0) { // 이미 있으면 생략
	            for (int i = 1; i <= 10; i++) {
	                Board board = new Board();
	                board.setBoardTitle("테스트 게시글 " + i);
	                board.setBoardContent("Redis 캐시 실험용");
	                board.setBoardCategory("자유게시판");
	                board.setBoardRecommend(i);  // 추천수
	                board.setBoardHit(100 - i);     // 조회수

	                boardRepository.save(board);
	            }
	        }
	    };
	}


    public static void main(String[] args) {
        SpringApplication.run(OutvenApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(OutvenApplication.class);
    }
}
