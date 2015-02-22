package jp.gr.java_conf.shiolier.wayn.entity;

public class GroupRequest {
	private int id;
	private User user;
	private long requestTime;

	public GroupRequest() {
	}

	public GroupRequest(int id, User user, long requestTime) {
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

	public long getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(long requestTime) {
		this.requestTime = requestTime;
	}
}
