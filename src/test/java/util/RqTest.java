package util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RqTest {
    @Test
    @DisplayName("명령어에서 경로와 ID를 잘 추출한다")
    void t1() {
        Rq rq = new Rq("detail 1");
        assertThat(rq.getActionPath()).isEqualTo("detail");
        assertThat(rq.getId()).isEqualTo(1);
    }

    @Test
    @DisplayName("ID가 없는 명령어의 경우 ID는 0을 반환한다")
    void t2() {
        Rq rq = new Rq("list");
        assertThat(rq.getActionPath()).isEqualTo("list");
        assertThat(rq.getId()).isEqualTo(0);
    }

    @Test
    @DisplayName("ID 자리에 문자가 들어와도 프로그램이 터지지 않고 0을 반환한다")
    void t3() {
        Rq rq = new Rq("detail abc");
        assertThat(rq.getId()).isEqualTo(0);
    }
    @Test
    @DisplayName("공백이 섞여있어도 경로를 잘 추출해야 한다")
    void t4() {
        Rq rq = new Rq("  detail  1  ");
        assertThat(rq.getActionPath()).isEqualTo("detail");
        assertThat(rq.getId()).isEqualTo(1);
    }

    @Test
    @DisplayName("숫자가 너무 크면 id는 0이 되어야 한다 (int 범위 초과)")
    void t5() {
        Rq rq = new Rq("detail 99999999999999999");
        assertThat(rq.getId()).isEqualTo(0);
    }

    @Test
    @DisplayName("명령어 없이 공백만 들어왔을 때 에러가 나지 않아야 한다")
    void t6() {
        Rq rq = new Rq("   ");
        assertThat(rq.getActionPath()).isNotNull();
    }
}