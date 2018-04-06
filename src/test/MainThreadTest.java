package test;

public class MainThreadTest {
	public static void main(String[] args) throws InterruptedException {
		Thread aThread = new Thread() {
			public void run() {
				System.out.println("Thread Start...");
				try {
					System.out.println("start sleep");
					Thread.sleep(1000);
					System.out.println("over sleep");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				synchronized (MainThreadTest.class) {
					MainThreadTest.class.notify();
				}

				System.out.println("Thread End...");
			}
		};
		aThread.start();
		synchronized (MainThreadTest.class) {
			System.out.println("before wait");
			MainThreadTest.class.wait();
			System.out.println("after wait");
		}

	}
}
