package controller;

import domain.Article;
import service.ArticleService;
import util.Rq;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class ArticleController {

    private final ArticleService articleService;
    private final Scanner sc;

    public ArticleController(ArticleService articleService,Scanner sc) {
        this.articleService = articleService;
        this.sc = sc;
    }

    public void writeView() {
        System.out.print("제목: ");
        String title = sc.nextLine();
        System.out.print("내용: ");
        String content = sc.nextLine();
        int id = articleService.writeArticle(title, content);
        System.out.printf("%d번 게시글이 등록되었습니다.\n", id);
    }

    public void listView() {
        List<Article> articlesList = articleService.getListArticles();

        if (articlesList.isEmpty()) {
            System.out.println("게시글이 존재하지 않습니다.");
            return;
        }

        System.out.println("번호 | 제목       | 최초 등록일 | 최근 수정일");
        System.out.println("--------------------------------------------------");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for (Article article : articlesList) {
            String regDate = article.getRegDate().format(formatter);
            String modDate = article.getRegDate().format(formatter);

            System.out.printf("%-4d | %-10s | %s | %s\n",
                    article.getId(), article.getTitle(), regDate, modDate);
        }
    }

    public void showDetailView(Rq rq) {
        int id  = rq.getId();
        Article article = articleService.findById(id);

        if (article == null) {
            System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
            return;
        }
        System.out.printf("번호: %d\n제목: %s\n내용: %s\n최초 등록일: %s\n최근 수정일: %s\n",
                article.getId(), article.getTitle(), article.getContent(), article.getRegDate(), article.getModDate());
    }

    public void updateView(Rq rq) {
        int id = rq.getId();
        Article article = articleService.findById(id);

        if (article == null) {
            System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
            return;
        }

        System.out.printf("제목(기존: %s): ", article.getTitle());
        String title = sc.nextLine();
        System.out.printf("내용(기존: %s): ", article.getContent());
        String content = sc.nextLine();

        articleService.updateArticle(article, title, content);
        System.out.printf("%d번 게시글이 수정되었습니다.\n", id);
    }

    public void deleteView(Rq rq) {
        int id = rq.getId();
        if (articleService.deleteArticle(id)) {
            System.out.printf("%d번 게시글이 삭제되었습니다.\n", id);
        } else {
            System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
        }
    }

    public void helpView() {
        System.out.println("명령어 모음");
        System.out.println("등록 : write");
        System.out.println("목록 : list");
        System.out.println("상세 : detail {id}");
        System.out.println("수정 : update {id}");
        System.out.println("삭제 : delete {id}");
        System.out.println("도움 : help");
        System.out.println("종료: exit");
    }
}
