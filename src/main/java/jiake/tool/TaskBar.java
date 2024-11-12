package jiake.tool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * TaskBar 类提供了一个图形界面的任务栏，用于添加和删除任务.
 * 它使用 Swing 构建界面，CopyOnWriteArrayList 用于线程安全地管理任务列表.
 */
public class TaskBar extends JFrame {
    private static final Logger logger= LogManager.getLogger(TaskBar.class);
    // 任务列表，显示在界面上
    private JList<TaskPanel> taskList;
    // 任务模型，用于管理显示在列表中的任务名称
    private DefaultListModel<TaskPanel> taskModel;
    // 存储任务的线程安全列表
    private CopyOnWriteArrayList<SwingWorker<Void,Integer>> tasks = new CopyOnWriteArrayList<>();


    /**
     * TaskBar 构造函数初始化任务栏界面.
     */
    public TaskBar() {
        super("Task Bar");
        // 初始化任务模型和列表
        taskModel = new DefaultListModel<>();
        taskList = new JList<>(taskModel);
        taskList.setCellRenderer(new TaskPanelRender());
        // 将任务列表添加到滚动面板，并设置为窗口的中心组件
        this.add(new JScrollPane(taskList), BorderLayout.CENTER);
        // 设置窗口关闭操作
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 调整窗口大小以适应其内容
        this.pack();
        // 显示窗口
        this.setVisible(true);
        logger.info("任务栏已经初始化");
    }

    /**
     * 添加任务到任务栏.
     *
     * @param task    要添加的任务，作为一个 Runnable 对象
     * @param taskName 任务的名称，显示在任务列表中
     */
    public void addTask(Runnable task, String taskName) {
        // 创建任务面板
        TaskPanel taskPanel = new TaskPanel(taskName);
        // 将任务面板添加到任务模型
        taskModel.addElement(taskPanel);
        logger.info("任务栏中已添加线程: "+taskPanel);
        SwingWorker<Void,Integer>worker=new SwingWorker<Void, Integer>() {
            @Override
            protected Void doInBackground() throws Exception {
                // 模拟任务进度更新
                for (int i = 0; i <= 100; i++) {
                    if (isCancelled()) {
                        break;
                    }
                    setProgress(i);
                    publish(i);
                    logger.info("任务名称" + taskName + "任务进度: " + i);
                    Thread.sleep(100); // 模拟任务的每一步
                }
                return null;
            }
            @Override
            protected void process(java.util.List<Integer> chunks) {
                for(int i:chunks){
                    SwingUtilities.invokeLater(() -> taskPanel.setProgress(i));
                }
                //logger.info("任务进度: " + chunks.get(chunks.size() - 1));
            }
            @Override
            protected void done() {
                // 任务完成后移除任务
                SwingUtilities.invokeLater(() -> removeTask(this, taskPanel));
            }
        };
        // 将任务添加到任务列表
        tasks.add(worker);
        worker.execute();
    }

    /**
     * 从任务栏中移除任务.
     *
     * @param task 要移除的任务，作为一个 Runnable 对象
     */
    public void removeTask(SwingWorker<Void, Integer> task,TaskPanel taskPanel) {
        // 从任务列表中移除任务
        tasks.remove(task);
        // 从任务模型中移除任务面板
        taskModel.removeElement(taskPanel);
        logger.info("任务栏中已移除线程: "+taskPanel.toString());
    }

    /**
     * 主函数，用于启动 TaskBar.
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        // 在事件调度线程上运行，以确保 Swing 组件线程安全地创建和更新
        SwingUtilities.invokeLater(() -> {
            TaskBar taskBar = new TaskBar();
            // 添加一个示例任务
            taskBar.addTask(() -> {
                try {
                    // 模拟一个长时间运行的任务
                    for (int i = 0; i < 100; i++) {
                        Thread.sleep(50); // 模拟任务的每一步
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "Long Running Task");
            taskBar.addTask(() -> {
                try {
                    // 模拟一个长时间运行的任务
                    for (int i = 0; i < 200; i++) {
                        Thread.sleep(100); // 模拟任务的每一步
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "Running Task");
        });
    }
}
