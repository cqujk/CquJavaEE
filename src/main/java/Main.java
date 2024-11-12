import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import jiake.contro.MainController;
import jiake.global.ThreadPoolManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static jiake.tool.AnsiColor.*;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(RED+"Do you want to input a path? (y/n): "+RESET);
        String input = scanner.nextLine();
        String path = "";
        if ("y".equalsIgnoreCase(input)) {
            System.out.print(BLUE+"Enter the path: "+RESET);
            path = scanner.nextLine();
        }
        MainController controller = MainController.init(path);
        while (true) {
            controller.show();
            String request = scanner.nextLine();
            if ("exit".equalsIgnoreCase(input)) {
                break;
            }
            controller.handleRequest(request);
        }
        ThreadPoolManager.getExecutor().shutdown(); // 关闭线程池
    }
}