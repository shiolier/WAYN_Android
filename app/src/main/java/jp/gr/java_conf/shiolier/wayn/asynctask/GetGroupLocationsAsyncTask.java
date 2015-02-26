package jp.gr.java_conf.shiolier.wayn.asynctask;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import jp.gr.java_conf.shiolier.wayn.entity.User;

public class GetGroupLocationsAsyncTask extends AsyncTask<User, Void, ArrayList<User>> {
	private static final String URL = "http://157.7.204.152:60000/locations/group/";

	private int groupId;
	private OnPostExecuteListener listener;

	public GetGroupLocationsAsyncTask(int groupId, OnPostExecuteListener listener) {
		this.groupId = groupId;
		this.listener = listener;
	}

	@Override
	protected ArrayList<User> doInBackground(User... params) {
		String data = null;

		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(URL + groupId + ".json?" + params[0].queryStringForAuthWhenGet());

		try {
			data = httpClient.execute(httpGet, new ResponseHandler<String>() {
				@Override
				public String handleResponse(HttpResponse httpResponse) throws IOException {
					int statusCode = httpResponse.getStatusLine().getStatusCode();
					if (statusCode != 200) {
						Log.w("MyLog", String.format("GetGroupLocations statusCode: %d", statusCode));
						if (statusCode != 204) {
							Log.w("MyLog", EntityUtils.toString(httpResponse.getEntity()));
						}
						return null;
					}
					return EntityUtils.toString(httpResponse.getEntity());
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}

		ArrayList<User> userList = new ArrayList<>();

		if (data == null)	return userList;

		try {
			JSONArray jsonArray = new JSONArray(data);
			for (int i = 0; i < jsonArray.length(); i++) {
				User user = new User();

				JSONObject userJson = jsonArray.getJSONObject(i);
				user.setId(userJson.getInt(User.KEY_ID));
				user.setName(userJson.getString(User.KEY_NAME));
				user.setLatitude(userJson.getDouble(User.KEY_LATITUDE));
				user.setLongitude(userJson.getDouble(User.KEY_LONGITUDE));
				user.setAltitude(userJson.getDouble(User.KEY_ALTITUDE));
				user.setUpdatedLocationAt(userJson.getLong(User.KEY_UPDATED_LOCATION_AT));
				user.setCreatedAt(userJson.getLong(User.KEY_CREATED_AT));

				userList.add(user);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return userList;
	}

	@Override
	protected void onPostExecute(ArrayList<User> userList) {
		listener.onPostExecute(userList);
	}

	public interface OnPostExecuteListener {
		void onPostExecute(ArrayList<User> userList);
	}
}
