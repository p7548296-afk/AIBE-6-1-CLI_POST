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
        return articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(id + "번 게시글은 존재하지 않습니다."));
    }

    public void modify(int id, String title, String content) {
        Article article = getArticle(id);
        article.update(title, content);
    }

    public void remove(int id) {
        getArticle(id);
        articleRepository.delete(id);
    }
}