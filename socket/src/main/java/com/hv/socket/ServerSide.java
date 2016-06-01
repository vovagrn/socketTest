package com.hv.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.StringTokenizer;

public class ServerSide {
	
	

	private ServerSocket serverSocket;
	private Socket socket;
	
	private BufferedReader in;
	private OutputStream out;
	
	private int port;
	
	ServerSide(int port){		
		this.port = port;
	}
	
	public void start(){
		try{
			 serverSocket = new ServerSocket(port);
			
			while(true){
				socket = serverSocket.accept();
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				//System.out.println(in.readLine());				
				out = socket.getOutputStream();
				if(in.readLine().equals("stop")){
					out.write("End\n".getBytes());
					break;
				}
				out.write("hello\n".getBytes());
				out.flush();
				out.write("End\n".getBytes());
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				
				if(out!=null)out.close();
				if(in!=null)in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	private String hendler(String symbol) {	
		InputStreamReader inStream = null;
		BufferedReader buff = null;
		String ticker = null;
		String price = null;
		String tradeDate = null;
		String tradeTime = null;		
		try {
			URL url = new URL("http://quote.yahoo.com/d/quotes.csv?s=" + symbol + "&f=sl1d1t1c1ohgv&e=.csv");
			URLConnection urlConn = url.openConnection();
			inStream = new InputStreamReader(urlConn.getInputStream());
			buff = new BufferedReader(inStream);
	           
			String csvString = buff.readLine();  
	           
			StringTokenizer tokenizer = new StringTokenizer(csvString, ",");
			ticker = tokenizer.nextToken();
			price  = tokenizer.nextToken();
			tradeDate = tokenizer.nextToken();  
			tradeTime = tokenizer.nextToken(); 
			
		} catch (MalformedURLException e) {
			// NON
			e.printStackTrace();
		} catch (IOException e) {
			// NON
			e.printStackTrace();
		}	
		finally{
			 try {
				if(inStream != null) inStream.close();
				if(buff != null)buff.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "Symbol: " + ticker + " Price: " + price + " Date: "  + tradeDate + " Time: " + tradeTime;
		
	}

}
