package com.kangningj.demo.test.base.service;

/**
 * @author kangningj Date: 2020-03-17 Time: 5:14 下午
 * @version $
 */

public class ThreadSafeSample {
    public int sharedState;
    public void nonSafeAction() {
        while (sharedState < 100000) {
            int former = sharedState++;
            int latter = sharedState;
            if (former != latter - 1) {
                System.out.printf("Observed data race, former is " +
                                      former + ", " + "latter is " + latter + "，" + "ThreadName:" + Thread.currentThread().getName());
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadSafeSample sample = new ThreadSafeSample();
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