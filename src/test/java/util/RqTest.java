package util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class RqTest {

    @Test
    @DisplayName("경로와 단일 파라미터(id) 추출 테스트")
    void t1() {
        Rq rq = new Rq("detail?id=1");
        assertThat(rq.getActionPath()).isEqualTo("detail");
        assertThat(rq.getIntParam("id", 0)).isEqualTo(1);
    }

    @Test
    @DisplayName("복합 파라미터(page, pageSize, order) 추출 테스트")
    void t2() {
        Rq rq = new Rq("list?page=2&pagesize=10&order=reverse");
        assertThat(rq.getActionPath()).isEqualTo("list");
        assertThat(rq.getIntParam("page", 1)).isEqualTo(2);
        assertThat(rq.getIntParam("pagesize", 5)).isEqualTo(10);
        assertThat(rq.getParam("order", "default")).isEqualTo("reverse");
    }

    @Test
    @DisplayName("파라미터가 없을 때 기본값을 잘 반환하는지 테스트")
    void t3() {
        Rq rq = new Rq("list");
        assertThat(rq.getActionPath()).isEqualTo("list");
        assertThat(rq.getIntParam("page", 1)).isEqualTo(1);
        assertThat(rq.getParam("order", "reverse")).isEqualTo("reverse");
    }

    @Test
    @DisplayName("잘못된 형식의 파라미터(문자 입력 등) 시 기본값 반환 테스트")
    void t4() {
        Rq rq = new Rq("list?page=abc&id=999999999999999");
        // 숫자가 아니거나 범위를 벗어나면 기본값 반환
        assertThat(rq.getIntParam("page", 1)).isEqualTo(1);
        assertThat(rq.getIntParam("id", 0)).isEqualTo(0);
    }

    @Test
    @DisplayName("URL에 공백이 섞여있어도 경로를 잘 추출해야 한다")
    void t5() {
        Rq rq = new Rq("  list?page=1  ");
        assertThat(rq.getActionPath()).isEqualTo("list");
        assertThat(rq.getIntParam("page", 0)).isEqualTo(1);
    }

    @Test
    @DisplayName("파라미터 값에 공백이 포함된 경우 처리")
    void t6() {
        Rq rq = new Rq("list?search=  java  ");
        assertThat(rq.getParam("search", "")).isEqualTo("java");
    }

    @Test
    @DisplayName("경로만 있고 물음표만 찍힌 경우 에러가 나지 않아야 한다")
    void t7() {
        Rq rq = new Rq("list?");
        assertThat(rq.getActionPath()).isEqualTo("list");
        assertThat(rq.getIntParam("page", 1)).isEqualTo(1);
    }
}