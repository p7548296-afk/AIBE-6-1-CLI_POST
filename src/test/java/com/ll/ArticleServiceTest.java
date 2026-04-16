package com.ll;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("ArticleService 테스트")
class ArticleServiceTest {

    private ArticleService service;

    @BeforeEach
    void setUp() {
        service = new ArticleService();
    }

    @Nested
    @DisplayName("write() 테스트")
    class WriteTest {

        @Test
        @DisplayName("제목과 내용을 입력하면 게시글이 생성되고 id가 1부터 증가하는지 확인")
        void createsArticleWithIncrementingId() {
            Article first = service.write("제목1", "내용1");
            Article second = service.write("제목2", "내용2");

            assertThat(first.getId()).isEqualTo(1);
            assertThat(second.getId()).isEqualTo(2);
        }

        @Test
        @DisplayName("제목이 빈 문자열이면 예외가 발생하는지 확인")
        void throwsWhenTitleIsEmpty() {
            assertThatThrownBy(() -> service.write("", "내용"))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("내용이 빈 문자열이면 예외가 발생하는지 확인")
        void throwsWhenContentIsEmpty() {
            assertThatThrownBy(() -> service.write("제목", ""))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("findById() 테스트")
    class FindByIdTest {

        @Test
        @DisplayName("존재하는 id로 조회하면 해당 게시글을 반환하는지 확인")
        void returnsArticleWhenIdExists() {
            service.write("자바 공부", "내용");

            Article found = service.findById(1);

            assertThat(found).isNotNull();
            assertThat(found.getTitle()).isEqualTo("자바 공부");
        }

        @Test
        @DisplayName("존재하지 않는 id로 조회하면 null을 반환하는지 확인")
        void returnsNullWhenIdDoesNotExist() {
            assertThat(service.findById(999)).isNull();
        }
    }

    @Nested
    @DisplayName("delete() 테스트")
    class DeleteTest {

        @Test
        @DisplayName("게시글 삭제 후 findAll()에서 제거되는지 확인")
        void removesArticleFromList() {
            Article post = service.write("삭제할 글", "내용");
            boolean deleted = service.deleteById(post.getId());

            assertThat(deleted).isTrue();
            assertThat(service.findAll()).isEmpty();
        }

        @Test
        @DisplayName("존재하지 않는 id 삭제 시 false를 반환하는지 확인")
        void returnsFalseWhenDeleteIdDoesNotExist() {
            boolean deleted = service.deleteById(999);

            assertThat(deleted).isFalse();
        }
    }

    @Nested
    @DisplayName("modify() 테스트")
    class ModifyTest {

        @Test
        @DisplayName("수정 후 제목과 내용이 변경되는지 확인")
        void updatesTitleAndContent() {
            Article post = service.write("원래 제목", "원래 내용");
            boolean modified = service.modifyById(post.getId(), "수정 제목", "수정 내용");

            assertThat(modified).isTrue();
            assertThat(post.getTitle()).isEqualTo("수정 제목");
            assertThat(post.getContent()).isEqualTo("수정 내용");
        }

        @Test
        @DisplayName("존재하지 않는 id 수정 시 false를 반환하는지 확인")
        void returnsFalseWhenModifyIdDoesNotExist() {
            boolean modified = service.modifyById(999, "수정 제목", "수정 내용");

            assertThat(modified).isFalse();
        }
    }

    @Test
    @DisplayName("findAll()로 받은 리스트는 수정할 수 없는지 확인")
    void findAllReturnsUnmodifiableList() {
        service.write("제목", "내용");

        List<Article> posts = service.findAll();

        assertThatThrownBy(() -> posts.add(new Article(999, "외부 추가", "내용")))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Nested
    @DisplayName("search() 테스트")
    class SearchTest {

        @Test
        @DisplayName("키워드가 제목에 포함된 게시글만 반환하는지 확인")
        void returnsArticlesMatchingByTitle() {
            service.write("자바 공부", "컬렉션 학습");
            service.write("스프링 공부", "DI 학습");

            List<Article> result = service.search("자바");

            assertThat(result).hasSize(1);
            assertThat(result.get(0).getTitle()).isEqualTo("자바 공부");
        }

        @Test
        @DisplayName("키워드가 내용에 포함된 게시글도 반환하는지 확인")
        void returnsArticlesMatchingByContent() {
            service.write("공부 일지", "자바 백엔드 개발");
            service.write("취미 기록", "독서");

            List<Article> result = service.search("자바");

            assertThat(result).hasSize(1);
            assertThat(result.get(0).getTitle()).isEqualTo("공부 일지");
        }

        @Test
        @DisplayName("키워드가 없으면 빈 리스트를 반환하는지 확인")
        void returnsEmptyListWhenNoMatch() {
            service.write("자바 공부", "내용");

            List<Article> result = service.search("파이썬");

            assertThat(result).isEmpty();
        }
    }
}

