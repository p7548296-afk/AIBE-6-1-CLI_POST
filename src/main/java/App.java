import controller.ArticleController;
import global.dto.Rq;

import java.util.Scanner;

public class App {

    private final Scanner sc;

    public App(Scanner sc) { // 외부에서 주입받도록 변경
        this.sc = sc;
    }

    public void run() {
        Container.init(this.sc);
        ArticleController articleController = Container.getArticleController();

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
                case "list" -> articleController.showList(rq);
                case "detail" -> articleController.showDetail(rq);
                case "update" -> articleController.doModify(rq);
                case "delete" -> articleController.doDelete(rq);
                case "help" -> articleController.showHelp();
                default -> System.out.println("존재하지 않는 명령어입니다. 'help'를 입력해보세요.");
            }
        }
    }
}
