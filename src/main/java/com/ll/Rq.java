package com.ll;

import java.util.Map;

/**
 * 명령어 요청 처리 클래스
 * 사용자의 명령어 문자열을 파싱하여 액션 이름과 파라미터를 추출합니다.
 * 
 * 예시:
 * - "write" -> actionName: "write", params: {}
 * - "detail 1" -> actionName: "detail", params: {id: "1"}
 * - "update 5" -> actionName: "update", params: {id: "5"}
 */
public class Rq {
    private final String actionName;              // 요청의 액션 이름 (명령어)
    private final Map<String, String> paramsMap;  // 요청의 파라미터를 저장하는 맵

    /**
     * Rq 생성자
     * 명령어 문자열을 파싱하여 액션 이름과 파라미터를 추출합니다.
     * 
     * @param cmd 사용자가 입력한 명령어 문자열 (예: "detail 1")
     */
    public Rq(String cmd) {
        // cmd를 공백으로 분리하여 액션 이름과 파라미터를 추출
        String[] cmdBits = cmd.split(" ", 2);
        actionName = cmdBits[0]; // 첫 번째 비트는 액션 이름 (write, list, detail, update, delete, exit 등)

        // 파라미터가 있으면 ID를 파라미터로 저장
        String paramValue = cmdBits.length > 1 ? cmdBits[1].trim() : "";

        // 간단한 파라미터 맵 생성 (id 파라미터만 사용)
        if (!paramValue.isEmpty()) {
            paramsMap = Map.of("id", paramValue);
        } else {
            paramsMap = Map.of();
        }
    }

    /**
     * 액션 이름을 반환합니다.
     * @return 명령어 (write, list, detail, update, delete, exit 등)
     */
    public String getActionName() {
        return actionName;
    }

    /**
     * 파라미터 값을 문자열로 가져옵니다.
     * @param paramName 파라미터 이름 (예: "id")
     * @param defaultValue 파라미터가 없을 때 반환할 기본값
     * @return 파라미터 값 또는 기본값
     */
    public String getParam(String paramName, String defaultValue) {
        if (paramsMap.containsKey(paramName)) {
            return paramsMap.get(paramName);
        } else {
            return defaultValue;
        }
    }

    /**
     * 파라미터 값을 정수로 가져옵니다.
     * @param paramName 파라미터 이름 (예: "id")
     * @param defaultValue 파라미터가 없거나 변환 실패 시 반환할 기본값
     * @return 정수로 변환된 파라미터 값 또는 기본값
     */
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
