package jiake.contro;

import jiake.global.DisplayManager;
import jiake.tool.AnsiColor;
import antlr4.FileManagementGrammarBaseListener;
import antlr4.FileManagementGrammarLexer;
import antlr4.FileManagementGrammarParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.nio.file.InvalidPathException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static jiake.contro.CommandResolve.*;
import static jiake.global.DisplayManager.waitForUserInput;
import static jiake.tool.AnsiColor.*;
import static jiake.tool.PathTool.toAbsolutePath;


public class CommandController extends AbstractController implements PathObserver {
    private static CommandController instance;
    private final DisplayManager displayManager= DisplayManager.getInstance();
    private static final Logger logger = LogManager.getLogger(CommandController.class);
    private CommandController(String path) {
        super(path);
    }
    public static synchronized CommandController getInstance(String path){
        if(instance == null){
            instance = new CommandController(path);
        }
        return instance;
    }

    public void handRequest(String request) throws Exception{
        // 将请求字符串转换为字符流
        CharStream charStream = CharStreams.fromString(request);
        // 使用字符流创建词法分析器
        FileManagementGrammarLexer lexer = new FileManagementGrammarLexer(charStream);
        // 使用词法分析器创建令牌流
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        // 使用令牌流创建语法解析器
        FileManagementGrammarParser parser = new FileManagementGrammarParser(tokens);
        // 解析命令行，生成语法树
        ParseTree tree = parser.commandLine();
        // 分割请求以获取命令及其参数
        String[] parts = request.split("\\s+");
        String commandType = parts[0];
        // 创建一个基础监听器来处理命令行
        FileManagementGrammarBaseListener listener = new FileManagementGrammarBaseListener() {
            @Override
            public void enterCommandLine(FileManagementGrammarParser.CommandLineContext ctx) {
                // 获取命令文本
                String command = ctx.command().getText();
                // 获取所有非引号参数
                List<String> arguments = ctx.argument().stream().map(a -> a.getText()).collect(Collectors.toList());
                // 获取所有引号参数，并去除引号
                List<String> quotedArguments = ctx.quotedArgument().stream().map(qa -> qa.getText().replaceAll("\"", "")).collect(Collectors.toList());
                logger.info("commandCon中，拆分后的指令是" + Arrays.toString(parts) + "commandType是" + commandType);// 记录日志信息
                try {
                    switch (commandType) {
                        case "cd":
                            handleCd(parts);
                            break;
                        case "dir":
                            handleDir();
                            break;
                        case "ls":
                            handleLs(parts);
                            break;
                        case "zip":
                        case "unzip":
                            handleZip(parts);
                            break;
                        case "syc":
                            notifyObservers(currentPath);
                            break;
                        case "cp":
                            handleCp(parts);
                            break;
                        case "cat":
                            handleCat(parts);
                            break;
                        case "encry"://加密
                        case "decry":
                            handleEncry(parts);
                            break;
                        case "mkdir":
                            handleCreate(parts,0);
                        case "touch":
                            handleCreate(parts,1);
                            break;
                        case "rm":
                            handleDelete(parts);
                            break;
                        case "help":
                            handleHelp();
                        default:
                            break;
                    }
                }catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        };
// 用于遍历解析树
        ParseTreeWalker walker = new ParseTreeWalker();
// 使用walker的walk方法来遍历解析树，执行listener中定义的操作
// 参数listener是实现了解析树监听器接口的对象，用于处理遍历过程中的各种事件
// 参数tree是解析树的根节点，表示要遍历的解析树结构
        walker.walk(listener, tree);

    }

    private void handleHelp() {
        displayManager.MainShow(()->{
            System.out.println("+----------------------------"+GREEN+"指令:"+RESET+"------------------------------");
            System.out.println(YELLOW+"|    cd <directory>      "+RESET+"   : 切换到指定目录                                 ");
            System.out.println(YELLOW+"|           dir     "+RESET+"        : (Windows原生命令)列出当前目录下的文件和文件夹      ");
            System.out.println(YELLOW+"|            ls      "+RESET+"       : 列出当前目录下的文件和文件夹，可选择条件进行筛选     ");
            System.out.println(YELLOW+"|    zip <file> <tarPath>  "+RESET+" : 压缩文件或文件夹到指定目录                       ");
            System.out.println(YELLOW+"|  unzip <file> <tarPath>  "+RESET+" : 解压文件到指定目录                              ");
            System.out.println(YELLOW+"|            syc          "+RESET+"  : 同步当前目录                                ");
            System.out.println(YELLOW+"|cp <source> <destination>"+RESET+"  : 复制文件或文件夹                             ");
            System.out.println(YELLOW+"|      cat <file>         "+RESET+"  : 显示文件内容                                ");
            System.out.println(YELLOW+"|   encry <file> <key>     "+RESET+" : 加密文件                                     ");
            System.out.println(YELLOW+"|   decry <file> <key>    "+RESET+"  : 解密文件                                     ");
            System.out.println(YELLOW+"|   mkdir <directory>     "+RESET+"  : 创建目录                                        ");
            System.out.println(YELLOW+"|     touch <file>        "+RESET+"  : 创建文件                                       ");
            System.out.println(YELLOW+"|     rm < -r > <file>    "+RESET+"  : 删除文件或文件夹                             ");
            System.out.println("---------------------------------------");
            System.out.println(PURPLE+"Author"+RESET+": Jiake");
            System.out.println(PURPLE+"Website:"+BLUE+" https://github.com/cqujk"+RESET);
            System.out.println(PURPLE+"        "+BLUE+" https://blog.csdn.net/m0_73553411?spm=1000.2115.3001.5343"+RESET);
            System.out.println("这是20220669贾轲的JAVA第二次实验，有了第一次实验的经验，这次更加注重设计的构建，但开始动工后，又有很多新想法以及新问题与BUG不断地窜出来，也是不断地推倒与重构原先的工程结构与想法，在不断引入新技术并解决各种各样的BUG后，终于也是在半周里顺利的完成了；复盘一下，还是在不断的重构过程中融入了很多新技术，比如ANTLR4，SWING等，但由于项目前期认知不太全面，导致这些方法只是为了解决某一问题而被引入的，并没有完全发挥其真正用处，所以在项目中多少有点鸡肋以及屎山的感觉。不过既然是项目学习实验，用了总比不用好，至少又熟悉了很多之前没用过的技术与方法，在之后的项目构思中肯定也会发挥用处，总体收获还是很大的，感觉比第一次要多（因为这次融的面很多）");
            waitForUserInput();
        });
    }

    @Override
    public void onPathChanged(String newPath) {
        logger.info("CommandCon的onPath方法已触发");
    }
    private void handleCd(String[] parts) {
        if (parts.length > 1) {
            String directory;
            try{
                logger.info("此时传入ToAbsolutePath中的currentPath为"+CommandController.getCurrentPath());
                directory = toAbsolutePath(CommandController.getCurrentPath(),parts[1]);
            }catch (InvalidPathException e){
                System.out.println(AnsiColor.RED+"路径不合法:"+e.getMessage()+ RESET);
                return;
            }
            logger.info("从命令中取得的绝对路径为"+directory);
            File path = new File(directory);
            if(!path.exists()){
                System.out.println(AnsiColor.RED+"目标路径不存在: " + directory+ RESET);
                return;
            }
            if (path.isDirectory()){
                setCurrentPath(directory);
                logger.info("CommandCon的currentPath已改变为" + CommandController.getCurrentPath());
            } else {
                System.out.println(AnsiColor.RED+"目标不为目录" + directory+ RESET);
            }
        } else {
            System.out.println(BLUE+"Usage: cd <directory>"+ RESET);
        }
        //notifyObservers(getCurrentPath());
    }
}
