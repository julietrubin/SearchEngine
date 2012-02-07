package InvertedIndex;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;

public class Priority{
	public final static int SUPERHIGHPRIORITY = 300;
	private final static int HIGHPRIORITY = 100;
	private final static int MEDIUMPRIORITY = 50;
	private final static int LOWPRIORITY = 25;
	private final static int SUPERLOWPRIORITY = 15;

	public static Comparator<Object> valueSort = new Comparator<Object>() {  
		@SuppressWarnings("unchecked")
		public int compare( Object hm1, Object hm2) {  
			Map.Entry<String,Integer> e1 = (Map.Entry<String,Integer>)hm1 ;  
			Map.Entry<String,Integer> e2 = (Map.Entry<String,Integer>)hm2;  
			Integer firstvalue = (Integer)e1.getValue();  
			Integer secondvalue = (Integer)e2.getValue();  
			return secondvalue.compareTo(firstvalue);  
		}  
	};

	public static int occurencePriority(ArrayList<Integer> positionlist) {
		int priority;

		priority = positionlist.size();
		for(Integer position: positionlist){
			if (position < 10)
				priority += HIGHPRIORITY;
			else if (10 < position  && position <  20)
				priority += MEDIUMPRIORITY;
			else if (20 < position  && position <  30)
				priority += LOWPRIORITY;
			else if (30 < position  && position <  40)
				priority += SUPERLOWPRIORITY;
		}
		return priority;
	}

	public static int proximityPriority(ArrayList<Integer> a, ArrayList<Integer> a2) {
		int priority;
		int position;

		priority= 0;
		for (int i = 0; i < a.size(); i++) {
			position = a.get(i);
			for (int j = i + 1; j < a2.size(); j++) {
				if ((position - a2.get(j)) == 1)
					priority += SUPERHIGHPRIORITY;
			}
		}
		return priority;
	}
}
