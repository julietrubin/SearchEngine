package InvertedIndex;
import java.util.ArrayList;

public class AdvancedSearch{
	private ArrayList<String> quotes;
	private ArrayList <String> negitive;
	private ArrayList <String> plain;

	public AdvancedSearch() {
		quotes = new ArrayList<String>();
		negitive = new ArrayList<String>();
		plain = new ArrayList<String>();
	} 

	public ArrayList<String> getPlain() {
		return plain;
	}

	public ArrayList<String> getQuotes() {
		return quotes;
	}

	public ArrayList<String> getNegative() {
		return negitive;
	}

	public String start(String query) {
		int index;

		if (query.matches("[^-\"].*")){
			int indexM = query.indexOf("-");
			int indexQ = query.indexOf("\"");

			if (indexM == -1 && indexQ == -1)
				index = query.length();
			else if (indexM== -1)
				index = indexQ;
			else if (indexQ == -1)
				index = indexM;
			else
				index = (indexM < indexQ) ? indexM : indexQ;

			plain.add(query.substring(0, index).trim());
			return start(query.substring(index, query.length()).trim());
		}

		if (query.matches("\".+\".*")){
			index = query.indexOf("\"", 1);
			quotes.add(query.substring(1 ,index));
			return start(query.substring(index + 1, query.length()).trim());
		}

		if (query.matches("-.+")) {
			index = query.contains(" ") ? query.indexOf(" ", 1): query.length();
			negitive.add(query.substring(1, index));
			return start(query.substring(index, query.length()).trim());
		}
		return "";
	}
}


