package Servlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class VerifyLogin extends Base {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name;
		String password;
		String id = null;

		if(getValue(request, "id") != null){
			response.sendRedirect(response.encodeRedirectURL("/index"));
			return;
		}
		name = request.getParameter("name");
		password = request.getParameter("password");

		id = statusID(name, password);
		if(id.matches("[TF][TF][TF]")) {
			response.sendRedirect(response.encodeRedirectURL("/login?status=" + id));
			return;
		}

		response.addCookie(new Cookie("name", name));
		response.addCookie(new Cookie("id", id));
		response.sendRedirect(response.encodeRedirectURL("/index"));
	}

	private String statusID(String name, String password){
		Integer id;
		boolean nameEr;
		boolean pasEr;

		nameEr = (name == null || name.trim().length() < 5);
		pasEr = (password == null || password.trim().length() < 5);

		if(nameEr && pasEr)
			return "FFT";
		if(nameEr)
			return "FTT";
		if(pasEr)
			return "TFT";

		id = DBHandler.instance().login(name, password);
		if(id == -1)
			return "TTF";
		return id.toString();
	}
}
