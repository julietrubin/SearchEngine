package Servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LinkPage extends Base{
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
		p.println(header("Search Site Page"));	
		for(String code: panel())
			p.println(code); 
		for(String link: DBHandler.instance().getHistory("link", id)) {
			p.println(link(link, link));
			p.println(newLine());
		}
			p.println(background("http://www.123backgrounds.com/freebg/9768.jpg"));
		p.println(footer());
	}
	
	
	/*user Panel*/
	private static ArrayList<String> panel() {
		ArrayList<String> s;
		s = new ArrayList<String>();
		s.add(alignRight());
		s.add("Site's Visted Page!");
		s.add(linedivider());
		s.add(link("/clearlink", "clear site history"));
		s.add(linedivider());
		s.add(link("/index", "back"));
		s.add(newLine());
		s.add(linebreak());
		s.add(endDiv());
		return s;
	}
}
