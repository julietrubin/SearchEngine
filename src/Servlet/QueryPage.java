package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Crawler.Crawler;


public class QueryPage extends Base{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(getValue(request, "id") == null) {
			response.sendRedirect(response.encodeRedirectURL("/index"));
			return;
		}

		String name;
		String id;
		name = getValue(request, "name");
		id = getValue(request, "id");

		/*HTML*/
		response.setStatus(HttpServletResponse.SC_OK);
		PrintWriter p = prepareResp(response);
		p.println(header("Search History Page"));	
		for(String code: panel())
			p.println(code); 
		for(String query: DBHandler.instance().getHistory("query", id)) {
			p.println(link("/index?query=" + query , query));
			p.println(newLine());
		}
		p.println(background("http://web.mst.edu/~rogersda/umrcourses/geo372/2005-03-31-359-Grand_Canyon-Desert_View_Watchtower-girls.JPG"));
		p.println(footer());
	}
	
	
	
	/*user Panel*/
	private static ArrayList<String> panel() {
		ArrayList<String> s;
		s = new ArrayList<String>();
		s.add(alignRight());
		s.add("Search History!");
		s.add(linedivider());
		s.add(link("/clearquery", "clear search history"));
		s.add(linedivider());
		s.add(link("/index", "back"));
		s.add(newLine());
		s.add(linebreak());
		s.add(endDiv());
		return s;
	}
}
