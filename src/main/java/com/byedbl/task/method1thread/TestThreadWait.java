package com.byedbl.task.method1thread;

public class TestThreadWait {

    /**
     * 不建议使用，任务复杂时存在内存泄露风险
     * @param args
     */
    public static void main(String[] args) {
        Thread myThread = new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    System.out.println("TestThreadWait is called!");
                    try {
                        // 使用线程休眠来实现周期执行
                        Thread.sleep(1000 * 3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        myThread.start();
    }

}
