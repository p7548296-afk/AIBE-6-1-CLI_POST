package service;

import domain.Article;
import repository.ArticleRepository;
import global.dto.Page;
import global.dto.Pageable;

import java.util.List;

public class ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public int write(String title, String content) {
        return articleRepository.save(title, content);
    }

    public Page<Article> getArticles(Pageable pageable) {
        List<Article> allArticles = articleRepository.findAll();
        int totalCount = allArticles.size();

        int totalPages = (totalCount == 0) ? 1 : (int) Math.ceil((double) totalCount / pageable.getPageSize());

        int fromIndex = pageable.getOffset();

        if (fromIndex >= totalCount) {
            return new Page<>(List.of(), pageable.getPage(), totalPages);
        }

        int toIndex = Math.min(fromIndex + pageable.getPageSize(), totalCount);
        List<Article> content = allArticles.subList(fromIndex, toIndex);

        return new Page<>(content, pageable.getPage(), totalPages);
    }

    public int getTotalPage(int pageSize) {
        int totalCount = articleRepository.findAll().size();
        if (totalCount == 0) return 1;

        return (int) Math.ceil((double) totalCount / pageSize);
    }

    // 상세보기용(조회수 증가)
    public Article getArticleWithIncreaseCount(int id) {
        Article article = getArticle(id);
        article.addCount();
        return article;
    }

    public void modify(int id, String title, String content) {
        Article article = getArticle(id);
        article.update(title, content);
    }

    public void remove(int id) {
        getArticle(id);
        articleRepository.delete(id);
    }

    public Article getArticle(int id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(id + "번 게시글은 존재하지 않습니다."));
    }

}