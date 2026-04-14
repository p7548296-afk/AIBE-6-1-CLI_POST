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
}