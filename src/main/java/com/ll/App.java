package com.ll;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class App {
    Scanner scanner = new Scanner(System.in);
    int lastId = 0;
    List<Post> postList = new ArrayList<>();

    void run() {
        System.out.println("== 게시글 앱 ==");

        while (true) {
            System.out.print("명령어) ");
            String cmd = scanner.nextLine().trim();

            if (cmd.equals("exit")) {
                System.out.println("프로그램을 종료합니다.");
                break;
            } else if (cmd.equals("write")) {
                actionWrite();
            } else if (cmd.equals("list")) {
                actionList();
            } else if (cmd.startsWith("delete ")) {
                actionDelete(cmd);
            }

        }

        scanner.close();
    }

    void actionWrite() {
        System.out.print("제목: ");
        String title = scanner.nextLine().trim();

        System.out.print("내용: ");
        String content = scanner.nextLine().trim();

        Post post = write(title, content);

        System.out.println("%d번 게시글이 등록되었습니다.".formatted(post.getId()));
    }

    Post write(String title, String content) {
        Post post = new Post(++lastId, title, content);
        postList.add(post);
        return post;
    }

    void actionList() {
        System.out.println("번호 / 제목 / 내용");
        System.out.println("----------------------");

        for (int i = postList.size() - 1; i >= 0; i--) {
            Post post = postList.get(i);
            System.out.println("%d / %s / %s".formatted(post.getId(), post.getTitle(), post.getContent()));
        }
    }

    void actionDelete(String cmd) {
        String[] cmdBits = cmd.split(" ");

        if (cmdBits.length < 2 || cmdBits[1].isEmpty()) {
            System.out.println("id를 입력해주세요.");
            return;
        }

        int id = Integer.parseInt(cmdBits[1]);

        delete(id);

        System.out.println("%d번 게시글이 삭제되었습니다.".formatted(id));
    }

    private void delete(int id) {
        Post post = null;
        for (int i = 0; i < postList.size(); i++) {
            if (postList.get(i).getId() == id) {
                post = postList.get(i);
            }
        }

        if (post == null) {
            System.out.println("해당 아이디는 존재하지 않습니다.");
            return;
        }

        postList.remove(post);
    }
}
