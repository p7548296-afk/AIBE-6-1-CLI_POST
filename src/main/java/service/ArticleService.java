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

    public List<Article> getArticles(int page, int pageSize) {
        List<Article> allArticles = articleRepository.findAll();

        int fromIndex = (page - 1) * pageSize;

        if (fromIndex >= allArticles.size()) {
            System.out.println("해당 페이지에 게시물이 없습니다");
            return List.of();
        }

        int toIndex = Math.min(fromIndex + pageSize, allArticles.size());

        return allArticles.subList(fromIndex, toIndex);
    }

    public int getTotalPage(int pageSize) {
        int totalCount = articleRepository.findAll().size();
        if (totalCount == 0) return 1;

        return (int) Math.ceil((double) totalCount / pageSize);
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