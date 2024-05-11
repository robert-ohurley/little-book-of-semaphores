package mutex;

import java.util.concurrent.Semaphore;

class ThreadA extends Thread {
    Semaphore count;
    SharedResource shared;

    public ThreadA(Semaphore count, SharedResource s) {
        this.count = count;
        this.shared = s;
    }

    public void run() {
        for (int i = 0; i < 10000; i++) {
            try {
                this.count.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            this.shared.increment();
            this.count.release();
        }

        SharedResource.finished.release();
    }
}

class ThreadB extends Thread {
    Semaphore count;
    SharedResource shared;

    public ThreadB(Semaphore count, SharedResource s) {
        this.count = count;
        this.shared = s;
    }

    public void run() {
        for (int i = 0; i < 10000; i++) {
            try {
                this.count.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            this.shared.increment();
            System.out.printf("shared is: %d\n", shared.shared);

            this.count.release();
        }

        SharedResource.finished.release();
    }
}

class SharedResource {
    int shared;
    static Semaphore finished = new Semaphore(0);

    public SharedResource() {
        this.shared = 0;
    }

    public void increment() {
        this.shared += 1;
    }

    public void decrement() {
        this.shared -= 1;
    }
}

public class mutex {
    public static void main(String args[]) {
        SharedResource s = new SharedResource();
        Semaphore count = new Semaphore(1);

        ThreadA tA = new ThreadA(count, s);
        ThreadB tB = new ThreadB(count, s);

        tA.start();
        tB.start();

        try {
            SharedResource.finished.acquire();
            SharedResource.finished.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(s.shared);

    }
}
