import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

class TT extends Thread {
    private int id;
    Enum<State> threadState;
  
    public TT(int idInput) {
    	this.id=idInput;
    }

  
	@Override
    public void run() {
		while (true) {
			if( DiningPhilosophers.forks[this.id].availablePermits()==1) {
				try {
					DiningPhilosophers.forks[this.id].acquire();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if( DiningPhilosophers.forks[(this.id+1)%5].availablePermits()==1) {
					try {
						DiningPhilosophers.forks[(this.id+1)%5].acquire();
			           	System.out.println(this.id +" start eating ");
						this.sleep((long) 50);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		           	System.out.println(this.id +" end eating ");
		           	DiningPhilosophers.forks[(this.id+1)%5].release();
					DiningPhilosophers.forks[this.id].release();
					try {
						this.sleep((long) 150);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else  {
					DiningPhilosophers.forks[this.id].release();
					try {
						System.out.println(this.id +" could not eat");
						this.sleep((long) 200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			
			}
			else {
				try {
					System.out.println(this.id +" sleeping");
					this.sleep((long) 200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
				
			}
			
}
}
public class DiningPhilosophers {
	static Semaphore[] forks=new Semaphore[5];
	
	public static void main(String[] args) {
		for (int i = 0; i < forks.length; i++) {
			forks[i]=new Semaphore(1);
		}
		Thread[] threads=new Thread[forks.length];
		for (int i = 0; i < forks.length; i++) {
			TT p = new TT(i);
			 p.start();
			threads[i]=p;
		}
//		for (int i = 0; i < forks.length; i++) {
//			try {
//		        threads[i].join();
//		    } catch (InterruptedException e) {
//		        e.printStackTrace();
//		    }
//
//		}
	}

}
