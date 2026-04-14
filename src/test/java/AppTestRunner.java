import util.TestUtil;
import java.io.ByteArrayOutputStream;
import java.util.Scanner;

public class AppTestRunner {
    public static String run(String input) {
        // 1. 입력 설정
        Scanner sc = TestUtil.genScanner(input.trim() + "\nexit\n");
        ByteArrayOutputStream output = TestUtil.setOutToByteArray();

        // 2. 실행 (이제 App 내부에서 Container.init(sc)를 호출하므로 가짜 스캐너가 잘 전파됨)
        new App(sc).run();

        String result = output.toString();

        // 3. 자원 회수
        TestUtil.clearSetOut(output);

        return result;
    }
}