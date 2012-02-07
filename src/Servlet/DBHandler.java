package Servlet;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

public class DBHandler{
	String dbname;
	String dbpassword;
	String url;
	String db;
	Properties p;
	Statement stmt;
	Connection con;
	private static final DBHandler INSTANCE = new DBHandler();

	protected static DBHandler instance() {
		return INSTANCE;
	}

	protected DBHandler(){
		FileInputStream in;

		p = new Properties();
		try {
			in = new FileInputStream("config/db.config.txt");
			p.load(in);
			in.close();
		} catch (Exception e1) {}
		dbname = p.getProperty("username");
		dbpassword = p.getProperty("password");
		url = p.getProperty("url");
		db = p.getProperty("db");
	}
	
	protected void open(){
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection(url + db, dbname, dbpassword);
			stmt = con.createStatement();
		} 
		catch (Exception e) {
			System.err.println("Can't find driver");
			System.exit(1);
		}
	}


	protected void close(){
		try {
			con.close();
			stmt.close();
		} catch (SQLException e) {}
	}


	protected boolean register(String name, String password){
		try {
			if(login(name, password) != -1)
				return false;
			open();
			stmt.executeUpdate("INSERT INTO info (name, password) VALUES (\"" + name + "\", \"" + password + "\")");
			close();
		} catch (SQLException e) {return false;}
		return true;
	}


	protected int login(String userName, String password){
		int id = -1;
		try{
			open();
			ResultSet result = results(userName);
			if(result != null && result.getString("password").contentEquals(password))
				id = result.getInt("id");
			close();
		} catch (Exception e){}
		return id;
	}

	private ResultSet results(String name){
		try {
			ResultSet result = stmt.executeQuery("SELECT * FROM info WHERE name = \"" + name + "\"");
			if(result.next())
				return result;
		} catch (SQLException e) {}
		return null;
	}



	protected void addHistory(String table, String history, String id){
		try {
			open();
			stmt.executeUpdate("INSERT INTO "+ table +" (id, history) VALUES (" + id + ", \"" + history + "\")");
			close();
		} catch (SQLException e) {}
	}

	protected ArrayList<String> getHistory(String table, String id){
		ArrayList<String> history = new ArrayList<String>();
		ResultSet result;
		try {
			open();
			result = stmt.executeQuery("SELECT * FROM "+ table +" WHERE id = " + id);
			while (result.next())
				history.add(result.getString("history"));
			close();
		} catch (SQLException e) {}
		return history;
	}

	protected void clearHistory(String table, String id){
		try {
			open();
			stmt.executeUpdate("DELETE FROM "+ table + " WHERE id = " + id);
			close();
		} catch (SQLException e) {} 
	}

	protected boolean changePassword(String name, String oldPassword, String newPassword){
		if(login(name, oldPassword) == -1)
			return false;
		try {
			open();
			stmt.executeUpdate("UPDATE info SET password = \""+ newPassword + "\" WHERE name = \"" + name+"\"");
			close();
		} catch (SQLException e) {return false;} 
		return true;
	}
}
