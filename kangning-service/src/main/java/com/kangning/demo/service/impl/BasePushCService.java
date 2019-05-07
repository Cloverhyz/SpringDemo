package com.kangning.demo.service.impl;


import com.alibaba.fastjson.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 加康宁 Date: 2018-08-28 Time: 22:07
 * @version $Id$
 */
public abstract class BasePushCService<T> extends Thread {

    private volatile static boolean isRunning = false;

    private volatile static boolean isStopConsume = false;

    private volatile static boolean isStopProduce = false;

    private static List<Object> queue = new ArrayList<>();

    private static int length = 10000;

    public void pushByDelay(List<T> dataList) throws InterruptedException {
        if (!isRunning) {
            System.out.println("开始执行消费行为");
            this.start();
        }

        synchronized (queue) {
            System.out.println("开始——添加——推送消息");
            if (queue.size() >= length) {
                isStopProduce = true;
                queue.wait();
            }
            isStopProduce = false;
            System.out.println("当前生产者线程id ： " + Thread.currentThread().getId());
            queue.addAll(dataList);
            if (isStopConsume) {
                queue.notifyAll();
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                List<T> list = new ArrayList<>();
                synchronized (queue) {
                    isRunning = true;
                    System.out.println("开始——消费——推送消息");
                    if (queue.size() == 0) {
                        isStopConsume = true;
                        if (isStopProduce) {
                            queue.notifyAll();
                        }
                        System.out.println("\n======消费完毕====== ：" + queue.size());
                        queue.wait();
                    }
                    isStopConsume = false;
                    int romoveCount = 5 < queue.size() ? 5 : queue.size();
                    for (int i = 0; i < romoveCount; i++) {
                        list.add((T) queue.remove(0));
                    }
                }
                pushDataList(list);
                Thread.sleep(100 * 3);
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected void pushDataList(List<T> list) throws IOException, InterruptedException {
        System.out.println("当前消费线程id" + Thread.currentThread().getId());
        String syncUrl = getSyncUrl();
        String body = JSONArray.toJSONString(list);
        Thread.sleep(1000 * 3);
        dealWithResponse(list);
    }

    protected abstract String getSyncUrl();

    protected abstract void dealWithResponse(List<T> list);
}