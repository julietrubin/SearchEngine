package Crawler;
import java.util.LinkedList;
public class WorkQueue{

	private LinkedList<Runnable> queue;
	private PoolWorker[] threads;
	private boolean on;

	public WorkQueue(int capacity) {
		this.queue = new LinkedList<Runnable>();
		this.threads = new PoolWorker[capacity];
		this.on = true;

		for (int i = 0; i < capacity; i++) {
			this.threads[i] = new PoolWorker();
			this.threads[i].start();		
		}
	}

	public void execute(Runnable r, String whichSite) {
		synchronized(queue) {
			queue.addLast(r);
			queue.notify();
		}
	}

	public void stop() {
		this.on = false;
		synchronized(queue){
			queue.notifyAll();
		}
	}

	private class PoolWorker extends Thread {

		public void run() {
			Runnable textprocesor;
			while (on) {
				synchronized(queue) {
					while (queue.isEmpty() && on) {
						try	{	
							queue.wait();
						} catch (InterruptedException ignored) {}
					}	
					if (!on)
						return;
					textprocesor = (Runnable) queue.removeFirst();
				}
				try {
					textprocesor.run();
				} catch (RuntimeException e) {}
			}
		}
	}
}
