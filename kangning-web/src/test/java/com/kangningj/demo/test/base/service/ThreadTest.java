package com.kangningj.demo.test.base.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.junit.Test;

/**
 * @author 加康宁 Date: 2019-12-02 Time: 10:34
 * @version $
 */
public class ThreadTest {


    private static ExecutorService cachedThreadPool = new ThreadPoolExecutor(2, 4, 0, TimeUnit.MILLISECONDS, new SynchronousQueue<>(), r -> new Thread(r, "ThreadTest"));

    @Test
    public void testLinkedBlockingQueue() throws InterruptedException {
        for (int i = 0; i < 100; i++){
            int finalI = i;
            cachedThreadPool.execute(()->{
                System.out.println(finalI);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        cachedThreadPool.shutdown();
        while (!cachedThreadPool.isTerminated()){
            Thread.sleep(1000);
        }
    }

}
