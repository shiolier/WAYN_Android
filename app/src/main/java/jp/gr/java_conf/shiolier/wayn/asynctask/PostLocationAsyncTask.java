package jp.gr.java_conf.shiolier.wayn.asynctask;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import jp.gr.java_conf.shiolier.wayn.entity.User;

public class PostLocationAsyncTask extends AsyncTask<User, Void, User> {
	private static final String URL = "http://157.7.204.152:60000/locations/update.json";

	private OnPostExecuteListener listener;

	public PostLocationAsyncTask(OnPostExecuteListener listener) {
		this.listener = listener;
	}

	@Override
	protected User doInBackground(User... params) {
		String data = null;

		User user = params[0];
		String postJsonStr = user.toJsonStringForUpdateLocaiton();

		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(URL);
		try {
			httpPost.setEntity(new StringEntity(postJsonStr, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		try {
			data = httpClient.execute(httpPost, new ResponseHandler<String>() {
				@Override
				public String handleResponse(HttpResponse httpResponse) throws IOException {
					int statusCode = httpResponse.getStatusLine().getStatusCode();
					if (statusCode != 200) {
						Log.w("MyLog", String.format("PostLocation statusCode: %d", statusCode));
						Log.w("MyLog", EntityUtils.toString(httpResponse.getEntity()));
						return null;
					}
					return EntityUtils.toString(httpResponse.getEntity());
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}


		if (data == null)	return user;

		try {
			JSONObject jsonObject = new JSONObject(data);
			user.setUpdatedLocationAt(jsonObject.getInt(User.KEY_UPDATED_LOCATION_AT));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return user;
	}

	@Override
	protected void onPostExecute(User user) {
		listener.onPostExecute(user);
	}

	public interface OnPostExecuteListener {
		void onPostExecute(User user);
	}
}
