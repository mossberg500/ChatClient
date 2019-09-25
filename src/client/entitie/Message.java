package client.entitie;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Message {

	private String date = (new SimpleDateFormat("yyyy.MM.dd hh:mm:ss ")).format(new Date());
	private String from;
	private String to;
	private String text;
	private String room;
	private static ObjectMapper mapper = new ObjectMapper();

	public Message() {
	}

	public Message(String from, String to, String text, String room) {
		this.from = from;
		this.to = to;
		this.text = text;
		this.room = room;
	}

	public String toJSON() {
		String json = "";
		try {
			json = mapper.writeValueAsString(this);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return json;
	}

	public static Message fromJSON(String s) {
		Message message = null;
		try {
			message = mapper.readValue(s, Message.class);

		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return message;
	}

	@Override
	public String toString() {
		return new StringBuilder().append("[").append(" Date: ").append(date).append(", From: ").append(from)
				.append(", Room: ").append(room).append(", To: ").append(to).append("] ").append(text).toString();
	}

	public int send(String url) throws IOException {
		URL obj = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

		conn.setRequestMethod("POST");
		conn.setDoOutput(true);

		try (OutputStream os = conn.getOutputStream()) {
			String json = toJSON();
			os.write(json.getBytes(StandardCharsets.UTF_8));
			return conn.getResponseCode();
		}
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}
}
