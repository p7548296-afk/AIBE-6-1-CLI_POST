package service;

import domain.Article;
import repository.ArticleRepository;
import java.util.List;

public class ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public int write(String title, String content) {
        return articleRepository.save(title, content);
    }

    public List<Article> getArticles() {
        return articleRepository.findAll();
    }

    public Article getArticle(int id) {
        Article article = articleRepository.findById(id);
        if (article == null) {
            throw new RuntimeException(id + "번 게시글은 존재하지 않습니다.");
        }
        return article;
    }

    public void modify(int id, String title, String content) {
        Article article = getArticle(id); // 존재 여부 체크 포함
        article.setTitle(title);
        article.setContent(content);
        article.setModDate(java.time.LocalDateTime.now());
    }

    public void remove(int id) {
        getArticle(id); // 존재 여부 체크 (없으면 예외 발생)
        articleRepository.delete(id);
    }
}