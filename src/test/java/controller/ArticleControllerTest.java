package controller;

import org.junit.jupiter.api.*;
import repository.ArticleRepository;
import service.ArticleService;
import global.dto.Rq;
import util.TestUtil;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class ArticleControllerTest {
    private ArticleRepository repo;
    private ArticleService service;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd HH:mm");
    private ByteArrayOutputStream output;

    @BeforeEach
    void setUp() {
        repo = new ArticleRepository();
        service = new ArticleService(repo);
        output = TestUtil.setOutToByteArray();
    }

    @AfterEach
    void tearDown() {
        TestUtil.clearSetOut(output);
    }

    @Test
    @DisplayName("doWrite: 제목과 내용을 입력받아 게시글을 등록한다")
    void t1() {
        // given
        Scanner sc = TestUtil.genScanner("제목1\n내용1\n");
        ArticleController controller = new ArticleController(service, sc, formatter);

        // when
        controller.doWrite();

        // then
        String out = output.toString();
        assertThat(out).contains("1번 게시글이 등록되었습니다.");

        assertThat(service.getArticle(1).getTitle()).isEqualTo("제목1");
    }

    @Test
    @DisplayName("showList: 게시글이 있을 때 목록과 페이지 정보를 출력한다")
    void t2() {
        service.write("제목1", "내용1");
        Scanner sc = TestUtil.genScanner("");
        ArticleController controller = new ArticleController(service, sc, formatter);

        controller.showList(new Rq("list?page=1&pagesize=5"));

        String out = output.toString();
        assertThat(out).contains("번호 | 제목 | 등록일");
        assertThat(out).contains("1 | 제목1");
        assertThat(out).contains("--- 현재 페이지: 1 / 1 ---");
    }

    @Test
    @DisplayName("doModify: 기존 내용을 보여주고 수정을 진행한다")
    void t3() {
        // given
        service.write("원래 제목", "원래 내용");

        Scanner sc = TestUtil.genScanner("수정 제목\n수정 내용\n");
        ArticleController controller = new ArticleController(service, sc, formatter);

        // when
        controller.doModify(new Rq("update?id=1"));

        // then
        String out = output.toString();
        assertThat(out).contains("제목(기존: 원래 제목):");
        assertThat(out).contains("1번 게시글이 수정되었습니다.");
        assertThat(service.getArticle(1).getTitle()).isEqualTo("수정 제목");
    }

    @Test
    @DisplayName("doDelete: 삭제 성공 시 메시지를 출력한다")
    void t4() {
        // given
        service.write("삭제용", "내용");
        ArticleController controller = new ArticleController(service, new Scanner(""), formatter);

        // when
        controller.doDelete(new Rq("delete?id=1"));

        // then
        assertThat(output.toString()).contains("1번 게시글이 삭제되었습니다.");
    }

    @Test
    @DisplayName("showDetail: 게시글 상세 내용과 날짜가 정확한 포맷으로 출력되어야 한다")
    void t5() {
        // given
        service.write("상세 제목", "상세 내용");
        ArticleController controller = new ArticleController(service, new Scanner(""), formatter);

        // when
        controller.showDetail(new Rq("detail?id=1"));

        // then
        String out = output.toString();
        assertThat(out)
                .contains("번호: 1")
                .contains("제목: 상세 제목")
                .contains("내용: 상세 내용")
                .contains("조회수: 1")
                .contains("등록일: ")
                .contains("수정일: ");
    }

    @Test
    @DisplayName("실패 테스트: 존재하지 않는 게시글 번호로 상세보기를 시도하면 에러 메시지를 보여준다")
    void t6() {
        // given
        ArticleController controller = new ArticleController(service, new Scanner(""), formatter);

        // when
        controller.showDetail(new Rq("detail?id=99"));

        // then
        String out = output.toString();
        assertThat(out).contains("99번 게시글은 존재하지 않습니다.");
    }

    @Test
    @DisplayName("실패 테스트: 존재하지 않는 게시글 번호로 수정을 시도하면 에러 메시지를 보여준다")
    void t7() {
        // given
        // 수정 시 제목을 물어보기 전에 예외가 터져야 하므로 빈 입력값을 준비
        Scanner sc = TestUtil.genScanner("\n\n");
        ArticleController controller = new ArticleController(service, sc, formatter);

        // when
        controller.doModify(new Rq("update?id=99"));

        // then
        String out = output.toString();
        assertThat(out).contains("99번 게시글은 존재하지 않습니다.");
    }

    @Test
    @DisplayName("doSearch: 제목 검색 성공 시 결과 목록을 출력한다")
    void t8() {
        // given
        service.write("공지사항입니다", "내용");
        service.write("오늘의 일기", "내용");
        ArticleController controller = new ArticleController(service, new Scanner(""), formatter);

        // when
        controller.doSearch(new Rq("search?target=title&keyword=공지"));

        // then
        String out = output.toString();
        assertThat(out).contains("[title] 검색 결과");
        assertThat(out).contains("공지사항입니다");
        assertThat(out).doesNotContain("오늘의 일기");
    }

    @Test
    @DisplayName("doSearch: 검색 결과가 없을 때 안내 메시지를 출력한다")
    void t9() {
        // given
        ArticleController controller = new ArticleController(service, new Scanner(""), formatter);

        // when
        controller.doSearch(new Rq("search?target=all&keyword=갤럭시"));

        // then
        assertThat(output.toString()).contains("'갤럭시'에 대한 검색 결과가 없습니다.");
    }

    @Test
    @DisplayName("doSearch: 키워드 없이 검색 시 경고 메시지를 출력한다")
    void t10() {
        // given
        ArticleController controller = new ArticleController(service, new Scanner(""), formatter);

        // when
        controller.doSearch(new Rq("search?target=title&keyword="));

        // then
        assertThat(output.toString()).contains("검색어를 입력해주세요.");
    }

}