package com.kangningj.demo.test.base.service;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author kangningj Date: 2020-03-17 Time: 6:55 下午
 * @version $
 */
public class ReentrantLockTest {
    ReentrantLock lock = new ReentrantLock();
    public int sharedState;

    public void nonSafeAction() {
        while (sharedState < 100000) {
            lock.lock();
            int former = sharedState++;
            int latter = sharedState;
            lock.unlock();
            if (former != latter - 1) {
                System.out.println("Observed data race, former is " +
                                      former + ", " + "latter is " + latter + "，" + "ThreadName:" + Thread.currentThread().getName());
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockTest sample = new ReentrantLockTest();
        Thread threadA = new Thread("1"){
            public void run(){
                sample.nonSafeAction();
            }
        };
        Thread threadB = new Thread("2"){
            public void run(){
                sample.nonSafeAction();
            }
        };
        threadA.start();
        threadB.start();
        threadA.join();
        threadB.join();
    }
}