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
            }
        }

        scanner.close();
    }

    void actionWrite() {
        System.out.print("제목: ");
        String postTitle = scanner.nextLine().trim();

        System.out.print("내용: ");
        String postContent = scanner.nextLine().trim();

        int id = ++lastId;

        Post post = new Post();

        post.id = id;
        post.title = postTitle;
        post.content = postContent;

        postList.add(post);

        System.out.println("%d번 게시글이 등록되었습니다.".formatted(id));
    }

    void actionList() {
        System.out.println("번호 / 제목 / 내용");
        System.out.println("----------------------");

        for (int i = postList.size()-1; i >= 0; i--) {
            Post post = postList.get(i);
            System.out.println("%d / %s / %s".formatted(post.id, post.title, post.content));
        }
    }

}
