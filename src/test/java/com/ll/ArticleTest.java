package com.ll;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Article 테스트")
class ArticleTest {

    @Test
    @DisplayName("제목과 내용이 올바르게 입력되었을 때 게시글이 성공적으로 생성되는지 확인")
    void createsArticleWhenInputIsValid() {
        Article article = new Article(1, "자바 공부", "자바 텍스트 게시판 만들기");

        assertNotNull(article);
        assertEquals(1, article.getId());
        assertEquals("자바 공부", article.getTitle());
        assertEquals("자바 텍스트 게시판 만들기", article.getContent());
        assertNotNull(article.getCurrentDate());
        assertEquals(0, article.getCount());
    }

    @Test
    @DisplayName("현재 날짜가 올바른 형식(yyyy-MM-dd)으로 출력되는지 확인")
    void returnsDateInExpectedFormat() {
        Article article = new Article(1, "날짜 테스트", "내용");
        String dateText = article.getCurrentDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        assertNotNull(dateText);
        assertTrue(dateText.matches("\\d{4}-\\d{2}-\\d{2}"));
        assertDoesNotThrow(() -> LocalDate.parse(dateText, formatter));
    }

    @Test
    @DisplayName("제목/내용 setter가 정상 동작하는지 확인")
    void updatesTitleAndContent() {
        Article article = new Article(1, "원래 제목", "원래 내용");

        article.setTitle("수정 제목");
        article.setContent("수정 내용");

        assertEquals("수정 제목", article.getTitle());
        assertEquals("수정 내용", article.getContent());
    }

    @Test
    @DisplayName("조회수 증가 메서드가 정상 동작하는지 확인")
    void increasesCount() {
        Article article = new Article(1, "제목", "내용");

        article.increaseCount();
        article.increaseCount();

        assertEquals(2, article.getCount());
    }
}


