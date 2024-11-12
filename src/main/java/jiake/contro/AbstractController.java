package jiake.contro;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * 抽象控制器类，用于管理当前路径并通知观察者路径变化
 */
public abstract class AbstractController {
    protected static String currentPath;
    private List<PathObserver> observers = new ArrayList<>();// 观察者列表
    public AbstractController(String path) {
        this.currentPath = path;
    }
    /**
     * 添加路径变化观察者
     * @param observer 要添加的观察者
     */
    public void addObserver(PathObserver observer) {
        observers.add(observer);
    }
    public void removeObserver(PathObserver observer) {
        observers.remove(observer);
    }
    /**
     * 通知所有观察者路径已更改
     * @param newPath 新路径
     */
    protected void notifyObservers(String newPath) {
        for (PathObserver observer : observers) {
            observer.onPathChanged(newPath);
        }
    }
    public static String getCurrentPath() {
        return currentPath;
    }
    public void setCurrentPath(String newPath) {
        this.currentPath = newPath;
        notifyObservers(newPath);
    }
}

