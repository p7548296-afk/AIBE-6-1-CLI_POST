package com.ll;

import java.util.ArrayList;
import java.util.List;

// 게시글 비즈니스 로직 클래스
// 게시글의 생성, 조회, 수정, 삭제 및 검색 기능을 담당합니다.
public class ArticleService {
    private int lastId = 0;
    private final List<Article> postList = new ArrayList<>();

    // 게시글을 생성하고 리스트에 추가합니다.
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

    // 전체 게시글 목록을 반환합니다.
    public List<Article> findAll() {
        return postList;
    }

    // id로 게시글을 조회합니다. 없으면 null을 반환합니다.
    public Article findById(int id) {
        for (Article post : postList) {
            if (post.getId() == id) {
                return post;
            }
        }
        return null;
    }

    // 게시글을 삭제합니다.
    public void delete(Article post) {
        postList.remove(post);
    }

    // 게시글의 제목과 내용을 수정합니다.
    public void modify(Article post, String title, String content) {
        post.setTitle(title);
        post.setContent(content);
    }

    // 키워드로 게시글을 검색합니다. (제목 또는 내용에 키워드가 포함된 게시글 반환)
    public List<Article> search(String keyword) {
        List<Article> matched = new ArrayList<>();
        for (Article post : postList) {
            if (post.matches(keyword)) {
                matched.add(post);
            }
        }
        return matched;
    }
}

