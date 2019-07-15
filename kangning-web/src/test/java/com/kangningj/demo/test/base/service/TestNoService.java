package com.kangningj.demo.test.base.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.junit.Test;

/**
 * @author 加康宁 Date: 2019-07-13 Time: 16:42
 * @version $
 */
public class TestNoService {

    private static ExecutorService cachedThreadPool = new ThreadPoolExecutor(8, Runtime.getRuntime().availableProcessors()
            * 2, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), r -> new Thread(r, "RatePlanController"));

    public static void main(String args[]){
        Date startDate = DateTime.now().withMillisOfDay(0).toDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        int month = calendar.get(Calendar.MONTH);
        month += 7;
        calendar.set(calendar.get(Calendar.YEAR), month,1);
        Date endDate = DateUtils.addDays(calendar.getTime(), -1);
        System.out.println(endDate);
    }

    @Test
    public void testAsyn() throws ExecutionException, InterruptedException {

        List<Callable<Integer>> callableList = new ArrayList<>();
        for (int i = 0; i < 15; i++){
            int finalI = i;
            Callable<Integer> callable = () -> {
                Thread.sleep(1500 -  finalI * 100);
                System.out.println(finalI);
                return finalI;
            };
            callableList.add(callable);
        }

        System.out.println("同步：");
        for (Callable<Integer> callable : callableList){
            cachedThreadPool.submit(callable).get();
        }
        System.out.println("异步：");
        cachedThreadPool.invokeAll(callableList);
    }
}
