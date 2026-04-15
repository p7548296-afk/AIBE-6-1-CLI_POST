import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class AppTest {

    @Test
    @DisplayName("전체 시나리오: 등록 -> 목록 -> 상세 -> 수정 -> 삭제")
    void t1() {
        String input = """
            write
            제목1
            내용1
            write
            제목2
            내용2
            list?page=1&pagesize=5
            detail?id=1
            update?id=2
            수정제목
            수정내용
            delete?id=1
            list?page=1
            """;

        String rs = AppTestRunner.run(input);

        System.out.println(rs);

        // 삭제 전 로그들 검증
        assertThat(rs).contains("1번 게시글이 삭제되었습니다.");

        // 삭제 명령 이후의 마지막 'list' 출력 부분만 확인하기 위해 문자열 자르기
        int lastListIndex = rs.lastIndexOf("번호 | 제목 | 등록일");
        String lastResult = rs.substring(lastListIndex);

        assertThat(lastResult).doesNotContain("1 | 제목1");
        assertThat(lastResult).contains("2 | 수정제목");

        assertThat(rs).contains("1 / 1"); // 페이지 정보가 찍혔는지 확인
        assertThat(rs).contains("2번 게시글이 수정되었습니다.");
    }

    @Test
    @DisplayName("명령어 도ㅁ움말 확인")
    void t2() {
        String rs = AppTestRunner.run("help");

        assertThat(rs)
                .contains("=== 명령어 도움말 ===")
                .contains("등록 : write")
                .contains("종료 : exit");
    }
}