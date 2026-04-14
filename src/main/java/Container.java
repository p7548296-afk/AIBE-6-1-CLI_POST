

import controller.ArticleController;
import repository.ArticleRepository;
import service.ArticleService;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;


public class Container {
    private static Scanner sc;
    private static ArticleRepository articleRepository;
    private static ArticleService articleService;
    private static ArticleController articleController;

    public static void init(Scanner scanner) {
        sc = scanner;
        articleRepository = new ArticleRepository();
        articleService = new ArticleService(articleRepository);
        articleController = new ArticleController(articleService,sc,DATE_FORMATTER);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            scClose();
            System.out.println("\n스캐너는 닫았으니 메모리 누수는 걱정 말라");
        }));
    }

    public static void scClose() {
        if (sc != null) {
            sc.close();
        }
    }

    public static ArticleController getArticleController() {
        return articleController;
    }

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yy-MM-dd HH:mm");

}
