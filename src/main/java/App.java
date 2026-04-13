import controller.ArticleController;
import util.Rq;

import java.util.Scanner;

public class App {
    public void run() {
        Container.init();
        ArticleController articleController = Container.getArticleController();
        Scanner sc = Container.getScanner();

        System.out.println("== 자바 텍스트 게시판 시작 ==");

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
                case "write" -> articleController.doWrite();
                case "list" -> articleController.showList();
                case "detail" -> articleController.showDetail(rq);
                case "update" -> articleController.doModify(rq);
                case "delete" -> articleController.doDelete(rq);
                case "help" -> articleController.showHelp();
                default -> System.out.println("존재하지 않는 명령어입니다. 'help'를 입력해보세요.");
            }
        }
    }
}
