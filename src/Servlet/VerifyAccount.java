package Servlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class VerifyAccount extends Base {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name;
		String passwordc;
		String passwordn;
		String id = null;

		if(getValue(request, "id") == null){
			response.sendRedirect(response.encodeRedirectURL("/index"));
			return;
		}
		name = request.getParameter("name");
		passwordc = request.getParameter("passwordc");
		passwordn = request.getParameter("passwordn");
		id = statusID(name, passwordc, passwordn);

		if(!id.matches("TTTT")) {
			response.sendRedirect(response.encodeRedirectURL("/account?status=" + id));
			return;
		}
		response.sendRedirect(response.encodeRedirectURL("/account?status=changed"));
	}

	private String statusID(String name, String passwordc, String passwordn){
		String id = "";
		boolean nameEr;
		boolean pasErc;
		boolean pasErn;

		nameEr = (name == null || name.trim().length() < 5);
		pasErc = (passwordc == null || passwordc.trim().length() < 5);
		pasErn = (passwordn == null || passwordn.trim().length() < 5);

		if(nameEr)
			id += "F";
		else
			id += "T";
		if(pasErc)
			id += "F";
		else
			id += "T";
		if(pasErn)
			id += "F";
		else
			id += "T";

		if(id.equals("TTT")) {
			if(DBHandler.instance().changePassword(name, passwordc, passwordn))
				id += "T";
			else 
				id+= "F";
		}
		else 
			id += "T";
		return id;
	}
}
