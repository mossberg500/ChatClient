package client.processing;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import client.entitie.User;

public class UserList {
	public UserList() {
	}

	public static List<User> fromJSON(InputStream input) {
		ObjectMapper mapper = new ObjectMapper();
		List<User> listOfUser = new ArrayList();
		try {
			listOfUser = (List<User>) mapper.readValue(input, List.class);
		} catch (JsonProcessingException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return listOfUser;
	}

	public static List<User> send(String url) {
		List<User> list = new ArrayList<>();
		try {
			URL obj = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

			conn.setRequestMethod("GET");
			conn.setDoOutput(true);

			if (conn.getResponseCode() == 200) {
				list = fromJSON(conn.getInputStream());
			}

		} catch (MalformedURLException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return list;
	}

}
