package jp.gr.java_conf.shiolier.wayn.entity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class GroupRequest implements Serializable {
	public static final String KEY_GROUP_REQUEST = "GROUP_REQUEST";

	public static final String KEY_ID = "id";
	public static final String KEY_USER = "user";
	public static final String KEY_GROUP = "group";
	public static final String KEY_REQUEST_TIME = "request_time";

	private int id;
	private User user;
	private Group group;
	private long requestTime;

	public GroupRequest() {
	}

	public GroupRequest(int groupId, int userId, String userPassword) {
		group = new Group(groupId);
		user = new User(userId, userPassword);
	}

	public GroupRequest(int id, User user, Group group, long requestTime) {
		this.id = id;
		this.user = user;
		this.group = group;
		this.requestTime = requestTime;
	}

	public GroupRequest(String jsonString) {
		try {
			setRequestDataFromJSONObject(new JSONObject(jsonString));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public GroupRequest(JSONObject jsonObject) {
		setRequestDataFromJSONObject(jsonObject);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public long getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(long requestTime) {
		this.requestTime = requestTime;
	}

	public void setRequestDataFromJSONObject(JSONObject jsonObject) {
		try {
			id = jsonObject.getInt(KEY_ID);
			user = new User(jsonObject.getJSONObject(KEY_USER));
			group = new Group(jsonObject.getJSONObject(KEY_GROUP));
			requestTime = jsonObject.getLong(KEY_REQUEST_TIME);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
