package Servlet;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.bio.SocketConnector;
import org.mortbay.jetty.servlet.ServletHandler;
import Crawler.Crawler;



public class Driver {
	public static void main(String[] args) throws Exception {
		Crawler c = Crawler.instance();
		c.addLink("www.cnn.com", "");
		c.addLink("www.popoholic.com", "");
		Thread.sleep(20000);
		Crawler.instance().stop();

		Server server = new Server();
		Connector connector = new SocketConnector();
		connector.setPort(8080);
		server.setConnectors(new Connector[]{connector});
		ServletHandler handler=new ServletHandler();
		server.setHandler(handler);
		handler.addServletWithMapping("Servlet.HomePage", "/");
		handler.addServletWithMapping("Servlet.Move", "/move");
		handler.addServletWithMapping("Servlet.HomePage", "/index");
		handler.addServletWithMapping("Servlet.AccountPage", "/account");
		handler.addServletWithMapping("Servlet.ClearLinkHistory", "/clearlink");
		handler.addServletWithMapping("Servlet.ClearQueryHistory", "/clearquery");
		handler.addServletWithMapping("Servlet.VerifyAccount", "/verifyaccount");
		handler.addServletWithMapping("Servlet.QueryPage", "/query");
		handler.addServletWithMapping("Servlet.LinkPage", "/link");
		handler.addServletWithMapping("Servlet.LogoutAction", "/logout");
		handler.addServletWithMapping("Servlet.LoginPage", "/login");
		handler.addServletWithMapping("Servlet.RegisterPage", "/register");
		handler.addServletWithMapping("Servlet.VerifyRegister", "/verifyregister");
		handler.addServletWithMapping("Servlet.VerifyLogin", "/verifylogin");
		server.start();
		server.join();
	}
}
