package com.ll;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// 게시글 데이터 클래스
// 게시글의 아이디, 제목, 내용, 등록일을 관리합니다.
public class Article {
    private int id;              // 게시글 번호
    private String title;        // 게시글 제목
    private String content;      // 게시글 내용
    private String regDate;      // 게시글 등록일 (yyyy-MM-dd 형식)
    private int count;           // 게시글 조회수

    // Article 생성자
    public Article(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
        // 현재 날짜를 yyyy-MM-dd 형식으로 저장
        this.regDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.count = 0;
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

    public String getCurrentDate() {
        return this.regDate;
    }

    public int getCount() {
        return this.count;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void increaseCount() {
        this.count++;
    }

}
