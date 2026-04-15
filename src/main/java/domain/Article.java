package domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Article {
    private int id;
    private String title;
    private String content;
    private LocalDateTime regDate;
    private LocalDateTime modDate;


    public void update(String title, String content) {
        this.title = title;
        this.content = content;
        this.modDate = LocalDateTime.now();
    }


}
