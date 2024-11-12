package jiake.tool;

import javax.swing.*;
import java.awt.*;

public class TaskPanelRender  extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        TaskPanel taskPanel = (TaskPanel) value;
        if (taskPanel == null) {
            return this;
        }
        if (isSelected) {
            taskPanel.setBackground(list.getSelectionBackground());
            taskPanel.setForeground(list.getSelectionForeground());
        } else {
            taskPanel.setBackground(list.getBackground());
            taskPanel.setForeground(list.getForeground());
        }
        taskPanel.setEnabled(list.isEnabled());
        taskPanel.setFont(list.getFont());
        taskPanel.setFocusable(false);
        return taskPanel;
    }
}
