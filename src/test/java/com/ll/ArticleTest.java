package com.ll;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("Article 테스트")
class ArticleTest {

    @Test
    @DisplayName("제목과 내용이 올바르게 입력되었을 때 게시글이 성공적으로 생성되는지 확인")
    void createsArticleWhenInputIsValid() {
        Article article = new Article(1, "자바 공부", "자바 텍스트 게시판 만들기");

        assertThat(article).isNotNull();
        assertThat(article.getId()).isEqualTo(1);
        assertThat(article.getTitle()).isEqualTo("자바 공부");
        assertThat(article.getContent()).isEqualTo("자바 텍스트 게시판 만들기");
        assertThat(article.getCurrentDate()).isNotNull();
        assertThat(article.getCount()).isEqualTo(0);
    }

    @Test
    @DisplayName("현재 날짜가 올바른 형식(yyyy-MM-dd)으로 출력되는지 확인")
    void returnsDateInExpectedFormat() {
        Article article = new Article(1, "날짜 테스트", "내용");
        String dateText = article.getCurrentDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        assertThat(dateText).isNotNull();
        assertThat(dateText).matches("\\d{4}-\\d{2}-\\d{2}");
        assertThatCode(() -> LocalDate.parse(dateText, formatter)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("제목/내용 setter가 정상 동작하는지 확인")
    void updatesTitleAndContent() {
        Article article = new Article(1, "원래 제목", "원래 내용");

        article.setTitle("수정 제목");
        article.setContent("수정 내용");

        assertThat(article.getTitle()).isEqualTo("수정 제목");
        assertThat(article.getContent()).isEqualTo("수정 내용");
    }

    @Test
    @DisplayName("조회수 증가 메서드가 정상 동작하는지 확인")
    void increasesCount() {
        Article article = new Article(1, "제목", "내용");

        article.increaseCount();
        article.increaseCount();

        assertThat(article.getCount()).isEqualTo(2);
    }

    @Test
    @DisplayName("키워드가 제목에 포함되면 matches()가 true를 반환하는지 확인")
    void matchesByTitle() {
        Article article = new Article(1, "자바 공부", "스프링 실습");

        assertThat(article.matches("자바")).isTrue();
        assertThat(article.matches("파이썬")).isFalse();
    }

    @Test
    @DisplayName("키워드가 내용에 포함되면 matches()가 true를 반환하는지 확인")
    void matchesByContent() {
        Article article = new Article(1, "공부 일지", "자바 텍스트 게시판 만들기");

        assertThat(article.matches("텍스트")).isTrue();
        assertThat(article.matches("Django")).isFalse();
    }
}


