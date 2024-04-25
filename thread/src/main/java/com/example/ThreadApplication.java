package com.example;

public class ThreadApplication {

    public static void main(String[] args) {
        Thread thread1 = new Thread(new Runner("Runner1"));
        Thread thread2 = new Thread(new Runner("Runner2"));

        // 啟動線程，無需等待 run() 方法中的程式執行完畢。
//        thread1.start();
//        thread2.start();

        // 將 run() 方法中的程式當作普通方法調用，第一個執行完才會執行第二個。
        thread1.run();
        thread2.run();
    }

}

class Runner implements Runnable {

    private String name;

    Runner(String name) {
        super();
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println("開始執行: " + this.name);
        for (int i = 0; i < 1000; i++) {
            System.out.println(this.name + " count: " + i);
        }
        System.out.println("結束執行: " + this.name);
    }
}
