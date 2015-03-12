package jp.gr.java_conf.shiolier.wayn.asynctask;

import android.app.Activity;
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

import jp.gr.java_conf.shiolier.wayn.entity.Group;
import jp.gr.java_conf.shiolier.wayn.entity.GroupRequest;
import jp.gr.java_conf.shiolier.wayn.entity.User;
import jp.gr.java_conf.shiolier.wayn.fragment.ProgressDialogFragment;

public class GetGroupRequestsAsyncTask extends AsyncTask<User, Void, ArrayList<GroupRequest>> {
	private static final String URL = "http://157.7.204.152:60000/groups/request/";

	private int groupId;
	private Activity activity;
	private OnPostExecuteListener listener;
	private ProgressDialogFragment dialogFragment;

	public GetGroupRequestsAsyncTask(int groupId, Activity activity, OnPostExecuteListener listener) {
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
	protected ArrayList<GroupRequest> doInBackground(User... params) {
		String data = null;

		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(URL + groupId + ".json?" + params[0].toQueryStringForAuthWhenGet());

		try {
			data = httpClient.execute(httpGet, new ResponseHandler<String>() {
				@Override
				public String handleResponse(HttpResponse httpResponse) throws IOException {
					int statusCode = httpResponse.getStatusLine().getStatusCode();
					if (statusCode != 200) {
						Log.w("MyLog", String.format("GetGroupRequests statusCode: %d", statusCode));
						if (httpResponse.getEntity() != null) {
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

		ArrayList<GroupRequest> requestList = new ArrayList<>();
		if (data == null)	return requestList;

		try {
			JSONArray jsonArray = new JSONArray(data);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject requestJson = jsonArray.getJSONObject(i);
				GroupRequest request = new GroupRequest(requestJson);
				requestList.add(request);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return requestList;
	}

	@Override
	protected void onPostExecute(ArrayList<GroupRequest> requestList) {
		dialogFragment.getDialog().dismiss();
		listener.onPostExecute(requestList);
	}

	public interface OnPostExecuteListener {
		void onPostExecute(ArrayList<GroupRequest> requestList);
	}
}
