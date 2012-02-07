package Servlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ClearQueryHistory extends Base{

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(getValue(request, "id") == null) {
			response.sendRedirect(response.encodeRedirectURL("/index"));
			return;
		}  
		String id;
		id = getValue(request, "id");
		DBHandler.instance().clearHistory("query", id);
		response.sendRedirect(response.encodeRedirectURL("/query"));
	}
}
