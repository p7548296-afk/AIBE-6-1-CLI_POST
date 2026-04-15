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

import static org.junit.jupiter.api.Assertions.assertFalse;
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

    // 중복 코드 리펙토링
    private String[] writeCommand(String title, String content) {
        return new String[]{"write", title, content};
    }

    private String runWithTwoArticlesThen(String... tailCommands) {
        String[] first = writeCommand("첫번째 제목", "첫번째 내용");
        String[] second = writeCommand("두번째 제목", "두번째 내용");
        String[] all = new String[first.length + second.length + tailCommands.length + 1];

        int idx = 0;
        for (String cmd : first) all[idx++] = cmd;
        for (String cmd : second) all[idx++] = cmd;
        for (String cmd : tailCommands) all[idx++] = cmd;
        all[idx] = "exit";

        return runAppWithInput(all);
    }

    private void assertContainsAll(String output, String... expectedTexts) {
        for (String expected : expectedTexts) {
            assertTrue(output.contains(expected));
        }
    }

    // --------------------------------------
    // 테스트 케이스
    // --------------------------------------
    @Nested
    @DisplayName("게시글 목록 출력(listArticles) 테스트")
    class ListArticlesTest {

        @Test
        @DisplayName("게시글이 없을 때 빈 목록이 출력되는지 확인")
        void showsEmptyMessageWhenNoArticles() {
            String output = runAppWithInput("list", "exit");

            assertTrue(output.contains("번호 | 제목 | 등록일"));
            assertTrue(output.contains("(게시글 없음)"));
        }

        @Test
        @DisplayName("여러 게시글이 등록되었을 때, 목록이 올바르게 출력되는지 확인")
        void showsAllArticlesInList() {
            String output = runWithTwoArticlesThen("list");

            assertContainsAll(output, "1    | 첫번째 제목", "2    | 두번째 제목");
        }

        @Test
        @DisplayName("최신 게시글이 목록의 상단에 위치하는지 확인")
        void latestArticleAppearsFirst() {
            String output = runWithTwoArticlesThen("list");

            int secondIndex = output.indexOf("2    | 두번째 제목");
            int firstIndex = output.indexOf("1    | 첫번째 제목");

            assertTrue(secondIndex >= 0);
            assertTrue(firstIndex >= 0);
            assertTrue(secondIndex < firstIndex);
        }
    }

    @Nested
    @DisplayName("게시글 상세보기(showDetail) 테스트 케이스")
    class ShowDetailTest {

        @Test
        @DisplayName("존재하지 않는 ID로 상세보기를 요청할 때 예외가 발생하는지 확인")
        void showsErrorMessageWhenIdDoesNotExist() {
            String output = runAppWithInput("detail 999", "exit");

            assertTrue(output.contains("해당 아이디는 존재하지 않습니다."));
            assertFalse(output.contains("번호: 999"));
        }

        @Test
        @DisplayName("올바른 ID로 요청 시 게시글 내용이 정확하게 출력되는지 확인")
        void showsDetailWhenIdIsValid() {
            String output = runAppWithInput("write", "자바 공부", "자바 텍스트 게시판 만들기", "detail 1", "exit");

            assertContainsAll(output,
                    "번호: 1",
                    "제목: 자바 공부",
                    "내용: 자바 텍스트 게시판 만들기",
                    "등록일:");
        }
    }

    @Nested
    @DisplayName("게시글 수정(updateArticle) 테스트")
    class UpdateArticleTest {

        @Test
        @DisplayName("존재하지 않는 ID로 수정 요청 시 예외 상황 메시지가 출력되는지 확인")
        void showsErrorMessageWhenUpdateIdDoesNotExist() {
            String output = runAppWithInput("update 999", "exit");

            assertTrue(output.contains("해당 아이디는 존재하지 않습니다."));
            assertFalse(output.contains("=> 게시글이 수정되었습니다."));
        }

        @Test
        @DisplayName("제목과 내용을 수정할 때, 수정된 내용이 올바르게 반영되는지 확인")
        void updatesTitleAndContentCorrectly() {
            String output = runAppWithInput(
                    "write", "원래 제목", "원래 내용",
                    "update 1", "수정 제목", "수정 내용",
                    "detail 1",
                    "exit"
            );

            assertContainsAll(output,
                    "=> 게시글이 수정되었습니다.",
                    "제목: 수정 제목",
                    "내용: 수정 내용");
        }

        @Test
        @DisplayName("제목 또는 내용이 비어있을 때 수정이 불가능한지 확인")
        void cannotUpdateWhenTitleOrContentIsEmpty() {
            String emptyTitleOutput = runAppWithInput(
                    "write", "원래 제목", "원래 내용",
                    "update 1", "", "수정 내용",
                    "detail 1",
                    "exit"
            );

            assertContainsAll(emptyTitleOutput,
                    "제목/내용은 비어 있을 수 없습니다.",
                    "제목: 원래 제목",
                    "내용: 원래 내용");

            String emptyContentOutput = runAppWithInput(
                    "write", "원래 제목", "원래 내용",
                    "update 1", "수정 제목", "",
                    "detail 1",
                    "exit"
            );

            assertContainsAll(emptyContentOutput,
                    "제목/내용은 비어 있을 수 없습니다.",
                    "제목: 원래 제목",
                    "내용: 원래 내용");
        }
    }

    @Nested
    @DisplayName("게시글 삭제(deleteArticle) 테스트")
    class DeleteArticleTest {

        @Test
        @DisplayName("존재하지 않는 ID로 삭제 요청 시 예외 상황 메시지가 출력되는지 확인")
        void showsErrorMessageWhenDeleteIdDoesNotExist() {
            String output = runAppWithInput("delete 999", "exit");

            assertTrue(output.contains("해당 아이디는 존재하지 않습니다."));
            assertFalse(output.contains("=> 게시글이 삭제되었습니다."));
        }

        @Test
        @DisplayName("게시글이 삭제된 후 목록에서 해당 게시글이 없는지 확인")
        void deletedArticleDoesNotAppearInList() {
            String output = runWithTwoArticlesThen("delete 1", "list");

            assertContainsAll(output, "=> 게시글이 삭제되었습니다.", "2    | 두번째 제목");
            assertFalse(output.contains("1    | 첫번째 제목"));
        }
    }



}

