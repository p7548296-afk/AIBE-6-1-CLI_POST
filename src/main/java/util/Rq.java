package util;

import lombok.Getter;
import java.util.HashMap;
import java.util.Map;

public class Rq {
    @Getter
    private final String actionPath;
    private final Map<String, String> params = new HashMap<>();

    public Rq(String command) {
        // 전체를 '?' 기준으로 나눔
        String[] commandBits = command.trim().split("\\?", 2);
        this.actionPath = commandBits[0].trim();

        if (commandBits.length == 2) {
            // '&' 기준으로 파라미터들을 나눔
            String[] paramBits = commandBits[1].split("&");
            for (String paramBit : paramBits) {
                String[] keyValue = paramBit.split("=", 2);
                if (keyValue.length == 2) {
                    params.put(keyValue[0].trim(), keyValue[1].trim());
                }
            }
        }
    }

    // 파라미터 값을 가져오되, 없으면 기본값 반환
    public String getParam(String name, String defaultValue) {
        return params.getOrDefault(name, defaultValue);
    }

    public int getIntParam(String name, int defaultValue) {
        try {
            String value = params.get(name);
            if (value == null) return defaultValue;
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}