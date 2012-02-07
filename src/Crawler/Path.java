package Crawler;
public class Path {
	static public String getPath(String parent, String child){
		String absolutePath;

		child = child.replace("href=", "").replaceAll("\"", "");
		if (child.contains(".jpg") || child.contains(".pdf") || child.equals("#")
				|| child.equals("/") || child.contains("javascript") || parent.contains("/"))
			return null;

		if (child.contains("http://"))
			absolutePath = child.replace("http://", "") + " ";
		else if (!child.contains("\\S+"))
			absolutePath = parent + "/ ";
		else
			absolutePath = parent + child + " ";

	//	absolutePath = absolutePath.replaceAll("www.", "");
		return absolutePath;
	}
}
