package jiake.contro;

import jiake.global.DisplayManager;
import jiake.global.ThreadPoolManager;
import jiake.tool.AnsiColor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Files;
import java.nio.file.Paths;

import static jiake.tool.AnsiColor.*;

public class MainController extends AbstractController implements PathObserver{
    private static MainController instance;
    private final DisplayManager displayManager = DisplayManager.getInstance();
    private CommandController commandController;
    private static final Logger logger = LogManager.getLogger(MainController.class);
    private MainController(String path) {
        super(path);
        this.commandController = CommandController.getInstance(path);
        commandController.addObserver(this);
    }
    public static synchronized MainController init(String path) {
        if (path == null || path.isEmpty()) {
            path = System.getProperty("user.dir");
        } else {
            if(!Files.isDirectory(Paths.get(path))){
                System.out.println(AnsiColor.RED+"输入的路径不存在，已切换到默认路径"+ RESET);
                path=System.getProperty("user.dir");
            }
        }
        if (instance == null) {
            instance = new MainController(path);
        }
        return instance;
    }
    public void show() {
       // displayManager.clearMainShow();
//        displayManager.TopShow(()->{
//            System.out.println("---------文件管理----------");
//        });
        displayManager.MainShow(() -> {
            System.out.println();
            System.out.println("+------------------------------------"+PURPLE+"文件管理"+RESET+"----------------------------------+");
            displayManager.showFiles(getCurrentPath());
            System.out.println("+-----------------------------------------------------------------------------+");
            if(ThreadPoolManager.getActiveCount()>0){
                System.out.println(BLUE+"当前后台共有"+ThreadPoolManager.getActiveCount()+"个任务正在执行"+RESET);
            }
            System.out.print("PS " + commandController.getCurrentPath() + ">");

        });
    }
    public void handleRequest(String request) {
        try{
            commandController.handRequest(request);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onPathChanged(String newPath) {
        setCurrentPath(newPath);
        logger.info("MainCon已触发PathChanged方法,此时mainCon的路径为"+getCurrentPath());
    }
}
