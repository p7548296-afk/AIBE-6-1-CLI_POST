package com.ll;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("App 테스트")
class AppTest {

    private App app;

    @BeforeEach
    void setUp() {
        app = new App();
    }

    // 콘솔 입력(lines)을 주입해 App을 실행하고 출력 문자열을 반환하는 테스트 헬퍼
    private String runAppWithInput(String... lines) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outputBuffer = new ByteArrayOutputStream();

        try {
            String input = String.join(System.lineSeparator(), lines) + System.lineSeparator();
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(outputBuffer, true, StandardCharsets.UTF_8));

            App consoleApp = new App();
            consoleApp.run();

            return outputBuffer.toString(StandardCharsets.UTF_8);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
    }


    // 1. 게시글 작성 (writeArticle)테스트 케이스
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

    // 2. 게시글 목록 출력 (listArticles) 테스트 케이스
    @Nested
    @DisplayName("게시글 목록 출력(listArticles) 테스트")
    class ListArticlesTest {

        @Test
        @DisplayName("게시글이 없을 때 빈 목록이 출력된다")
        void showsEmptyMessageWhenNoArticles() {
            String output = runAppWithInput(
                    "list",
                    "exit"
            );

            assertTrue(output.contains("번호 | 제목 | 등록일"));
            assertTrue(output.contains("(게시글 없음)"));
        }

        @Test
        @DisplayName("여러 게시글이 등록되면 목록이 올바르게 출력된다")
        void showsAllArticlesInList() {
            String output = runAppWithInput(
                    "write",
                    "첫번째 제목",
                    "첫번째 내용",
                    "write",
                    "두번째 제목",
                    "두번째 내용",
                    "list",
                    "exit"
            );

            assertTrue(output.contains("1    | 첫번째 제목"));
            assertTrue(output.contains("2    | 두번째 제목"));
        }

        @Test
        @DisplayName("최신 게시글이 목록 상단에 위치한다")
        void latestArticleAppearsFirst() {
            String output = runAppWithInput(
                    "write",
                    "첫번째 제목",
                    "첫번째 내용",
                    "write",
                    "두번째 제목",
                    "두번째 내용",
                    "list",
                    "exit"
            );

            int secondIndex = output.indexOf("2    | 두번째 제목");
            int firstIndex = output.indexOf("1    | 첫번째 제목");

            assertTrue(secondIndex >= 0);
            assertTrue(firstIndex >= 0);
            assertTrue(secondIndex < firstIndex);
        }
    }
}

