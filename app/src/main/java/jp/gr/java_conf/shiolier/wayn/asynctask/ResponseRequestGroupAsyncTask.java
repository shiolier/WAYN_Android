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

public class ResponseRequestGroupAsyncTask extends AsyncTask<User, Void, Boolean> {
	private static final String URL = "http://157.7.204.152:60000/groups/";

	private int requestId;
	private boolean approve;
	private Activity activity;
	private OnPostExecuteListener listener;
	private ProgressDialogFragment dialogFragment;

	public ResponseRequestGroupAsyncTask(int requestId, boolean approve, Activity activity, OnPostExecuteListener listener) {
		this.requestId = requestId;
		this.approve = approve;
		this.activity = activity;
		this.listener = listener;
	}

	@Override
	protected void onPreExecute() {
		dialogFragment = ProgressDialogFragment.newInstance("退会中", "しばらくお待ち下さい");
		dialogFragment.show(activity.getFragmentManager(), "progress");
	}

	@Override
	protected Boolean doInBackground(User... params) {
		String data = null;

		String url;
		if (approve) {
			url =  URL + "approve/";
		} else {
			url =  URL + "reject/";
		}

		url += requestId + ".json";

		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		try {
			httpPost.setEntity(new StringEntity(params[0].toJSONObjectContainIdAndPassword().toString(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		try {
			data = httpClient.execute(httpPost, new ResponseHandler<String>() {
				@Override
				public String handleResponse(HttpResponse httpResponse) throws IOException {
					int statusCode = httpResponse.getStatusLine().getStatusCode();
					if (statusCode != 200) {
						Log.w("MyLog", String.format("LeaveGroup statusCode: %d", statusCode));
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

