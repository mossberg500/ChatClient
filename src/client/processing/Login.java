package client.processing;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import client.entitie.User;

public class Login {
	  
	    public static User fromJson(InputStream inputStream) throws IOException {
	        ObjectMapper mapper = new ObjectMapper();
	        User user = null;
	        try {
	            user = mapper.readValue(inputStream, User.class);
	        } catch (JsonProcessingException ex) {
	            ex.printStackTrace();
	        }
	        return user;
	    }

	    public static User send(String url) {
	        User user = null;

	        try {
	            URL obj = new URL(url);
	            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

	            conn.setRequestMethod("GET");
	            conn.setDoOutput(true);           

	            if (conn.getResponseCode() == 200) {
	                user = fromJson(conn.getInputStream());
	            }

	        } catch (MalformedURLException ex) {
	            ex.printStackTrace();
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
	        return user;
	    }
	    public static void sendrem(String url) {
	    	try {
	    		URL obj = new URL(url); 
	    		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	    		con.setRequestMethod("GET");
	    		con.setDoOutput(true);
	    		if (con.getResponseCode() == 200) {
	                System.out.println("Disconnect");
	            }
	    		
	    	}catch (IOException e) {
				e.printStackTrace();
			}
			
		}
}
