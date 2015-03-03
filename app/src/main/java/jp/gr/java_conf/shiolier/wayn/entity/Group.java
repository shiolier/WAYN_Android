package jp.gr.java_conf.shiolier.wayn.entity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Group implements Serializable {
	public static final String KEY_GROUP = "GROUP";

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

	public Group(int id) {
		this.id = id;
	}

	public Group(String name, User leader) {
		this.name = name;
		this.leader = leader;
	}

	public Group(String groupName, int leaderId, String leaderPassword) {
		this(groupName, new User(leaderId, leaderPassword));
	}

	public Group(int id, String name, User leader, long createdAt) {
		this(name, leader);
		this.id = id;
		this.name = name;
		this.leader = leader;
		this.createdAt = createdAt;
	}

	public Group(String jsonString) {
		try {
			setGroupDataFromJSONObject(new JSONObject(jsonString));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public Group(JSONObject jsonObject) {
		setGroupDataFromJSONObject(jsonObject);
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

	public void setGroupDataFromJSONObject(JSONObject jsonObject) {
		try {
			id = jsonObject.getInt(KEY_ID);
			name = jsonObject.getString(KEY_NAME);
			leader = new User(jsonObject.getJSONObject(KEY_LEADER));
			createdAt = jsonObject.getLong(KEY_CREATED_AT);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public String toJsonStringForCreateGroup() {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put(User.KEY_ID, leader.getId())
					.put(User.KEY_PASSWORD, leader.getPassword())
					.put("group", new JSONObject().put(KEY_NAME, name));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject.toString();
	}
}
