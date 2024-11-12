package jiake.tool;

import javax.swing.*;
import java.awt.*;

/**
 * TaskPanel 类表示一个任务面板，包含任务名称和进度条.
 */
public class TaskPanel extends JPanel {
    private JLabel taskNameLabel;
    private JProgressBar progressBar;
    public TaskPanel(String taskName) {
        setLayout(new BorderLayout());
        taskNameLabel = new JLabel(taskName);
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true); // 显示百分比
        add(taskNameLabel, BorderLayout.WEST);
        add(progressBar, BorderLayout.CENTER);
    }

    /**
     * 设置进度条的进度.
     *
     * @param progress 进度值，范围为 0 到 100
     */
    public void setProgress(int progress) {
        progressBar.setValue(progress);
    }
}
