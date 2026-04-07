package domain;

import java.time.LocalDateTime;

public class Article {
    public int id;
    public String title;
    public String content;
    public LocalDateTime regDate;
    public LocalDateTime modDate;

    public Article(int id, String title, String content, LocalDateTime regDate, LocalDateTime modDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.regDate = regDate;
        this.modDate = modDate;
    }

}
