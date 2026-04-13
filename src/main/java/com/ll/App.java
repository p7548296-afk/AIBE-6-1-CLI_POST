package com.ll;

import java.util.Scanner;

public class App {
    void run() {
        System.out.println("== 게시글 앱 ==");

        Scanner scanner = new Scanner(System.in);

        Post[] posts = new Post[100];
        int postsLastIndex = -1;

        int lastId = 0;

        while (true) {
            System.out.print("명령어) ");
            String cmd = scanner.nextLine().trim();

            if (cmd.equals("exit")) {
                System.out.println("프로그램을 종료합니다.");
                break;
            } else if (cmd.equals("write")) {
                System.out.print("제목: ");
                String postTitle = scanner.nextLine().trim();

                System.out.print("내용: ");
                String postContent = scanner.nextLine().trim();

                int id = ++lastId;

                Post post = new Post();

                post.id = id;
                post.title = postTitle;
                post.content = postContent;

                posts[++postsLastIndex] = post;

                System.out.println("%d번 게시글이 등록되었습니다.".formatted(id));
            } else if (cmd.equals("list")) {
                System.out.println("번호 / 제목 / 내용");
                System.out.println("----------------------");

                for (int i = postsLastIndex; i >=0; i--) {
                    Post post = posts[i];
                    System.out.println("%d / %s / %s".formatted(post.id, post.title, post.content));
                }
                
            } else if (cmd.equals("detail")) {
                System.out.print("게시글 번호: ");
                String id = scanner.nextLine().trim();

                System.out.println("게시글 상세 정보입니다.");
                
            } else {
                System.out.println("존재하지 않는 명령어입니다.");
            }
        }

        scanner.close();
    }
}
