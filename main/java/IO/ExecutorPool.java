package IO;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by posy on 17.12.8.
 */
public class ExecutorPool {
    private ExecutorService executorService;

    public ExecutorPool(int max, int queueSize) {
        this.executorService = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), max, 120L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(queueSize));
    }

    public void execute(Runnable runnable) {
        executorService.execute(runnable);
    }
}
