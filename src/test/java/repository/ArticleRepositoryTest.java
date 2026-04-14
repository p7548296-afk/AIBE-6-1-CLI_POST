package repository;

import domain.Article;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ArticleRepositoryTest {
    private ArticleRepository repo;

    @BeforeEach
    void setUp() {
        repo = new ArticleRepository();
    }

    @Test
    @DisplayName("새 게시글을 저장하면 ID는 1부터 시작하여 증가한다")
    void save_test() {
        int id1 = repo.save("제목1", "내용1");
        int id2 = repo.save("제목2", "내용2");

        assertThat(id1).isEqualTo(1);
        assertThat(id2).isEqualTo(2);
    }

    @DisplayName("findAll 성공 - 모든 게시글을 최신순으로 가져온다")
    @Test
    void findAll_NotEmpty() {
        // given
        repo.save("title1", "content1"); // ID: 1
        repo.save("title2", "content2"); // ID: 2
        repo.save("title3", "content3"); // ID: 3

        // when
        List<Article> list = repo.findAll();

        // then
        assertThat(list.size()).isEqualTo(3);

        // 정렬 순서 검증 (최신순이므로 첫 번째 아이템의 ID는 3이어야 함)
        assertThat(list.get(0).getId()).isEqualTo(3);
        assertThat(list.get(1).getId()).isEqualTo(2);
        assertThat(list.get(2).getId()).isEqualTo(1);
    }

    @DisplayName("findAll 성공 - 게시물이 하나도 없으면 빈 list를 내보낸다")
    @Test
    void findAll_Empty() {
        // given

        // when
        List<Article> list = repo.findAll();

        // then
        assertThat(list).isNotNull();
        assertThat(list).isEmpty();
        assertThat(list.size()).isEqualTo(0);

    }

    @DisplayName("findById - 존재하지 않는 ID 조회 시 Optional.empty() 반환")
    @Test
    void findById_fail() {
        // when
        Optional<Article> articleOp = repo.findById(999);

        // then
        assertThat(articleOp.isPresent()).isFalse();
        assertThat(articleOp).isEmpty();
    }

    @DisplayName("findById - 존재하는 ID 조회 시 객체가 담긴 Optional 반환")
    @Test
    void findById_success() {
        // given
        int id = repo.save("제목", "내용");

        // when
        Optional<Article> articleOp = repo.findById(id);

        // then
        assertThat(articleOp.isPresent()).isTrue();
        assertThat(articleOp.get().getTitle()).isEqualTo("제목");
    }

}