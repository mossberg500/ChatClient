package client.processing;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import client.entitie.Message;
import client.entitie.User;
import client.source.Utils;

public class Main {
	public static void main(String[] args) {
		try (Scanner scanner = new Scanner(System.in)) {
			User user = new User();

			while (user.getStatus().equals("off")) {
				System.out.println("Enter your login: ");
				String login = scanner.nextLine();
				System.out.println("Enter your password");
				String pass = scanner.nextLine();

				// Veiw all user
				List<User> users = UserList.send(Utils.getURL() + "/users");
				if (!users.isEmpty()) {
					System.out.println(users.toString());
				}
				System.out.println("Select Chat-Room");
				System.out.println("Write '1' to select 1 room or '2' to select 2 room else room all ");
				String str = scanner.nextLine();
				String room = "";
				if (str == null || str.isEmpty()) {
					room = "all";
				}
				if (str.equals("1")) {
					room = "1";
				} else if (str.equals("2")) {
					room = "2";
				} else
					room = "all";
				user.setRoom(room);
				System.out.println("Your  room  = " + room);
				user = Login.send(Utils.getURL() + "/login" + "?login=" + login + "&pass=" + pass + "&room=" + room);

				if (null == user) {
					System.out.println("Ooops.  Try again");
				} else {
					user.setStatus("active");
				}
			}

			Thread th = new Thread(new GetThread(user));
			th.setDaemon(true);
			th.start();

			System.out.println("Enter 'to' user login whom the message will sent");
			String to = scanner.nextLine();

			System.out.println("Enter your message or 'show' for show all users: ");
			while (user.getStatus().equals("active")) {
				String text = scanner.nextLine();
				if (text.isEmpty()) {
					continue;
				}
				if (text.equalsIgnoreCase("off")) {
					user.setStatus("off");
					Login.sendrem(Utils.getURL() + "/loginrem" + "?login=" + user.getName() );
				}
				
				if (text.equalsIgnoreCase("show")) {
					List<User> users = UserList.send(Utils.getURL() + "/users");
					if (!users.isEmpty()) {
						System.out.println(users.toString());
					}
				}
				if (text.equalsIgnoreCase("to")) {
					to = scanner.nextLine();
					System.out.println("Enter the masssage " + to);
					text = scanner.nextLine();
					if (text.isEmpty()) {
						continue;
					}
				}
				Message m = new Message(user.getName(), to, text, user.getRoom());
				int res = m.send(Utils.getURL() + "/add");
				if (res != 200) { // 200 OK
					System.out.println("HTTP error occured: " + res);
					return;
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
