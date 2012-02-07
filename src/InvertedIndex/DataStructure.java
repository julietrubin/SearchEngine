package InvertedIndex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Map.Entry;


public class DataStructure{
	private HashMap<String, HashMap<String, ArrayList<Integer>>> invertedIndex;	
	private ReadWriteLock readWriteLock;

	public DataStructure() {
		this.invertedIndex = new HashMap<String, HashMap<String, ArrayList<Integer>>>();
		this.readWriteLock = new ReadWriteLock();
	}

	public void add(String word, String path, int position) {
		readWriteLock.acquireWrite();
		if (!containsWord(word))
			addWord(word, path, position);
		else if (!containsPath(word, path))
			addPath(word, path, position);
		else
			addPosition(word, path, position);
		readWriteLock.realeaseWrite();
	}

	/*contains methods*/
	private boolean containsWord(String word) {
		return invertedIndex.containsKey(word);
	}
	private boolean containsPath(String word, String path){
		return invertedIndex.get(word).containsKey(path);
	}

	/*access methods*/
	private Set<String> getPaths(String word){
		return invertedIndex.get(word).keySet();
	}
	private ArrayList<Integer> getPositions(String word, String path) {
		return invertedIndex.get(word).get(path);
	}

	/*mutate methods*/
	private void addWord(String word, String path, int position){
		HashMap<String, ArrayList<Integer>> innerMap;
		innerMap = new HashMap<String, ArrayList<Integer>>();
		innerMap.put(path, new ArrayList<Integer>(position));
		invertedIndex.put(word, innerMap);
	}
	private void addPath(String word, String path, int position) {
		ArrayList<Integer> a = new ArrayList<Integer>();
		a.add(position);
		invertedIndex.get(word).put(path, a);		
	}
	private void addPosition(String word, String path, int position) {
		getPositions(word, path).add(position);			
	}

	/*search methods*/
	@SuppressWarnings("unchecked")
	public ArrayList<String> searchDS(String query) {
		ArrayList<String> path;
		ArrayList<Object> entrySet;
		HashMap hm;

		path = new ArrayList<String>();
		readWriteLock.acquireRead();
		hm = pathPriorityMap(query.toLowerCase());
		entrySet = new ArrayList<Object>(hm.entrySet());  
		Collections.sort(entrySet, Priority.valueSort);
		for (Object s: entrySet) 
			path.add("http://" + ((Entry<String, Integer>) s).getKey());
		readWriteLock.realeaseRead();
		return path;
	}


	private HashMap<String, Integer> pathPriorityMap(String query){
		HashMap<String, Integer> quotePaths;
		HashMap<String, Integer> plainTextPaths;
		AdvancedSearch ad;

		ad = new AdvancedSearch();
		ad.start(query);
		quotePaths = inQuotesText(ad.getQuotes());
		plainTextPaths = plainText(ad.getPlain());

		for (String path : quotePaths.keySet())
			updatePriority(plainTextPaths, path, quotePaths.get(path));
		for(String key: negativeText(ad.getNegative()))
			plainTextPaths.remove(key);
		return plainTextPaths;
	}


	private HashMap<String, Integer> plainText(ArrayList<String> arrayList) {
		HashMap<String, Integer> HM;
		int priority;
		HM = new HashMap<String, Integer>();

		for(String line: arrayList) {
			String[] token = line.split(" ");
			for (int i = 0; i < token.length; i++){
				if(!containsWord(token[i]))
					continue;
				for(String path: getPaths(token[i])){
					priority = Priority.occurencePriority(getPositions(token[i], path));
					for(int n = i + 1; n < token.length; n++){
						if(containsWord(token[n]) && containsPath(token[n], path))
							priority =+ Priority.proximityPriority(getPositions(token[i], path), 
									getPositions(token[n], path));
					}
					updatePriority(HM, path, priority);
				}
			}
		}
		return HM;
	}

	private HashMap<String, Integer> inQuotesText(ArrayList<String> arrayList) {
		Set<String> list;
		String[] wordArray;
		HashMap<String, Integer> HM;
		HM = new HashMap<String, Integer>();


		for(String line: arrayList){
			wordArray = line.split(" ");
			list = new HashSet<String>(getPaths(wordArray[0]));
			for(int i = 1; i <wordArray.length; i++) {
				if(containsWord(wordArray[i]))
					list.retainAll(getPaths(wordArray[i]));
			}
			for (String path: list){
				updatePriority(HM, path, function(wordArray, path));
			}
		}
		return HM;
	}

	private int function(String[] wordArray, String path) {
		int priority = 0;
		ArrayList<Integer> positions = getPositions(wordArray[0], path);
		for(Integer position: positions){
			for(int i = 1; i < wordArray.length; i++) {
				if(!getPositions(wordArray[i], path).contains(position + i))
					break;
			}
			priority +=  wordArray.length * Priority.SUPERHIGHPRIORITY;
		}

		return priority;
	}

	private Set<String> negativeText(ArrayList<String> arrayList) {
		Set<String> list;
		list = new HashSet<String>();

		for(String word: arrayList){
			if(containsWord(word))
				list.addAll(getPaths(word));
		}
		return list;
	}


	private static void updatePriority(HashMap<String, Integer> hm, String path, int update){
		if (hm.containsKey(path))
			hm.put(path, hm.get(path) + update);
		else
			hm.put(path, update);			
	}
}
