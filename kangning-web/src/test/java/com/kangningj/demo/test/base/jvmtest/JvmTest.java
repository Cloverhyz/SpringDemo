package com.kangningj.demo.test.base.jvmtest;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author kangningj Date: 2020-05-07 Time: 1:44 下午
 * @version $
 */
public class JvmTest {

    public static void main(String[] args) throws InterruptedException {
        byte[] obj;
        LinkedBlockingQueue<byte[]> alarmList = new LinkedBlockingQueue<byte[]>();

        Thread thread = new Thread(() -> {
            while (alarmList.size() <= 10000) {
                byte[] newObj = new byte[1024 * 1024 * 10];
                alarmList.add(newObj);
                System.out.println("===添加对象完毕===");
            }

        });
        thread.start();

        Thread.sleep(20);
        while(true)
        {
                obj = alarmList.take() ;
//                System.out.println("取出对象完毕" + alarmList.size());

        }
//        System.out.println("结束" + alarmList.size());
    }

}
