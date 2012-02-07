package Servlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Move extends Base{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id;
		String link;

		link = request.getParameter("link");

		if(getValue(request, "id") != null) {
			id = getValue(request, "id");
			DBHandler.instance().addHistory("link", link, id);
		}

		response.sendRedirect(response.encodeRedirectURL(link));
	}
}
