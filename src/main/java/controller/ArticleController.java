package controller;

import domain.Article;
import service.ArticleService;
import global.dto.Page;
import global.dto.Pageable;
import global.dto.Rq;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ArticleController {
    private final ArticleService articleService;
    private final Scanner sc;
    private final DateTimeFormatter DATE_FORMATTER;

    public ArticleController(ArticleService articleService, Scanner sc,DateTimeFormatter DATE_FORMATTER) {
        this.articleService = articleService;
        this.sc = sc;
        this.DATE_FORMATTER = DATE_FORMATTER;
    }

    public void doWrite() {
        System.out.print("제목: ");
        String title = sc.nextLine();
        System.out.print("내용: ");
        String content = sc.nextLine();
        int id = articleService.write(title, content);
        System.out.printf("%d번 게시글이 등록되었습니다.\n", id);
    }

    public void showList(Rq rq) {
        Pageable pageable = rq.getPageable(5);
        Page<Article> articlePage = articleService.getArticles(pageable);

        if (articlePage.getContent().isEmpty()) {
            System.out.println("해당 페이지에 게시글이 없습니다.");
            return;
        }

        System.out.println("번호 | 제목 | 등록일");
        articlePage.getContent().forEach(a ->
                System.out.printf("%d | %s | %s\n",
                        a.getId(), a.getTitle(), a.getRegDate().format(DATE_FORMATTER)));

        System.out.printf("--- 현재 페이지: %d / %d ---\n",
                articlePage.getCurrentPage(), articlePage.getTotalPages());

    }

    public void showDetail(Rq rq) {
        try {
            Article article = articleService.getArticleWithIncreaseCount(rq.getIntParam("id", 0));
            String regDate = article.getRegDate().format(DATE_FORMATTER);
            String modDate = article.getModDate().format(DATE_FORMATTER);
            System.out.printf("번호: %d\n제목: %s\n내용: %s\n조회수: %d\n등록일: %s\n수정일: %s\n",
                    article.getId(), article.getTitle(), article.getContent(),article.getCount(),regDate,modDate);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    public void doModify(Rq rq) {
        try {
            int id = rq.getIntParam("id", 0);
            Article article = articleService.getArticle(id);
            System.out.printf("제목(기존: %s): ", article.getTitle());
            String title = sc.nextLine();
            System.out.printf("내용(기존: %s): ", article.getContent());
            String content = sc.nextLine();

            articleService.modify(id, title, content);
            System.out.printf("%d번 게시글이 수정되었습니다.\n", id);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    public void doDelete(Rq rq) {
        try {
            int id = rq.getIntParam("id", 0);
            articleService.remove(id);
            System.out.printf("%d번 게시글이 삭제되었습니다.\n", id);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    public void showHelp() {
        System.out.println("\n=== 명령어 도움말 ===");
        System.out.println("등록 : write");
        System.out.println("목록 : list?page=1&pagesize=5");
        System.out.println("상세 : detail?id=1");
        System.out.println("수정 : update?id=1");
        System.out.println("삭제 : delete?id=1");
        System.out.println("도움 : help");
        System.out.println("종료 : exit");
        System.out.println("====================\n");
    }
}