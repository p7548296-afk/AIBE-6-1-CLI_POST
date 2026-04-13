package service;

import domain.Article;
import repository.ArticleRepository;

import java.util.List;

public class ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public int writeArticle(String title, String content) {
        return articleRepository.write(title, content);
    }

    public List<Article> getListArticles() {
        return articleRepository.findAll();
    }

    public Article findById(int id) {
        return articleRepository.findById(id);
    }

    public void updateArticle(Article article, String title, String content) {
        articleRepository.update(article, title, content);
    }

    public boolean deleteArticle(int id) {
        return articleRepository.deleteById(id);
    }

}
