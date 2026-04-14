package com.ll;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class App {
    Scanner scanner = new Scanner(System.in);
    int lastId = 0;
    List<Article> postList = new ArrayList<>();

    void run() {
        System.out.println("== 게시글 앱 ==");

        while (true) {
            System.out.print("명령어) ");
            String cmd = scanner.nextLine().trim();

            Rq rq = new Rq(cmd);
            String actionName = rq.getActionName();

            if (actionName.equals("exit")) {
                System.out.println("프로그램을 종료합니다.");
                break;
            } else if (actionName.equals("write")) {
                writeArticle();
            } else if (actionName.equals("list")) {
                listArticles();
            } else if (actionName.equals("detail")) {
                int id = rq.getParamAsInt("id", -1);
                showDetail(id);
            } else if (actionName.equals("delete")) {
                int id = rq.getParamAsInt("id", -1);
                deleteArticle(id);
            } else if (actionName.equals("update")) {
                int id = rq.getParamAsInt("id", -1);
                updateArticle(id);
            }
        }
        scanner.close();
    }


    void writeArticle() {
        System.out.print("제목: ");
        String title = scanner.nextLine().trim();

        System.out.print("내용: ");
        String content = scanner.nextLine().trim();

        Article post = write(title, content);

        System.out.println("=> 게시글이 등록되었습니다.");
    }

    Article write(String title, String content) {
        Article post = new Article(++lastId, title, content);
        postList.add(post);
        return post;
    }

    void listArticles() {
        System.out.println("번호 | 제목 | 등록일");
        System.out.println("-----------------------------");

        for (int i = postList.size() - 1; i >= 0; i--) {
            Article post = postList.get(i);
            System.out.println("%d    | %s  | %s".formatted(post.getId(), post.getTitle(), post.getCurrentDate()));
        }
    }

    void showDetail(int id) {
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

    void deleteArticle(int id) {
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

    private void delete(Article post) {
        postList.remove(post);
    }

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

    void modify(Article post, String title, String content) {
        post.setTitle(title);
        post.setContent(content);
    }


    Article findById(int id) {
        Article post = null;
        for (Article p : postList) {
            if (p.getId() == id) {
                post = p;
            }
        }

        if (post == null) {
            System.out.println("해당 아이디는 존재하지 않습니다.");
            return null;
        }

        return post;
    }


}
