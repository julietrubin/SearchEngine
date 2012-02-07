package Servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AccountPage extends Base{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName;
		if((userName = getValue(request, "name")) == null) {
			response.sendRedirect(response.encodeRedirectURL("/index"));
			return;
		}
		String status;
		status = getPar("status", request);
		status = (status == null) ? "" : status;
		PrintWriter p = prepareResp(response);
		p.println(header("Account Page"));	
		for(String code: panel(userName))
			p.println(code); 
		if(status.equals("changed"))
			p.println("<h1>Password Changed!</h1>");
		else{
			p.println(margin());
			for(String code: change(status))
				p.println(code); 
			p.println(endDiv()); 
		}
		p.println(background("http://www.zingerbug.com/Backgrounds/background_images/snow_covered_trees.jpg"));
		p.println(footer());
	}
	/*user Panel*/
	private static ArrayList<String> panel(String name) {
		ArrayList<String> s;
		s = new ArrayList<String>();
		s.add(alignRight());
		s.add("Change Password Page");
		s.add(linedivider());
		s.add(link("/index", "back"));
		s.add(linebreak());
		s.add(endDiv());
		return s;
	}
	static protected ArrayList<String> change(String status){
		ArrayList<String> rv;
		String [] error = {"","","", ""};
		String [] message = new String[4];
		rv = new ArrayList<String>();
		if(status != ""){
			error = new String[4];
			rv = new ArrayList<String>();
			message[0]="username must be at least 5 characters";
			message[1]="password must be at least 5 characters";
			message[2]="password must be at least 5 characters";
			message[3]="invalid username and/or password";
			for(int i = 0; i < 4; i++){
				error[i] = status.charAt(i) == 'F' ? message[i] : "";
			}
		}
		rv.add(alignCenter());
		rv.add(startForm("input", "/verifyaccount", "post"));
		rv.add(startTable());
		rv.addAll(row("username:", form("text", "name", "")));
		rv.addAll(row("", error(error[0])));
		rv.addAll(row("current password: ", form("password", "passwordc", "")));
		rv.addAll(row("", error(error[1])));
		rv.addAll(row("new password: ", form("password", "passwordn", "")));
		rv.addAll(row("", error(error[2])));
		rv.addAll(row("", form("submit", "change")));
		rv.addAll(row("", error(error[3])));
		rv.add(endTable());
		rv.add(endForm());
		rv.add(endDiv());
		return rv;
	}	
}





