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

import jp.gr.java_conf.shiolier.wayn.entity.Group;
import jp.gr.java_conf.shiolier.wayn.fragment.ProgressDialogFragment;

public class CreateGroupAsyncTask extends AsyncTask<Group, Void, Group> {
	private static final String URL = "http://157.7.204.152:60000/groups/create.json";

	private Activity activity;
	private OnPostExecuteListener listener;
	private ProgressDialogFragment dialogFragment;

	public CreateGroupAsyncTask(Activity activity, OnPostExecuteListener listener) {
		this.activity = activity;
		this.listener = listener;
	}

	@Override
	protected void onPreExecute() {
		dialogFragment = ProgressDialogFragment.newInstance("グループ作成中", "しばらくお待ち下さい");
		dialogFragment.show(activity.getFragmentManager(), "progress");
	}
	@Override
	protected Group doInBackground(Group... params) {
		String data = null;

		Group group = params[0];
		String postJsonStr = group.toJsonStringForCreateGroup();

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
						Log.w("MyLog", String.format("CreateGroup statusCode: %d", statusCode));
						Log.w("MyLog", EntityUtils.toString(httpResponse.getEntity()));
						return null;
					}
					return EntityUtils.toString(httpResponse.getEntity());
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}


		if (data == null)	return group;

		try {
			JSONObject jsonObject = new JSONObject(data);
			group.setId(jsonObject.getInt(Group.KEY_ID));
			group.setCreatedAt(jsonObject.getLong(Group.KEY_CREATED_AT));
		} catch (JSONException e) {
			e.printStackTrace();
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
