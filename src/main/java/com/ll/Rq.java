package com.ll;

import java.util.Map;

public class Rq {
    // actionName: 요청의 액션 이름을 저장
    private final String actionName;
    // paramsMap: 쿼리 파라미터를 저장하는 맵
    private final Map<String, String> paramsMap;

    public Rq(String cmd) {
        // cmd를 공백으로 분리하여 액션 이름과 파라미터를 추출
        String[] cmdBits = cmd.split(" ", 2);
        actionName = cmdBits[0]; // 첫 번째 비트는 액션 이름 (write, list, detail, update, delete 등)

        // 파라미터가 있으면 ID를 파라미터로 저장
        String paramValue = cmdBits.length > 1 ? cmdBits[1].trim() : "";

        // 간단한 파라미터 맵 생성 (id 파라미터만 사용)
        if (!paramValue.isEmpty()) {
            paramsMap = Map.of("id", paramValue);
        } else {
            paramsMap = Map.of();
        }
    }

    // 액션 이름을 반환하는 메소드
    public String getActionName() {
        return actionName;
    }

    // 파라미터 값을 가져오는 메소드 (기본값 제공)
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
