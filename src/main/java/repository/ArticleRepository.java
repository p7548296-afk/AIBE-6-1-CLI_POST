package repository;

import domain.Article;
import java.time.LocalDateTime;
import java.util.*;

public class ArticleRepository {
    private final Map<Integer, Article> articleMap = new HashMap<>();
    private int lastId = 0;

    public int save(String title, String content) {
        int id = ++lastId;
        LocalDateTime now = LocalDateTime.now();
        articleMap.put(id, new Article(id, title, content, now, now,0));
        return id;
    }

    public List<Article> findAll() {
        List<Article> list = new ArrayList<>(articleMap.values());
        list.sort((a1, a2) -> a2.getId() - a1.getId());
        return list;
    }

    public Optional<Article> findById(int id) {
        return Optional.ofNullable(articleMap.get(id));
    }

    public void delete(int id) {
        articleMap.remove(id);
    }
}