package jp.gr.java_conf.shiolier.wayn.entity;

public class User {
	private int userId;
	private String name;
	private double latitude;
	private double longitude;
	private double altitude;
	private int updatedLocationAt;
	private int createdAt;

	public User() {
	}

	public User(int userId, String name, double latitude, double longitude, double altitude, int updatedLocationAt, int createdAt) {
		this.userId = userId;
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.altitude = altitude;
		this.updatedLocationAt = updatedLocationAt;
		this.createdAt = createdAt;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getAltitude() {
		return altitude;
	}

	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}

	public int getUpdatedLocationAt() {
		return updatedLocationAt;
	}

	public void setUpdatedLocationAt(int updatedLocationAt) {
		this.updatedLocationAt = updatedLocationAt;
	}

	public int getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(int createdAt) {
		this.createdAt = createdAt;
	}
}
