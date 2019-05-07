package com.kangning.demo.service.impl;

import com.kangning.demo.model.vo.PCData;
import com.kangning.demo.service.ConsumerDefService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 加康宁 Date: 2018-08-28 Time: 10:54
 * @version $Id$
 */
@Service("consumerDefService")
public class ConsumerDefServiceImpl extends Thread implements ConsumerDefService {

    private volatile static boolean isRunning = false;

    private volatile static boolean isStopConsume = false;

    private volatile static boolean isStopProduce = false;

    private static List<PCData> queue = new ArrayList<>();

    private static int length = 10000;

    @Override
    public void pushData(PCData pcData) {

//        queue.add(pcData);
        System.out.println(pcData.toString());

    }


    @Override
    public synchronized void pushByDelay(PCData pcdata) throws InterruptedException {

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
            queue.add(pcdata);
            if (isStopConsume){
                queue.notifyAll();
            }
        }


    }

    @Override
    public void run() {
        while (true) {
            try {
                List<PCData> list = new ArrayList<>();
                synchronized (queue) {
                    isRunning = true;
                    System.out.println("开始——消费——推送消息");
                    if (queue.size() == 0) {
                        isStopConsume = true;
                        if (isStopProduce){
                            queue.notifyAll();
                        }
                        queue.wait();
                    }
                    isStopConsume = false;
                    int romoveCount = 5<queue.size()?5:queue.size();
                    for (int i = 0; i <romoveCount;i++){
                        list.add(queue.remove(0));
                    }
                }
                System.out.println("当前消费线程id ：" + Thread.currentThread().getId());
                list.forEach(System.out::println);
                Thread.sleep(1000 * 3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
