package Crawler;
import java.util.ArrayList;
import java.util.LinkedList;
import InvertedIndex.DataStructure;

public class Crawler{
	private static final Crawler INSTANCE = new Crawler(10);
	private WorkQueue wq;
	private DataStructure ds;
	private LinkedList<String> ll;

	protected Crawler(int numberOfThreads){
		this.wq = new WorkQueue(numberOfThreads);
		this.ds = new DataStructure();
		this.ll = new LinkedList<String>();
	}

	public void addLink(String homepage, String extention) {
		String absolutePath;
		int index;

		synchronized(ll) {
			if (ll.size()>200)
				return;

			if((absolutePath = Path.getPath(homepage, extention)) == null)
				return;

			if (ll.contains(absolutePath))
				return;
			ll.add(absolutePath);
		}

		index = absolutePath.indexOf("/");
		wq.execute(new TagStripper(absolutePath.substring(0, index),
				absolutePath.substring(index, absolutePath.length()), this), homepage);
	}

	public void stop() {
		wq.stop();
	}

	public ArrayList<String> search(String query){
		return ds.searchDS(query.toLowerCase());
	}

	protected void addWordtoStructure(String token, String absolutePath, int position) {
		token = token.toLowerCase().replaceAll("\\W", "");
		if (!token.matches("\\S+"))
			return;
		synchronized (ds) {
			ds.add(token, absolutePath, position);
		}
	}

	public static Crawler instance() {
		return INSTANCE;
	}
}





