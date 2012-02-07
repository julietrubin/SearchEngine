package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Base extends HttpServlet {
	protected static String getValue(HttpServletRequest request, String parameter) {
		Cookie[] cookies = request.getCookies();
		String name = null;

		if(cookies != null) {
			for(Cookie c: cookies) {
				if(c.getName().equals(parameter)) 
					name = c.getValue();
			}
		}
		return name;
	}
	
	@SuppressWarnings("unchecked")
	protected String getPar(String key, HttpServletRequest request) {
		String status = "";
		Map <String[], String[]> m = request.getParameterMap();
		if(m.containsKey(key)) 
			status = ((String[])m.get(key))[0];
		return status;
	}
	protected int getIntParam(String key, HttpServletRequest request) throws InvalidParameterException {
		Map m = request.getParameterMap();
		int value;
		if(m.containsKey(key)) {
			try {
				value = Integer.parseInt(((String[])m.get(key))[0]);
			} catch(NumberFormatException nfe) {
				throw new InvalidParameterException(InvalidParameterException.PARAM_NOT_INT);
			}
		} 
		else 
			throw new InvalidParameterException(InvalidParameterException.NO_SUCH_PARAM);

		return value;
	}

	protected PrintWriter prepareResp(HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		return response.getWriter();
	}
	static protected String header(String title) {
		return "<html><head><style type=\"text/css\">"
		+ "table{padding: 18px; }"
		+ "td{padding: 4px;vertical-align:top;}"
		+ "body{font-family:fantasy;}"
		+ "#background {width: 100%; height: 100%; position: fixed;z-index: -1;left: 0px; top: 0px;}"
		+ ".stretch {width:100%;height:100%;}"
		+ "hr{height:3px; background-color:CCFFFF; color:CCFFFF;}"
		+ ".right{text-align:right;}"
		+ ".center{text-align:center;}"
		+ ".margin{margin-left:75%; margin-right:8%}"
		+ "</style><title>" + title + "</title></head><body>";		
	}

	static protected String footer() {
		return "</body></html>";
	}

	static protected String link(String path, String name) {
		return "<a href= " + path + " > " + name + " </a>";
	}

	static protected String endDiv() {
		return "</div>";
	}

	static protected String margin() {
		return "<div class=\"margin\">";
	}

	static protected String alignRight() {
		return "<div class=\"right\">";
	}

	static protected String alignCenter() {
		return "<div class=\"center\">";
	}

	public static String linebreak(){
		return "<HR />";
	}

	public static String newLine(){
		return "<BR />";
	}

	public static String linedivider(){
		return "&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;";
	}
	
	public static String space(){
		return "&nbsp;";
	}

	public static String startForm(String name, String action, String method){
		return "<form name=\" "+name+" \" action=\" "+action+" \" method=\" "+method+" \">";
	}

	public static String form(String type, String name, String value){
		return "<input type=\""+type+"\"name=\""+name+"\"value=\""+value +"\"/>";
	}

	public static String form(String type, String name, String value, int size){
		return "<input type=\""+type+  "\"name=\"" + name + "\"size=\"" + size + "\"value=\""+value +"\"/>";
	}

	public static String form(String type, String value){
		return "<input type=\""+type+"\"value=\""+value +"\"/>";
	}

	public static String startTable(String width){
		return "<table width=" + width + ">";
	}

	public static String startTable(){
		return "<table>";
	}

	public static String endTable(){
		return "</table>";
	}

	public static String endForm(){
		return "</form>";
	}

	static public ArrayList<String> row(String column, String column1){
		ArrayList <String> s;
		s = new ArrayList<String>();
		s.add("<tr>");
		s.add("<td>");
		s.add(column);
		s.add("</td>");
		s.add("<td>");
		s.add(column1);
		s.add("</td>");
		s.add("</tr>");
		return s;
	}

	static public ArrayList<String> row(String column){
		ArrayList <String> s;
		s = new ArrayList<String>();
		s.add("<tr>");
		s.add("<td align=\"center\" colspan=\"2\">");
		s.add(column);
		s.add("</td>");
		s.add("</tr>");
		return s;
	}
	
	static public String error(String error){
		return "<p style=\"font-size:80%;color:red\">" + error + "</p>";
	}

	static public String background(String path){
		return "<div id=\"background\">"
		+ "<img src=\""+ path +"\""
		+ "class=\"stretch\" alt=\"\" />";
	}
}