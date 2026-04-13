package domain;

import java.time.LocalDateTime;

public class Article {
    private int id;
    private String title;
    private String content;
    private LocalDateTime regDate;
    private LocalDateTime modDate;

    public Article(int id, String title, String content, LocalDateTime regDate, LocalDateTime modDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.regDate = regDate;
        this.modDate = modDate;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
        this.modDate = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getModDate() {
        return modDate;
    }

    public void setModDate(LocalDateTime modDate) {
        this.modDate = modDate;
    }

    public LocalDateTime getRegDate() {
        return regDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
