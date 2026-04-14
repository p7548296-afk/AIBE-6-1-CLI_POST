package util;

import java.io.*;
import java.util.Scanner;

public class TestUtil {
    // 입력을 시뮬레이션하기 위해 System.in을 교체
    public static Scanner genScanner(String input) {
        InputStream in = new ByteArrayInputStream(input.getBytes());
        return new Scanner(in);
    }

    // 출력을 가로채기 위해 System.out을 교체
    public static ByteArrayOutputStream setOutToByteArray() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        return out;
    }

    // 테스트가 끝나면 다시 원래 콘솔로 복구
    public static void clearSetOut(ByteArrayOutputStream out) {
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
    }
}