package com.ll;

import java.util.List;
import java.util.Scanner;

// 텍스트 게시판 애플리케이션 메인 클래스
// 사용자의 명령어를 받아 ArticleService에 위임하고, 결과를 출력합니다.
public class App {
    private final Scanner scanner = new Scanner(System.in);
    private final ArticleService articleService = new ArticleService();

    // 앱 실행 루프 (사용자로부터 명령어를 입력받아 적절한 동작을 수행)
    public void run() {
        System.out.println("== 게시글 앱 ==");

        while (true) {
            System.out.print("명령어) ");
            String cmd = scanner.nextLine().trim();

            Rq rq = new Rq(cmd);
            if (handleCommand(rq)) {
                break;
            }
        }
        scanner.close();
    }

    // 명령어(actionName)에 따라 기능을 분기 실행하고, 종료 명령이면 true를 반환합니다.
    private boolean handleCommand(Rq rq) {
        String actionName = rq.getActionName();
        int id = rq.getParamAsInt("id", -1);

        switch (actionName) {
            case "exit" -> {
                System.out.println("프로그램을 종료합니다.");
                return true;
            }
            case "write" -> writeArticle();
            case "list" -> listArticles();
            case "search" -> searchArticles(rq.getArg());
            case "detail" -> showDetail(id);
            case "delete" -> deleteArticle(id);
            case "update" -> updateArticle(id);
            default -> {
            }
        }

        return false;
    }

    // 게시글 작성 처리 (사용자 입력을 받아 ArticleService에 위임)
    private void writeArticle() {
        String title = readTrimmedLine("제목: ");
        String content = readTrimmedLine("내용: ");

        articleService.write(title, content);
        System.out.println("=> 게시글이 등록되었습니다.");
    }

    // 프로그래밍 방식으로 게시글을 작성합니다. (테스트 및 외부 호출용)
    public Article write(String title, String content) {
        return articleService.write(title, content);
    }

    // 모든 게시글 목록 출력 (최신글이 위에 오도록 역순으로 출력)
    private void listArticles() {
        printArticleHeader();

        List<Article> articles = articleService.findAll();
        if (articles.isEmpty()) {
            System.out.println("(게시글 없음)");
            return;
        }

        printArticlesNewestFirst(articles);
    }

    // 키워드로 게시글을 검색하여 출력
    private void searchArticles(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            System.out.println("검색어를 입력해주세요.");
            return;
        }

        printArticleHeader();

        List<Article> matched = articleService.search(keyword);
        if (matched.isEmpty()) {
            System.out.println("(검색 결과 없음)");
            return;
        }

        printArticlesNewestFirst(matched);
    }

    // 목록/검색 출력용 테이블 헤더를 출력합니다.
    private void printArticleHeader() {
        System.out.println("번호 | 제목 | 등록일 | 조회수");
        System.out.println("-----------------------------");
    }

    // 전달받은 게시글 리스트를 최신글부터(역순) 한 줄씩 출력합니다.
    private void printArticlesNewestFirst(List<Article> articles) {
        for (int i = articles.size() - 1; i >= 0; i--) {
            printArticleRow(articles.get(i));
        }
    }

    // 게시글 한 건을 목록 행 포맷에 맞춰 출력합니다.
    private void printArticleRow(Article article) {
        System.out.println("%d    | %s  | %s | %d".formatted(
                article.getId(),
                article.getTitle(),
                article.getCurrentDate(),
                article.getCount()
        ));
    }

    // 특정 게시글의 상세 내용 출력
    private void showDetail(int id) {
        if (isInvalidId(id)) return;
        Article post = findByIdOrPrintMessage(id);

        if (post == null) return;

        post.increaseCount();

        System.out.println("번호: " + post.getId());
        System.out.println("제목: " + post.getTitle());
        System.out.println("내용: " + post.getContent());
        System.out.println("등록일: " + post.getCurrentDate());
        System.out.println("조회수: " + post.getCount());
    }

    // 게시글 삭제 처리
    private void deleteArticle(int id) {
        if (isInvalidId(id)) return;
        if (!articleService.deleteById(id)) {
            System.out.println("해당 아이디는 존재하지 않습니다.");
            return;
        }
        System.out.println("=> 게시글이 삭제되었습니다.");
    }

    // 게시글 수정 처리
    private void updateArticle(int id) {
        if (isInvalidId(id)) return;
        Article post = findByIdOrPrintMessage(id);

        if (post == null) return;

        String title = readTrimmedLine("제목 (현재: %s): ".formatted(post.getTitle()));
        String content = readTrimmedLine("내용 (현재: %s): ".formatted(post.getContent()));

        if (title.isEmpty() || content.isEmpty()) {
            System.out.println("제목/내용은 비어 있을 수 없습니다.");
            return;
        }

        articleService.modifyById(id, title, content);
        System.out.println("=> 게시글이 수정되었습니다.");
    }

    // id로 게시글을 조회하고, 없으면 안내 메시지를 출력한 뒤 null을 반환합니다.
    private Article findByIdOrPrintMessage(int id) {
        Article post = articleService.findById(id);

        if (post == null) {
            System.out.println("해당 아이디는 존재하지 않습니다.");
            return null;
        }

        return post;
    }

    // id가 유효하지 않으면 안내 메시지를 출력하고 true를 반환합니다.
    private boolean isInvalidId(int id) {
        if (id < 0) {
            System.out.println("id를 입력해주세요.");
            return true;
        }

        return false;
    }

    // 프롬프트를 출력한 뒤 사용자 입력을 받아 양끝 공백을 제거해 반환합니다.
    private String readTrimmedLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}
