package Servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginPage extends Base{
	private static final long serialVersionUID = 5302758294650892973L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(getValue(request, "id") != null) {
			response.sendRedirect(response.encodeRedirectURL("/index"));
			return;
		}
		
		String status;
		status = getPar("status", request);
		status = status == null ? "" : status;

		PrintWriter p = prepareResp(response);
		p.println(header("Login Page"));
		for(String code: panel())
			p.println(code);
		p.println(margin());
		for(String code: login(status))
			p.println(code); 
		p.println(endDiv()); 
		
		p.println(background("http://myspacebackgrounds.tv/backgrounds/flower/1.jpg"));
		p.println(footer());
	}
	
	/*user Panel*/
	private static ArrayList<String> panel() {
		ArrayList<String> s;
		s = new ArrayList<String>();
		s.add(alignRight());
		s.add("Login Page");
		s.add(linedivider());
		s.add(link("/register", "register"));
		s.add(linedivider());
		s.add(link("/index","search as guest"));
		s.add(newLine());
		s.add(linebreak());
		s.add(endDiv());
		return s;
	}

	static protected ArrayList<String> login(String status){
		ArrayList<String> rv;
		String [] error = {"","",""};
		String [] message = new String[3];
		rv = new ArrayList<String>();
		if(status != ""){
			error = new String[3];
			rv = new ArrayList<String>();
			message[0]="username must be at least 5 characters";
			message[1]="password must be at least 5 characters";
			message[2]="invalid username and/or password";
			for(int i = 0; i < 3; i++){
				error[i] = status.charAt(i) == 'F' ? message[i] : "";
			}
		}
		rv.add(alignCenter());
		rv.add(startForm("name", "/verifylogin", "post"));
		rv.add(startTable());
		rv.addAll(row("username:", form("text", "name", "")));
		rv.addAll(row("", error(error[0])));
		rv.addAll(row("password: ", form("password", "password", "")));
		rv.addAll(row("", error(error[1])));
		rv.addAll(row("", form("submit", "login")));
		rv.addAll(row("", error(error[2])));
		rv.add(endTable());
		rv.add(endForm());
		rv.add(endDiv());
		return rv;
	}		
}