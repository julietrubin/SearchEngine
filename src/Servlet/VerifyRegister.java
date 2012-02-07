package Servlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VerifyRegister extends Base {
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

		if(id.contentEquals("")){
			response.sendRedirect(response.encodeRedirectURL("/register?status=registered"));
			return;
		}

		response.sendRedirect(response.encodeRedirectURL("/register?status=" + id));
	}

	private String statusID(String name, String password){
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

		if(DBHandler.instance().register(name, password))
			return "";
		return "TTF";

	}
}
