package com.ll;

public class Post {
    private int id;
    private String title;
    private String content;

    Post (int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
    int getId() {
        return this.id;
    }

    String getTitle() {
        return this.title;
    }

    String getContent() {
        return this.content;
    }
}
