package com.kangning.demo.service;


import com.kangning.demo.model.vo.PCData;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 加康宁 Date: 2018-08-27 Time: 21:41
 * @version $Id$
 */

public class ConsumerTest {

    private volatile static boolean isStopConsume = false;

    private volatile static boolean isStopProduce = false;

    private volatile static boolean isRunning = false;

    private static int length = 10000;

    private static List<PCData> queue = new ArrayList<>();

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            queue.add(new PCData());
        }

        while (true) {
            try {
                List<PCData> list = new ArrayList<>();
                 {
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
                System.out.println(list.toString());
//                list.forEach(System.out::println);
                Thread.sleep(1000 * 3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}