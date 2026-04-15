package com.ll;

import java.util.Map;

// 명령어 요청 처리 클래스.
// 사용자의 명령어 문자열을 파싱하여 액션 이름과 파라미터를 추출합니다.
public class Rq {
    private final String actionName;
    private final Map<String, String> paramsMap;

    //  명령어 문자열을 파싱하여 액션 이름과 파라미터를 추출합니다.
    public Rq(String cmd) {
        String[] cmdBits = cmd.split(" ", 2);
        actionName = cmdBits[0]; // 첫 번째 비트는 액션 이름 (write, list, detail, update, delete, exit 등)

        String paramValue = cmdBits.length > 1 ? cmdBits[1].trim() : "";

        if (!paramValue.isEmpty()) {
            paramsMap = Map.of("id", paramValue);
        } else {
            paramsMap = Map.of();
        }
    }

    //액션 이름을 반환합니다.
    public String getActionName() {
        return actionName;
    }

    // 파라미터 값을 문자열로 가져옵니다.
    public String getParam(String paramName, String defaultValue) {
        if (paramsMap.containsKey(paramName)) { // 파라미터가 존재하는 경우
            return paramsMap.get(paramName); // 해당 파라미터 값을 반환
        } else {
            return defaultValue; // 기본값 반환
        }
    }

    // 파라미터 값을 정수로 가져오는 메소드 (기본값 제공)
    public int getParamAsInt(String paramName, int defaultValue) {
        String value = getParam(paramName, ""); // 문자열 형태의 파라미터 값을 가져옴

        if (value.isEmpty()) { // 값이 비어있다면 기본값을 반환
            return defaultValue;
        }

        try {
            return Integer.parseInt(value); // 문자열을 정수로 변환
        } catch (NumberFormatException e) { // 변환 실패 시 기본값 반환
            return defaultValue;
        }
    }
}
