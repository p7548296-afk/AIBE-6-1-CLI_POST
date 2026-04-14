package com.ll;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 텍스트 게시판 애플리케이션 메인 클래스
 * 사용자의 명령어를 받아 게시글 CRUD 기능을 수행합니다.
 * 명령어:
 * - write: 새 게시글 작성
 * - list: 모든 게시글 목록 표시 (최신글이 위)
 * - detail [id]: 특정 게시글 상세보기
 * - update [id]: 특정 게시글 수정
 * - delete [id]: 특정 게시글 삭제
 * - exit: 프로그램 종료
 */

public class App {
    private Scanner scanner = new Scanner(System.in);
    private int lastId = 0;
    private List<Article> postList = new ArrayList<>();

    /**
     * 앱 실행 루프
     * 사용자로부터 명령어를 입력받아 적절한 동작을 수행합니다.
     */
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

    /**
     * 게시글 작성 처리
     * 사용자로부터 제목과 내용을 입력받아 새 게시글을 생성합니다.
     */
    private void writeArticle() {
        System.out.print("제목: ");
        String title = scanner.nextLine().trim();

        System.out.print("내용: ");
        String content = scanner.nextLine().trim();

        write(title, content);
        System.out.println("=> 게시글이 등록되었습니다.");
    }

    /**
     * 게시글을 리스트에 추가
     *
     * @param title   게시글 제목
     * @param content 게시글 내용
     * @return 생성된 Article 객체
     */
    private Article write(String title, String content) {
        Article post = new Article(++lastId, title, content);
        postList.add(post);
        return post;
    }

    /**
     * 모든 게시글 목록 출력
     * 최신글이 위에 오도록 역순으로 출력합니다.
     */
    private void listArticles() {
        System.out.println("번호 | 제목 | 등록일");
        System.out.println("-----------------------------");

        for (int i = postList.size() - 1; i >= 0; i--) {
            Article post = postList.get(i);
            System.out.println("%d    | %s  | %s".formatted(post.getId(), post.getTitle(), post.getCurrentDate()));
        }
    }

    /**
     * 특정 게시글의 상세 내용 출력
     *
     * @param id 조회할 게시글의 아이디
     */
    private void showDetail(int id) {
        if (id < 0) {
            System.out.println("id를 입력해주세요.");
            return;
        }

        Article post = findById(id);

        if (post == null) {
            return;
        }

        System.out.println("번호: " + post.getId());
        System.out.println("제목: " + post.getTitle());
        System.out.println("내용: " + post.getContent());
        System.out.println("등록일: " + post.getCurrentDate());
    }

    /**
     * 게시글 삭제 처리
     *
     * @param id 삭제할 게시글의 아이디
     */
    private void deleteArticle(int id) {
        if (id < 0) {
            System.out.println("id를 입력해주세요.");
            return;
        }

        Article post = findById(id);

        if (post == null) {
            return;
        }

        delete(post);
        System.out.println("=> 게시글이 삭제되었습니다.");
    }

    /**
     * 게시글을 리스트에서 제거
     *
     * @param post 삭제할 Article 객체
     */
    private void delete(Article post) {
        postList.remove(post);
    }

    /**
     * 게시글 수정 처리
     * 사용자로부터 새로운 제목과 내용을 입력받아 게시글을 수정합니다.
     *
     * @param id 수정할 게시글의 아이디
     */
    private void updateArticle(int id) {
        if (id < 0) {
            System.out.println("id를 입력해주세요.");
            return;
        }

        Article post = findById(id);

        if (post == null) {
            return;
        }

        System.out.printf("제목 (현재: %s): ", post.getTitle());
        String title = scanner.nextLine().trim();

        System.out.printf("내용 (현재: %s): ", post.getContent());
        String content = scanner.nextLine().trim();

        modify(post, title, content);
        System.out.println("=> 게시글이 수정되었습니다.");
    }

    /**
     * 게시글 정보 수정
     *
     * @param post    수정할 Article 객체
     * @param title   새로운 제목
     * @param content 새로운 내용
     */
    private void modify(Article post, String title, String content) {
        post.setTitle(title);
        post.setContent(content);
    }

    /**
     * 아이디로 게시글 조회
     *
     * @param id 조회할 게시글의 아이디
     * @return 찾은 Article 객체, 없으면 null
     */
    private Article findById(int id) {
        Article post = null;
        for (Article p : postList) {
            if (p.getId() == id) {
                post = p;
                break;
            }
        }

        if (post == null) {
            System.out.println("해당 아이디는 존재하지 않습니다.");
            return null;
        }

        return post;
    }

}
