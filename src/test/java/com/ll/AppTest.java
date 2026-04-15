package com.ll;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("App 테스트")
class AppTest {

    private App app;

    @BeforeEach
    void setUp() {
        app = new App();
    }

    @Nested
    @DisplayName("게시글 작성(writeArticle) BLUE 케이스")
    class WriteArticleBlueTest {

        @Test
        @DisplayName("제목/내용이 올바르면 게시글이 생성된다")
        void createsArticleWhenInputIsValid() {
            Article article = app.write("자바 공부", "자바 텍스트 게시판 만들기");

            assertNotNull(article);
            assertEquals(1, article.getId());
            assertEquals("자바 공부", article.getTitle());
            assertEquals("자바 텍스트 게시판 만들기", article.getContent());
            assertNotNull(article.getCurrentDate());
        }

        @Test
        @DisplayName("제목 또는 내용이 비어 있으면 예외가 발생한다")
        void throwsExceptionWhenTitleOrContentIsEmpty() {
            assertThrows(IllegalArgumentException.class, () -> app.write("", "정상 내용"));
            assertThrows(IllegalArgumentException.class, () -> app.write("정상 제목", ""));
            assertThrows(IllegalArgumentException.class, () -> app.write("   ", "정상 내용"));
            assertThrows(IllegalArgumentException.class, () -> app.write("정상 제목", "   "));
        }

        @Test
        @DisplayName("게시글 ID는 자동으로 1씩 증가한다")
        void incrementsIdAutomatically() {
            Article article1 = app.write("제목1", "내용1");
            Article article2 = app.write("제목2", "내용2");
            Article article3 = app.write("제목3", "내용3");

            assertEquals(1, article1.getId());
            assertEquals(2, article2.getId());
            assertEquals(3, article3.getId());
        }
    }
}

