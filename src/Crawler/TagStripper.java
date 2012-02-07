package Crawler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.StringTokenizer;



public class TagStripper implements Runnable{
	private Crawler c;
	private String homepage; 
	private String extention;
	private int position;
	private State state;

	private enum State {AT_VALID_LEFT_TAG_BRACKET, AT_POS_VALID_LEFT_TAG_BRACKET, 
		IN_VALID_TEXT, IN_A_TAG, IN_S_TAG_OR_S_BLOCK, IN_OTHER_TAG_OR_END_TAG}

	public TagStripper(String homepage, String extention, Crawler c) {
		this.c = c;
		this.homepage = homepage;
		this.extention = extention;
		this.position = 0;
		this.state = State.IN_VALID_TEXT;
	}

	public void run(){
		String line;
		StringTokenizer st;
		StringTokenizer st2;
		BufferedReader br; 
		Socket s;
		PrintWriter out;

		br = null;
		line = null;
		try {
			s = new Socket(InetAddress.getByName(homepage), 80);
			out = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
			out.println("GET " + extention + " HTTP/1.0 \n");
			out.flush(); 
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));     
			line = br.readLine();

			if (!line.contains("200")) 
				return;
			line = br.readLine();
			while (line != null) {
			//	System.out.println(line);
				st = new StringTokenizer(line); 
				while(st.hasMoreTokens()) {
					st2 = new StringTokenizer(st.nextToken(),"< >", true); 
					while (st2.hasMoreTokens())
						newToken(st2.nextToken());
				} line = br.readLine();

			} br.close();
		} catch (IOException e) {}
	}

	private void newToken(String token) {
		switch(state) {
		case AT_VALID_LEFT_TAG_BRACKET:
			function(token);
			break;
		case IN_VALID_TEXT:
			function2(token);
			break;
		case IN_S_TAG_OR_S_BLOCK:
			if(token.equals("<"))
				state = State.AT_POS_VALID_LEFT_TAG_BRACKET;
			break;
		case AT_POS_VALID_LEFT_TAG_BRACKET:
			if (token.equalsIgnoreCase("/script") || token.equalsIgnoreCase("/style"))
				state = State.IN_OTHER_TAG_OR_END_TAG;
			else
				state = State.IN_S_TAG_OR_S_BLOCK;				
			break;
		case IN_A_TAG:
			if (token.contains("href")) {
				c.addLink(homepage, token.trim());
				break;
			}
		case IN_OTHER_TAG_OR_END_TAG:
			if (token.equals(">"))
				state = State.IN_VALID_TEXT;
			break;
		}
	}

	private void function(String token) {
		if (token.equalsIgnoreCase("a"))
			state = State.IN_A_TAG;
		else if (token.equalsIgnoreCase("script") || token.equalsIgnoreCase("style"))
			state =  State.IN_S_TAG_OR_S_BLOCK;
		else
			state = State.IN_OTHER_TAG_OR_END_TAG;
	}

	private void function2(String token) {
		if (token.equals("<"))
			state = State.AT_VALID_LEFT_TAG_BRACKET;
		else {
			c.addWordtoStructure(token, homepage + extention, position);
			position++;
		}
	}
}