package domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Articles {
    private final List<Article> list = new ArrayList<>();
    private int lastId = 0;

    public int write(String title, String content) {
        int id = ++lastId;
        LocalDateTime now = LocalDateTime.now();
        list.add(new Article(id, title, content, now , now));
        return id;
    }

    public List<Article> findAll() {
        return list;
    }
}
