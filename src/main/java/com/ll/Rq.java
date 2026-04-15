package com.ll;

import java.util.Map;

// 명령어 요청 처리 클래스.
// 사용자의 명령어 문자열을 파싱하여 액션 이름과 파라미터를 추출합니다.
public class Rq {
    private final String actionName;
    private final String arg;
    private final Map<String, String> paramsMap;

    //  명령어 문자열을 파싱하여 액션 이름과 파라미터를 추출합니다.
    public Rq(String cmd) {
        String[] cmdBits = cmd.split(" ", 2);
        actionName = cmdBits[0];

        String paramValue = cmdBits.length > 1 ? cmdBits[1].trim() : "";
        arg = paramValue;

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

    public String getArg() {
        return arg;
    }

    // 파라미터 값을 문자열로 가져옵니다.
    public String getParam(String paramName, String defaultValue) {
        if (paramsMap.containsKey(paramName)) {
            return paramsMap.get(paramName);
        } else {
            return defaultValue;
        }
    }

    // 파라미터 값을 정수로 가져오는 메소드 (기본값 제공)
    public int getParamAsInt(String paramName, int defaultValue) {
        String value = getParam(paramName, "");

        if (value.isEmpty()) {
            return defaultValue;
        }

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
