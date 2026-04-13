package repository;

import domain.Article;

import java.time.LocalDateTime;
import java.util.*;

public class ArticleReposiorty {
    private final Map<Integer, Article> articleMap = new HashMap<>();
    private int lastId = 0;

    public int write(String title, String content) {
        int id = ++lastId;
        LocalDateTime now = LocalDateTime.now();
        articleMap.put(id, new Article(id, title, content, now, now));
        return id;
    }

    public List<Article> findAll() {
        List<Article> list = new ArrayList<>(articleMap.values());
        list.sort((a1, a2) -> a2.id - a1.id);
        return list;
    }

    public Article findById(int id) {
        return articleMap.get(id);
    }

    public boolean deleteById(int id) {
        return articleMap.remove(id) != null;
    }

    public void update(Article article, String title, String content) {
        article.title = title;
        article.content = content;
        article.modDate = LocalDateTime.now();
    }
}