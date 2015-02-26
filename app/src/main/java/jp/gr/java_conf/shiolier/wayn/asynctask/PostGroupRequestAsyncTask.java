package jp.gr.java_conf.shiolier.wayn.asynctask;

import android.app.Activity;
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
import jp.gr.java_conf.shiolier.wayn.fragment.ProgressDialogFragment;

public class PostGroupRequestAsyncTask extends AsyncTask<User, Void, Boolean> {
	private static final String URL = "http://157.7.204.152:60000/groups/request/";

	private int groupId;
	private Activity activity;
	private OnPostExecuteListener listener;
	private ProgressDialogFragment dialogFragment;

	public PostGroupRequestAsyncTask(int groupId, Activity activity, OnPostExecuteListener listener) {
		this.groupId = groupId;
		this.activity = activity;
		this.listener = listener;
	}

	@Override
	protected void onPreExecute() {
		dialogFragment = ProgressDialogFragment.newInstance("通信中", "しばらくお待ち下さい");
		dialogFragment.show(activity.getFragmentManager(), "progress");
	}

	@Override
	protected Boolean doInBackground(User... params) {
		String data = null;

		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(URL + groupId + ".json");
		try {
			httpPost.setEntity(new StringEntity(params[0].jsonObjectIdAndPassword().toString(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		try {
			data = httpClient.execute(httpPost, new ResponseHandler<String>() {
				@Override
				public String handleResponse(HttpResponse httpResponse) throws IOException {
					int statusCode = httpResponse.getStatusLine().getStatusCode();
					if (statusCode != 200) {
						Log.w("MyLog", String.format("PostGroupRequest statusCode: %d", statusCode));
						Log.w("MyLog", EntityUtils.toString(httpResponse.getEntity()));
						return null;
					}
					return EntityUtils.toString(httpResponse.getEntity());
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (data == null)	return false;

		boolean result = false;
		try {
			JSONObject jsonObject = new JSONObject(data);
			result = jsonObject.getBoolean("result");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return result;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		dialogFragment.getDialog().dismiss();
		listener.onPostExecute(result);
	}

	public interface OnPostExecuteListener {
		void onPostExecute(boolean result);
	}
}
