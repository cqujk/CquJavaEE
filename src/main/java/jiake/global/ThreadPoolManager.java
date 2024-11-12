package jiake.global;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPoolManager {
    private static final ExecutorService executor = Executors.newFixedThreadPool(10); // 创建固定大小的线程池
    private ThreadPoolManager() {
        // 防止实例化
    }
    public static ExecutorService getExecutor() {
        return executor;
    }
    public static int getPoolSize() {
        if (executor instanceof ThreadPoolExecutor) {
            ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor;
            return threadPoolExecutor.getPoolSize();
        }
        return -1; // 如果不是 ThreadPoolExecutor，返回 -1
    }
    public static int getActiveCount() {
        if (executor instanceof ThreadPoolExecutor) {
            ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor;
            return threadPoolExecutor.getActiveCount();
        }
        return -1; // 如果不是 ThreadPoolExecutor，返回 -1
    }
}
