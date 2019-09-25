package client.processing;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import client.entitie.JsonMessages;
import client.entitie.Message;
import client.entitie.User;
import client.source.Utils;

public class GetThread implements Runnable {

	private final Gson gson;
	private int n;
	private User user;

	public GetThread(User user) {
		gson = new GsonBuilder().create();
		this.user = user;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				URL url = new URL(Utils.getURL() + "/get?from=" + n + "&room=" + user.getRoom());
				HttpURLConnection http = (HttpURLConnection) url.openConnection();
				InputStream is = http.getInputStream();
				try {
					byte[] buf = requestBodyToArray(is);
					String strBuf = new String(buf, StandardCharsets.UTF_8);
					List<Message> list = gson.fromJson(strBuf, new TypeToken<List<Message>>() {
					}.getType());
					if (list != null) {
						for (Message m : list) {
							System.out.println(m);
							n++;
						}
					}
				} finally {
					is.close();
				}
				Thread.sleep(500);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private byte[] requestBodyToArray(InputStream is) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buf = new byte[10240];
		int r;
		do {
			r = is.read(buf);
			if (r > 0) {
				bos.write(buf, 0, r);
			}
		} while (r != -1);

		return bos.toByteArray();
	}
}
