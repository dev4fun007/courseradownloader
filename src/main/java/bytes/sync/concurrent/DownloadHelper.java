package bytes.sync.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DownloadHelper {

    private static ExecutorService downloadExecutorPool = Executors.newFixedThreadPool(5);


    public static void submitDownloadTask(DownloadTask downloadTask) {
        downloadExecutorPool.execute(downloadTask);
    }

    public static void shutdownExecutorService() {
        downloadExecutorPool.shutdown();
    }


}
