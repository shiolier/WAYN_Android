package jp.gr.java_conf.shiolier.wayn.asynctask;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
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
import jp.gr.java_conf.shiolier.wayn.fragment.ProgressDialogFragment;

public class UserRegistAsyncTask extends AsyncTask<String, Void, User> {
	private static final String URL = "http://v157-7-204-152.z1d5.static.cnode.jp:60000/user/regist.json";

	private Activity activity;
	private OnPostExecuteListener listener;
	private ProgressDialogFragment dialogFragment;

	public UserRegistAsyncTask(Activity activity, OnPostExecuteListener listener) {
		this.activity = activity;
		this.listener = listener;
	}

	@Override
	protected void onPreExecute() {
		dialogFragment = ProgressDialogFragment.newInstance("初期化処理中", "しばらくお待ち下さい");
		dialogFragment.show(activity.getFragmentManager(), "progress");
	}

	@Override
	protected User doInBackground(String... params) {
		String data = null;

		String postJsonStr = null;
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("name", params[0]);
			jsonObject.put("password", params[1]);
			postJsonStr = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}

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
						Log.w("MyLog", String.format("UserRegist statusCode: %d", statusCode));
						return null;
					}
					return EntityUtils.toString(httpResponse.getEntity());
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}

		User user = new User();

		try {
			JSONObject jsonObject = new JSONObject(data);
			user.setUserId(jsonObject.getInt("id"));
			user.setCreatedAt(jsonObject.getInt("created_at"));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return user;
	}

	@Override
	protected void onPostExecute(User user) {
		dialogFragment.getDialog().dismiss();
		listener.onPostExecute(user);
	}

	public interface OnPostExecuteListener {
		void onPostExecute(User user);
	}
}
