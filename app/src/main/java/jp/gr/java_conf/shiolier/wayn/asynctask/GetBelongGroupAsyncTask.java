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
import jp.gr.java_conf.shiolier.wayn.entity.User;
import jp.gr.java_conf.shiolier.wayn.fragment.ProgressDialogFragment;

public class GetBelongGroupAsyncTask extends AsyncTask<User, Void, ArrayList<Group>> {
	private static final String URL = "http://157.7.204.152:60000/groups/belong.json";

	private Activity activity;
	private OnPostExecuteListener listener;
	private ProgressDialogFragment dialogFragment;

	public GetBelongGroupAsyncTask(Activity activity, OnPostExecuteListener listener) {
		this.activity = activity;
		this.listener = listener;
	}

	@Override
	protected void onPreExecute() {
		dialogFragment = ProgressDialogFragment.newInstance("通信中", "しばらくお待ち下さい");
		dialogFragment.show(activity.getFragmentManager(), "progress");
	}

	@Override
	protected ArrayList<Group> doInBackground(User... params) {
		String data = null;

		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(URL + "?" + params[0].queryStringForAuthWhenGet());

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

		ArrayList<Group> groupList = new ArrayList<>();
		if (data == null)	return groupList;

		try {
			JSONArray jsonArray = new JSONArray(data);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject groupJson = jsonArray.getJSONObject(i);
				groupList.add(new Group(groupJson));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return groupList;
	}

	@Override
	protected void onPostExecute(ArrayList<Group> groupList) {
		dialogFragment.getDialog().dismiss();
		listener.onPostExecute(groupList);
	}

	public interface OnPostExecuteListener {
		void onPostExecute(ArrayList<Group> groupList);
	}
}
