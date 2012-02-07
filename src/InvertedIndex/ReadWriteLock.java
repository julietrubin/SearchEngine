package InvertedIndex;
public class ReadWriteLock 
{
	private int readers;
	private int writers;

	protected ReadWriteLock()	{
		this.readers = 0;
		this.writers = 0;
	}

	synchronized protected void acquireWrite() {
		while (readers > 0 || writers > 0)
			try {
				wait();
			} catch (InterruptedException e) {}
			writers++;
	}

	synchronized protected void realeaseWrite() {
		writers--;
		notifyAll();
	}

	synchronized protected void acquireRead() {
		while (writers > 0)
			try {
				wait();
			} catch (InterruptedException e) {}
			readers++;
	}

	synchronized protected void realeaseRead() {
		readers--;
		notifyAll();
	}
}
