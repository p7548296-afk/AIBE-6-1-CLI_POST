package com.ll;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// 텍스트 게시판 애플리케이션 메인 클래스
// 사용자의 명령어를 받아 게시글 CRUD 기능을 수행합니다.

public class App {
    private final Scanner scanner = new Scanner(System.in);
    private int lastId = 0;
    private final List<Article> postList = new ArrayList<>();

    // 앱 실행 루프 (사용자로부터 명령어를 입력받아 적절한 동작을 수행)
    public void run() {
        System.out.println("== 게시글 앱 ==");

        label:
        while (true) {
            System.out.print("명령어) ");
            String cmd = scanner.nextLine().trim();

            Rq rq = new Rq(cmd);
            String actionName = rq.getActionName();

            switch (actionName) {
                case "exit":
                    System.out.println("프로그램을 종료합니다.");
                    break label;
                case "write":
                    writeArticle();
                    break;
                case "list":
                    listArticles();
                    break;
                case "detail": {
                    int id = rq.getParamAsInt("id", -1);
                    showDetail(id);
                    break;
                }
                case "delete": {
                    int id = rq.getParamAsInt("id", -1);
                    deleteArticle(id);
                    break;
                }
                case "update": {
                    int id = rq.getParamAsInt("id", -1);
                    updateArticle(id);
                    break;
                }
            }
        }
        scanner.close();
    }

    //게시글 작성 처리
    private void writeArticle() {
        String title = readTrimmedLine("제목: ");
        String content = readTrimmedLine("내용: ");

        write(title, content);
        System.out.println("=> 게시글이 등록되었습니다.");
    }

    //게시글을 리스트에 추가하고 등록된 게시글 객체를 반환합니다.
    public Article write(String title, String content) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("제목은 비어 있을 수 없습니다.");
        }

        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("내용은 비어 있을 수 없습니다.");
        }

        Article post = new Article(++lastId, title, content);
        postList.add(post);
        return post;
    }

    // 모든 게시글 목록 출력 (최신글이 위에 오도록 역순으로 출력)
    private void listArticles() {
        System.out.println("번호 | 제목 | 등록일");
        System.out.println("-----------------------------");

        if (postList.isEmpty()) {
            System.out.println("(게시글 없음)");
            return;
        }

        for (int i = postList.size() - 1; i >= 0; i--) {
            Article post = postList.get(i);
            System.out.println("%d    | %s  | %s".formatted(post.getId(), post.getTitle(), post.getCurrentDate()));
        }
    }

    // 특정 게시글의 상세 내용 출력
    private void showDetail(int id) {
        if (isInvalidId(id)) return;
        Article post = findByIdOrPrintMessage(id);

        if (post == null) {
            return;
        }

        System.out.println("번호: " + post.getId());
        System.out.println("제목: " + post.getTitle());
        System.out.println("내용: " + post.getContent());
        System.out.println("등록일: " + post.getCurrentDate());
    }

    // 게시글 삭제 처리
    private void deleteArticle(int id) {
        if (isInvalidId(id)) return;
        Article post = findByIdOrPrintMessage(id);

        if (post == null) {
            return;
        }

        delete(post);
        System.out.println("=> 게시글이 삭제되었습니다.");
    }

    // 게시글을 리스트에서 제거
    private void delete(Article post) {
        postList.remove(post);
    }

    // 게시글 수정 처리
    private void updateArticle(int id) {
        if (isInvalidId(id)) return;
        Article post = findByIdOrPrintMessage(id);

        if (post == null) {
            return;
        }

        String title = readTrimmedLine("제목 (현재: %s): ".formatted(post.getTitle()));
        String content = readTrimmedLine("내용 (현재: %s): ".formatted(post.getContent()));

        if (title.isEmpty() || content.isEmpty()) {
            System.out.println("제목/내용은 비어 있을 수 없습니다.");
            return;
        }

        modify(post, title, content);
        System.out.println("=> 게시글이 수정되었습니다.");
    }

    // 게시글 정보 수정
    private void modify(Article post, String title, String content) {
        post.setTitle(title);
        post.setContent(content);
    }

    // 아이디로 게시글 조회
    private Article findById(int id) {
        for (Article p : postList) {
            if (p.getId() == id) {
                return p;
            }
        }

        return null;
    }

    private Article findByIdOrPrintMessage(int id) {
        Article post = findById(id);

        if (post == null) {
            System.out.println("해당 아이디는 존재하지 않습니다.");
            return null;
        }

        return post;
    }

    private boolean isInvalidId(int id) {
        if (id < 0) {
            System.out.println("id를 입력해주세요.");
            return true;
        }

        return false;
    }

    private String readTrimmedLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

}

