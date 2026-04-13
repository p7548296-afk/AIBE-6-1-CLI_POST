import domain.Article;
import domain.Articles;
import util.Rq;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class App {
    private final Articles articles = new Articles(); // 일급 컬렉션 주입
    private final Scanner sc = new Scanner(System.in);

    public void run() {
        System.out.println("== 자바 텍스트 게시판 시작 ==");
        helpCommand();

        while (true) {
            System.out.print("명령어: ");
            String command = sc.nextLine().trim();
            if (command.isEmpty()) continue;

            Rq rq = new Rq(command);

            switch (rq.getActionPath()) {
                case "exit" -> {
                    System.out.println("프로그램을 종료합니다.");
                    return;
                }
                case "write" -> writeArticle();
                case "list" -> listArticles();
                case "detail" -> showDetail(rq);
                case "update" -> updateArticle(rq);
                case "delete" -> deleteArticle(rq);
                case "help" -> helpCommand();
                default -> System.out.println("존재하지 않는 명령어입니다.");
            }
        }
    }

    private void writeArticle() {
        System.out.print("제목: ");
        String title = sc.nextLine();
        System.out.print("내용: ");
        String content = sc.nextLine();
        int id = articles.write(title, content);
        System.out.printf("%d번 게시글이 등록되었습니다.\n", id);
    }

    private void listArticles() {
        List<Article> articlesList = articles.findAll();

        if (articlesList.isEmpty()) {
            System.out.println("게시글이 존재하지 않습니다.");
            return;
        }

        System.out.println("번호 | 제목       | 최초 등록일 | 최근 수정일");
        System.out.println("--------------------------------------------------");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for (Article article : articlesList) {
            String regDate = article.regDate.format(formatter);
            String modDate = article.modDate.format(formatter);

            System.out.printf("%-4d | %-10s | %s | %s\n",
                    article.id, article.title, regDate, modDate);
        }
    }

    private void showDetail(Rq rq) {
        int id = rq.getId();
        Article article = articles.findById(id);

        if (article == null) {
            System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
            return;
        }
        System.out.printf("번호: %d\n제목: %s\n내용: %s\n등록일: %s\n",
                article.id, article.title, article.content, article.regDate);
    }

    private void updateArticle(Rq rq) {
        int id = rq.getId();
        Article article = articles.findById(id);

        if (article == null) {
            System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
            return;
        }

        System.out.printf("제목(기존: %s): ", article.title);
        String title = sc.nextLine();
        System.out.printf("내용(기존: %s): ", article.content);
        String content = sc.nextLine();

        articles.update(article, title, content);
        System.out.printf("%d번 게시글이 수정되었습니다.\n", id);
    }

    private void deleteArticle(Rq rq) {
        int id = rq.getId();
        if (articles.deleteById(id)) {
            System.out.printf("%d번 게시글이 삭제되었습니다.\n", id);
        } else {
            System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
        }
    }

    private void helpCommand() {
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
