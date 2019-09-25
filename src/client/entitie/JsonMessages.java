package client.entitie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JsonMessages {

	private final List<Message> list;

	public JsonMessages(List<Message> list) {
		super();
		this.list = new ArrayList<>();
	}

	public List<Message> getList() {
		return Collections.unmodifiableList(list);
	}
}
