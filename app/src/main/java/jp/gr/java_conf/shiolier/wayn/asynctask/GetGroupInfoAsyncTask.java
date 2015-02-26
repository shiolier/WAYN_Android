package jp.gr.java_conf.shiolier.wayn.asynctask;

import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import jp.gr.java_conf.shiolier.wayn.entity.Group;
import jp.gr.java_conf.shiolier.wayn.entity.User;
import jp.gr.java_conf.shiolier.wayn.fragment.ProgressDialogFragment;

public class GetGroupInfoAsyncTask extends AsyncTask<User, Void, Group> {
	private static final String URL = "http://157.7.204.152:60000/groups/info/";

	private int groupId;
	private Activity activity;
	private OnPostExecuteListener listener;
	private ProgressDialogFragment dialogFragment;

	public GetGroupInfoAsyncTask(int groupId, Activity activity, OnPostExecuteListener listener) {
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
	protected Group doInBackground(User... params) {
		String data = null;

		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(URL + groupId + ".json?" + params[0].queryStringForAuthWhenGet());

		try {
			data = httpClient.execute(httpGet, new ResponseHandler<String>() {
				@Override
				public String handleResponse(HttpResponse httpResponse) throws IOException {
					int statusCode = httpResponse.getStatusLine().getStatusCode();
					if (statusCode != 200) {
						Log.w("MyLog", String.format("GetBelongGroup statusCode: %d", statusCode));
						Log.w("MyLog", EntityUtils.toString(httpResponse.getEntity()));
						return null;
					}
					return EntityUtils.toString(httpResponse.getEntity());
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (data == null)	return new Group();

		Group group;
		try {
			group = new Group(new JSONObject(data));
		} catch (JSONException e) {
			e.printStackTrace();
			group = new Group();
		}

		return group;
	}

	@Override
	protected void onPostExecute(Group group) {
		dialogFragment.getDialog().dismiss();
		listener.onPostExecute(group);
	}

	public interface OnPostExecuteListener {
		void onPostExecute(Group group);
	}
}
