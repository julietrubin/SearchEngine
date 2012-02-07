package Servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Crawler.Crawler;

public class HomePage extends Base {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName;
		String query;
		String id;

		userName = getValue(request, "name");
		id = getValue(request, "id");
		query = request.getParameter("query");
		
		ArrayList<String> HTML = new ArrayList<String>();
		HTML.addAll((userName == null) ?  guestPanel(userName) : userPanel(userName));
		HTML.addAll(searchBox());
		HTML.addAll(results(id, query));

	
		response.setStatus(HttpServletResponse.SC_OK);
		PrintWriter p = prepareResp(response);
		p.println(header("Search Page"));	
		for(String s: HTML)
			p.println(s);
		p.println(background("http://www.kingston.ac.uk/includes/img/international/World-map-without-dots.gif"));
		p.println("</div>");
		p.println(footer());
	}

	/*searchBox*/
	private static ArrayList<String> searchBox(){
		ArrayList<String> s = new ArrayList<String>();
		s.add(alignCenter());
		s.add(startForm("query",  "/search",  "get"));
		s.add(startTable("100%"));
		s.addAll(row(""));
		s.addAll(row("<h1>Search!</h1>"));
		s.addAll(row(form("text", "query", "", 62)));
		s.addAll(row(form("submit", "search")));
		s.add(endTable());
		s.add(endForm());
		s.add(endDiv());
		return  s;
	}
	
	/*result links*/
	private static ArrayList<String> results(String id, String query){
		ArrayList<String> s;
		s = new ArrayList<String>();

		if(query != null && !query.contentEquals("")){
			DBHandler.instance().addHistory("query", query, id);
			s.add(newLine());
			for(String link: Crawler.instance().search(query.toLowerCase())){
				s.add(link("/move?link=" + link, link));
				s.add(newLine());
				s.add(newLine());
			}
		}
		return s;
	}

	/*guestPanel*/
	private static ArrayList<String> guestPanel(String name) {
		ArrayList<String> s;
		s = new ArrayList<String>();
		s.add(alignRight());
		s.add("Home Page");
		s.add(linedivider());
		s.add(link("/login", "login"));
		s.add(linedivider());
		s.add(link("/register", "register"));
		s.add(linebreak());
		s.add(endDiv());
		return s;
	}

	/*user Panel*/
	private static ArrayList<String> userPanel(String name) {
		ArrayList<String> s;
		s = new ArrayList<String>();
		s.add(alignRight());
		s.add("Welcome" + space() + name + "!");
		s.add(linedivider());
		s.add(link("/query", "search history"));
		s.add(linedivider());
		s.add(link("/link", "site's visted"));
		s.add(linedivider());
		s.add(link("/account", "account"));
		s.add(linedivider());
		s.add(link("/logout", "logout"));
		s.add(linebreak());
		s.add(endDiv());
		return s;
	}
}


