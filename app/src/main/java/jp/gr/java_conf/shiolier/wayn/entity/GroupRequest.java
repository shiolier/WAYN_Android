package jp.gr.java_conf.shiolier.wayn.entity;

public class GroupRequest {
	private int id;
	private User user;
	private int requestTime;

	public GroupRequest() {
	}

	public GroupRequest(int id, User user, int requestTime) {
		this.id = id;
		this.user = user;
		this.requestTime = requestTime;
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

	public int getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(int requestTime) {
		this.requestTime = requestTime;
	}
}
