package service;

import domain.Article;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.ArticleRepository;

import java.util.List;

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
    @DisplayName("getArticles - 페이징 처리가 정확하게 이루어져야 한다")
    void getArticles_paging_test() {
        // given
        for (int i = 1; i <= 10; i++) {
            articleService.write("제목" + i, "내용" + i);
        }

        // when
        List<Article> page2 = articleService.getArticles(2, 3);

        // then
        assertThat(page2).hasSize(3);
        assertThat(page2.get(0).getTitle()).isEqualTo("제목7");
        assertThat(page2.get(2).getTitle()).isEqualTo("제목5");
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