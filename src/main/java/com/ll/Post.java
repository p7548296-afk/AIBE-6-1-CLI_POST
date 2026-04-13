package com.ll;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Post {
    private int id;
    private String title;
    private String content;
    private String createDate;

    Post (int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
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

    String getCreateDate() {
        return this.createDate;
    }

    void setId(int id) {
        this.id = id;
    }

    void setTitle(String title) {
        this.title = title;
    }

    void  setContent(String content) {
        this.content = content;
    }

}
