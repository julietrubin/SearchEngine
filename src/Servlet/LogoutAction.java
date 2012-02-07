package Servlet;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class LogoutAction extends Base{

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(getValue(request, "id") == null) {
			response.sendRedirect(response.encodeRedirectURL("/index"));
			return;
		}  

		for(Cookie c:  request.getCookies()) {
			c.setMaxAge(0);
			response.addCookie(c);
		}
		response.sendRedirect(response.encodeRedirectURL("/login"));
	}
}
