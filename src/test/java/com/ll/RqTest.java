package com.ll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Rq 테스트")
class RqTest {

    @Test
    @DisplayName("액션 이름을 정상적으로 파싱하는지 확인")
    void parsesActionName() {
        Rq rq = new Rq("detail 1");

        assertThat(rq.getActionName()).isEqualTo("detail");
    }

    @Test
    @DisplayName("id 파라미터를 정수로 정상 파싱하는지 확인")
    void parsesIdParamAsInt() {
        Rq rq = new Rq("delete 3");

        assertThat(rq.getParamAsInt("id", -1)).isEqualTo(3);
    }

    @Test
    @DisplayName("id가 없거나 숫자가 아니면 기본값을 반환하는지 확인")
    void returnsDefaultWhenIdIsMissingOrInvalid() {
        Rq missingIdRq = new Rq("list");
        Rq invalidIdRq = new Rq("detail abc");

        assertThat(missingIdRq.getParamAsInt("id", -1)).isEqualTo(-1);
        assertThat(invalidIdRq.getParamAsInt("id", -1)).isEqualTo(-1);
    }

    @Test
    @DisplayName("명령어 뒤 인자를 그대로 가져오는지 확인")
    void getsArg() {
        Rq rq = new Rq("search 자바");

        assertThat(rq.getArg()).isEqualTo("자바");
    }
}

