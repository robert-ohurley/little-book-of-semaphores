package rendezvous;

import java.util.concurrent.Semaphore;

class ThreadA extends Thread{
    Semaphore aArrived;
    Semaphore bArrived;

    public ThreadA(Semaphore aArrived, Semaphore bArrived){
        this.aArrived = aArrived;
        this.bArrived = bArrived;
    }
    
    public void run(){
        while (true) {
        System.out.println("Running Task A1");
        
        aArrived.release();
        try {
			bArrived.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        
        Helpers.Sleep(1000);

        System.out.println("Running Task A2");
        
        aArrived.release();
        try {
			bArrived.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        Helpers.Sleep(1000);

        }
    }
}


class ThreadB extends Thread{
    Semaphore aArrived;
    Semaphore bArrived;
    
    public ThreadB(Semaphore aArrived, Semaphore bArrived){
        this.aArrived = aArrived;
        this.bArrived = bArrived;
    }
    
    public void run()
    {
        while (true)
        {

            System.out.println("Running Task B1");
            
            bArrived.release();
            try {
				aArrived.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
            
            Helpers.Sleep(1000);
            
            System.out.println("Running Task B2");
            
            bArrived.release();
            try {
				aArrived.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
            Helpers.Sleep(2000);

        }
    }
}

class Helpers {
	static void Sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

public class rendezvous {
    public static void main (String args[]) {

        Semaphore aArrived = new Semaphore(0);
        Semaphore bArrived = new Semaphore(0);      
        
        ThreadA tA = new ThreadA(aArrived, bArrived);
        ThreadB tB = new ThreadB(aArrived, bArrived);


        tA.start();
        tB.start();
    }

}