package jp.gr.java_conf.shiolier.wayn.entity;

import android.location.Location;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import jp.gr.java_conf.shiolier.wayn.util.LocationCalc;
import jp.gr.java_conf.shiolier.wayn.util.Point2D;

public class User implements Serializable {
	public static final String KEY_USER = "USER";

	public static final String KEY_ID = "id";
	public static final String KEY_NAME = "name";
	public static final String KEY_PASSWORD = "password";
	public static final String KEY_LATITUDE = "latitude";
	public static final String KEY_LONGITUDE = "longitude";
	public static final String KEY_UPDATED_LOCATION_AT = "updated_location_at";
	public static final String KEY_CREATED_AT = "created_at";

	private int id;
	private String name;
	private String password;
	private double latitude;
	private double longitude;
	private long updatedLocationAt;
	private long createdAt;

	private boolean existLocation;

	public User() {
	}

	public User(String name, String password) {
		this.name = name;
		this.password = password;
	}

	public User(int id, String password) {
		this.id = id;
		this.password = password;
	}

	public User(int id, String name, String password) {
		this(id, password);
		this.name = name;
	}

	public User(int id, String name, String password, double latitude, double longitude, int updatedLocationAt, int createdAt) {
		this(id, name, password);
		this.latitude = latitude;
		this.longitude = longitude;
		this.updatedLocationAt = updatedLocationAt;
		this.createdAt = createdAt;
	}

	public User(String jsonString) {
		try {
			setUserDataFromJSONObject(new JSONObject(jsonString));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public User(JSONObject jsonObject) {
		setUserDataFromJSONObject(jsonObject);
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

	public boolean getExistLocation() {
		return existLocation;
	}

	public void setUserDataFromJSONObject(JSONObject jsonObject) {
		try {
			id = jsonObject.getInt(KEY_ID);
			name = jsonObject.getString(KEY_NAME);
			setLocationFromJSONObject(jsonObject);
			createdAt = jsonObject.getLong(KEY_CREATED_AT);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void setLocationFromJSONObject(JSONObject jsonObject) {
		try {
			latitude = jsonObject.getDouble(KEY_LATITUDE);
			longitude = jsonObject.getDouble(KEY_LONGITUDE);
			updatedLocationAt = jsonObject.getLong(KEY_UPDATED_LOCATION_AT);
			existLocation = true;
		} catch (JSONException e) {
			e.printStackTrace();
			existLocation = false;
			latitude = 0.0;
			longitude = 0.0;
		}
	}

	public String toJsonStringForUserRegistration() {
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

	public String toJsonStringForUpdateLocaiton() {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put(KEY_ID, id);
			jsonObject.put(KEY_PASSWORD, password);
			jsonObject.put(KEY_LATITUDE, latitude);
			jsonObject.put(KEY_LONGITUDE, longitude);
		} catch (JSONException e) {
			e.printStackTrace();
			return "";
		}
		return jsonObject.toString();
	}

	public JSONObject toJSONObjectContainIdAndPassword() {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put(KEY_ID, id);
			jsonObject.put(KEY_PASSWORD, password);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	public String toQueryStringForAuthWhenGet() {
		ArrayList<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair(KEY_ID, Integer.toString(id)));
		params.add(new BasicNameValuePair(KEY_PASSWORD, password));
		return URLEncodedUtils.format(params, "UTF-8");
	}

	public Point2D getPoint2D(Location centerLocation, int meter) {
		float x = (float)(((centerLocation.getLongitude() - getLongitude()) * LocationCalc.longitudeOneDegree(centerLocation.getLongitude()) / meter));
		float y = (float)(((centerLocation.getLatitude() - getLatitude()) * LocationCalc.LATITUDE_ONE_DEGREE) / meter);

		return new Point2D(x, y);
	}
}
