package jp.gr.java_conf.shiolier.wayn.entity;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
	public static final String KEY_ID = "id";
	public static final String KEY_NAME = "name";
	public static final String KEY_PASSWORD = "password";
	public static final String KEY_LATITUDE = "latitude";
	public static final String KEY_LONGITUDE = "longitude";
	public static final String KEY_ALTITUDE = "altitude";
	public static final String KEY_UPDATED_LOCATION_AT = "updated_location_at";
	public static final String KEY_CREATED_AT = "created_at";

	private int id;
	private String name;
	private String password;
	private double latitude;
	private double longitude;
	private double altitude;
	private int updatedLocationAt;
	private int createdAt;

	public User() {
	}

	public User(int id, String name, String password, double latitude, double longitude, double altitude, int updatedLocationAt, int createdAt) {
		this.id = id;
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.altitude = altitude;
		this.updatedLocationAt = updatedLocationAt;
		this.createdAt = createdAt;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String jsonStringForUpdateLocaiton() {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put(KEY_ID, id);
			jsonObject.put(KEY_PASSWORD, password);
			jsonObject.put(KEY_LATITUDE, latitude);
			jsonObject.put(KEY_LONGITUDE, longitude);
			jsonObject.put(KEY_ALTITUDE, altitude);
		} catch (JSONException e) {
			e.printStackTrace();
			return "";
		}
		return jsonObject.toString();
	}
}
