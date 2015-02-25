package jp.gr.java_conf.shiolier.wayn.entity;

import android.location.Location;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import jp.gr.java_conf.shiolier.wayn.util.LocationCalc;
import jp.gr.java_conf.shiolier.wayn.util.Point2D;

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
	private long updatedLocationAt;
	private long createdAt;

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

	public User(String jsonString) throws JSONException {
		this(new JSONObject(jsonString));
	}

	public User(JSONObject jsonObject) throws JSONException {
		id = jsonObject.getInt(KEY_ID);
		name = jsonObject.getString(KEY_NAME);
		latitude = jsonObject.getDouble(KEY_LATITUDE);
		longitude = jsonObject.getDouble(KEY_LONGITUDE);
		altitude = jsonObject.getDouble(KEY_ALTITUDE);
		updatedLocationAt = jsonObject.getLong(KEY_UPDATED_LOCATION_AT);
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

	public long getUpdatedLocationAt() {
		return updatedLocationAt;
	}

	public void setUpdatedLocationAt(long updatedLocationAt) {
		this.updatedLocationAt = updatedLocationAt;
	}

	public long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(long createdAt) {
		this.createdAt = createdAt;
	}

	public String jsonStringForUserRegister() {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put(KEY_NAME, name);
			jsonObject.put(KEY_PASSWORD, password);
		} catch (JSONException e) {
			e.printStackTrace();
			return "";
		}
		return jsonObject.toString();
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

	public JSONObject jsonObjectIdAndPassword() {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put(KEY_ID, id);
			jsonObject.put(KEY_PASSWORD, password);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	public String queryStringForAuthWhenGet() {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(KEY_ID, Integer.toString(id)));
		params.add(new BasicNameValuePair(KEY_PASSWORD, password));
		return URLEncodedUtils.format(params, "UTF-8");
	}

	public Point2D getPoint2D(Location currentLocation, int meter) {
		float x = (float)(((currentLocation.getLongitude() - getLongitude()) * LocationCalc.longitudeOneDegree(currentLocation.getLongitude()) / meter));
		float y = (float)(((currentLocation.getLatitude() - getLatitude()) * LocationCalc.LATITUDE_ONE_DEGREE) / meter);

		return new Point2D(x, y);
	}
}
