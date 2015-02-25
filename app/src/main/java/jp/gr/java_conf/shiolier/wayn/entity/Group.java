package jp.gr.java_conf.shiolier.wayn.entity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Group implements Serializable {
	public static final String KEY_ID = "id";
	public static final String KEY_NAME = "name";
	public static final String KEY_LEADER = "leader";
	public static final String KEY_CREATED_AT = "created_at";

	private int id;
	private String name;
	private User leader;
	private long createdAt;

	public Group() {
	}

	public Group(int id, String name, User leader, long createdAt) {
		this.id = id;
		this.name = name;
		this.leader = leader;
		this.createdAt = createdAt;
	}

	public Group(String jsonString) throws JSONException {
		this(new JSONObject(jsonString));
	}

	public Group(JSONObject jsonObject) throws JSONException {
		id = jsonObject.getInt(KEY_ID);
		name = jsonObject.getString(KEY_NAME);
		leader = new User(jsonObject.getJSONObject(KEY_LEADER));
		createdAt = jsonObject.getLong(KEY_CREATED_AT);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getLeader() {
		return leader;
	}

	public void setLeader(User leader) {
		this.leader = leader;
	}

	public long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(long createdAt) {
		this.createdAt = createdAt;
	}

	public void setForGroupCreate(String groupName, int userId, String userPassword) {
		name = groupName;
		leader = new User(userId, userPassword);
	}

	public String jsonStringForCreateGroup() {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put(User.KEY_ID, leader.getId());
			jsonObject.put(User.KEY_PASSWORD, leader.getPassword());
			jsonObject.put("group_name", name);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject.toString();
	}
}
