package ru.javawebinar.basejava;

public class DeadLock {

    private static final Object RESOURCE1 = new Object();

    private static final Object RESOURCE2 = new Object();

    public static void main(String[] args) throws InterruptedException {

        Thread thread1 = new Thread(() -> {
            synchronized (RESOURCE1) {
                synchronized (RESOURCE2) {
                    try {
                        RESOURCE2.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                RESOURCE1.notify();
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (RESOURCE2) {
                synchronized (RESOURCE1) {
                    try {
                        RESOURCE1.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                RESOURCE2.notify();
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();


    }
}
