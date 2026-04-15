package service;

import domain.Article;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.ArticleRepository;
import global.dto.Page;
import global.dto.Pageable;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ArticleServiceTest {
    private ArticleService articleService;
    private ArticleRepository articleRepository;

    @BeforeEach
    void setUp() {
        articleRepository = new ArticleRepository();
        articleService = new ArticleService(articleRepository);
    }

    @Test
    @DisplayName("write를 통해 게시글을 저장하고 생성된 ID를 반환받는다")
    void write_test() {
        // when
        int id = articleService.write("제목", "내용");
        // then
        assertThat(id).isEqualTo(1);
    }

    @Test
    @DisplayName("getArticle - 게시글 조회 성공")
    void getArticle_success_test() {
        // given
        int id = articleService.write("제목", "내용");

        // when
        Article article = articleService.getArticle(id);

        // then
        assertThat(article).isNotNull();
        assertThat(article.getId()).isEqualTo(id);
        assertThat(article.getTitle()).isEqualTo("제목");
    }

    @Test
    @DisplayName("존재하지 않는 게시글 ID로 조회하면 RuntimeException이 발생한다")
    void getArticle_fail_test() {
        // when & then
        assertThatThrownBy(() -> articleService.getArticle(99))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("99번 게시글은 존재하지 않습니다.");
    }

    @Test
    @DisplayName("게시글 수정 시 제목과 내용이 변경되고 수정일이 갱신되어야 한다")
    void modify_test() {
        // given
        int id = articleService.write("제목1", "내용1");
        Article originalArticle = articleService.getArticle(id);

        // when
        articleService.modify(id, "수정제목", "수정내용");

        // then
        Article modifiedArticle = articleService.getArticle(id);
        assertThat(originalArticle.getId()).isEqualTo(modifiedArticle.getId());
        assertThat(modifiedArticle.getTitle()).isEqualTo("수정제목");
        assertThat(modifiedArticle.getContent()).isEqualTo("수정내용");
        assertThat(modifiedArticle.getModDate()).isAfterOrEqualTo(modifiedArticle.getRegDate());
    }

    @Test
    @DisplayName("삭제 기능 호출 시 리포지토리에서 데이터가 삭제되어야 한다")
    void remove_test() {
        // given
        int id = articleService.write("삭제될 제목", "삭제될 내용");

        // when
        articleService.remove(id);

        // then
        // 삭제 후 조회하면 예외가 발생하는지로 성공 여부 확인
        assertThatThrownBy(() -> articleService.getArticle(id))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("remove - 없는 게시물을 삭제하려 하면 예외가 발생한다")
    void remove_fail_test() {
        // when & then
        // remove 내부의 getArticle()이 예외를 던지는지 확인
        assertThatThrownBy(() -> articleService.remove(999))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("999번 게시글은 존재하지 않습니다.");
    }

    @Test
    @DisplayName("getArticles - Pageable을 이용한 페이징 처리가 정확하게 이루어져야 한다")
    void getArticles_paging_test() {
        // given
        for (int i = 1; i <= 10; i++) {
            articleService.write("제목" + i, "내용" + i);
        }

        // 2페이지, 페이지당 3개씩 요청 (인덱스: 3, 4, 5)
        // 최신순 정렬 시 ID: 10,9,8(1p) / 7,6,5(2p) / 4,3,2(3p) / 1(4p)
        Pageable pageable = new Pageable(2, 3);

        // when
        Page<Article> articlePage = articleService.getArticles(pageable);

        // then
        assertThat(articlePage.getContent()).hasSize(3);
        assertThat(articlePage.getContent().get(0).getTitle()).isEqualTo("제목7");
        assertThat(articlePage.getContent().get(2).getTitle()).isEqualTo("제목5");
        assertThat(articlePage.getCurrentPage()).isEqualTo(2);
        assertThat(articlePage.getTotalPages()).isEqualTo(4); // 10개 글, 페이지당 3개 -> 총 4페이지
    }

    @Test
    @DisplayName("getArticles - 데이터가 없는 페이지 요청 시 빈 리스트와 올바른 페이지 정보를 반환한다")
    void getArticles_empty_page_test() {
        // given
        for (int i = 1; i <= 5; i++) articleService.write("제목", "내용");
        Pageable pageable = new Pageable(2, 5);

        // when
        Page<Article> articlePage = articleService.getArticles(pageable);

        // then
        assertThat(articlePage.getContent()).isEmpty();
        assertThat(articlePage.getTotalPages()).isEqualTo(1);
        assertThat(articlePage.getCurrentPage()).isEqualTo(2);
    }

    @Test
    @DisplayName("getTotalPage - 전체 페이지 수가 올바르게 계산되어야 한다")
    void getTotalPage_test() {
        // given
        for (int i = 1; i <= 11; i++) articleService.write("제목", "내용");

        // when & then
        assertThat(articleService.getTotalPage(5)).isEqualTo(3);
    }
}