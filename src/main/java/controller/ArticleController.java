package controller;

import domain.Article;
import service.ArticleService;
import util.Rq;

import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
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

    public void showList() {
        List<Article> articles = articleService.getArticles();
        if (articles.isEmpty()) {
            System.out.println("게시글이 존재하지 않습니다.");
            return;
        }
        System.out.println("번호 | 제목 | 등록일");
        articles.forEach(a ->
                System.out.printf("%d | %s | %s\n",
                        a.getId(), a.getTitle(), a.getRegDate().format(DATE_FORMATTER)));
    }

    public void showDetail(Rq rq) {
        try {
            Article article = articleService.getArticle(rq.getId());
            String regDate = article.getRegDate().format(DATE_FORMATTER);
            String modDate = article.getModDate().format(DATE_FORMATTER);
            System.out.printf("번호: %d\n제목: %s\n내용: %s\n등록일: %s\n수정일: %s\n",
                    article.getId(), article.getTitle(), article.getContent(),regDate,modDate);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    public void doModify(Rq rq) {
        try {
            Article article = articleService.getArticle(rq.getId());
            System.out.printf("제목(기존: %s): ", article.getTitle());
            String title = sc.nextLine();
            System.out.printf("내용(기존: %s): ", article.getContent());
            String content = sc.nextLine();

            articleService.modify(rq.getId(), title, content);
            System.out.printf("%d번 게시글이 수정되었습니다.\n", rq.getId());
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    public void doDelete(Rq rq) {
        try {
            articleService.remove(rq.getId());
            System.out.printf("%d번 게시글이 삭제되었습니다.\n", rq.getId());
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    public void showHelp() {
        System.out.println("\n=== 명령어 도움말 ===");
        System.out.println("등록 : write");
        System.out.println("목록 : list");
        System.out.println("상세 : detail {id}");
        System.out.println("수정 : update {id}");
        System.out.println("삭제 : delete {id}");
        System.out.println("도움 : help");
        System.out.println("종료 : exit");
        System.out.println("====================\n");
    }
}