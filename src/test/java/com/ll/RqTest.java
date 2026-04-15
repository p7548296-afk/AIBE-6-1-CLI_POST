package com.ll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Rq 테스트")
class RqTest {

    @Test
    @DisplayName("액션 이름을 정상적으로 파싱하는지 확인")
    void parsesActionName() {
        Rq rq = new Rq("detail 1");

        assertEquals("detail", rq.getActionName());
    }

    @Test
    @DisplayName("id 파라미터를 정수로 정상 파싱하는지 확인")
    void parsesIdParamAsInt() {
        Rq rq = new Rq("delete 3");

        assertEquals(3, rq.getParamAsInt("id", -1));
    }

    @Test
    @DisplayName("id가 없거나 숫자가 아니면 기본값을 반환하는지 확인")
    void returnsDefaultWhenIdIsMissingOrInvalid() {
        Rq missingIdRq = new Rq("list");
        Rq invalidIdRq = new Rq("detail abc");

        assertEquals(-1, missingIdRq.getParamAsInt("id", -1));
        assertEquals(-1, invalidIdRq.getParamAsInt("id", -1));
    }
}

