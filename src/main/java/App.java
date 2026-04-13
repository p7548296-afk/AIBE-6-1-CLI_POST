import controller.ArticleController;
import util.Rq;

import java.util.Scanner;

public class App {
    public void run() {
        Container.init();
        ArticleController articleController = Container.getArticleController();
        Scanner sc = Container.getScanner();

        System.out.println("== 자바 텍스트 게시판 시작 ==");
        articleController.helpView();

        while (true) {
            System.out.print("명령어: ");
            String command = sc.nextLine().trim();
            if (command.isEmpty()) continue;

            Rq rq = new Rq(command);

            switch (rq.getActionPath()) {
                case "exit" -> {
                    System.out.println("프로그램을 종료합니다.");
                    Container.scClose();
                    return;
                }
                case "write" -> articleController.writeView();
                case "list" -> articleController.listView();
                case "detail" -> articleController.showDetailView(rq);
                case "update" -> articleController.updateView(rq);
                case "delete" -> articleController.deleteView(rq);
                case "help" -> articleController.helpView();
                default -> System.out.println("존재하지 않는 명령어입니다.");
            }
        }
    }
}
