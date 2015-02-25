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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import jp.gr.java_conf.shiolier.wayn.entity.AppInfo;
import jp.gr.java_conf.shiolier.wayn.fragment.ProgressDialogFragment;

public class GetAppInfoAsyncTask extends AsyncTask<Void, Void, AppInfo> {
	private static final String URL = "http://157.7.204.152:60000/infos/version.json";

	private Activity activity;
	private OnPostExecuteListener listener;
	private ProgressDialogFragment dialogFragment;

	public GetAppInfoAsyncTask(Activity activity, OnPostExecuteListener listener) {
		this.activity = activity;
		this.listener = listener;
	}

	@Override
	protected void onPreExecute() {
		dialogFragment = ProgressDialogFragment.newInstance("初期化処理中", "しばらくお待ち下さい");
		dialogFragment.show(activity.getFragmentManager(), "progress");
	}

	@Override
	protected AppInfo doInBackground(Void... params) {
		String data = null;

		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(URL);

		try {
			data = httpClient.execute(httpGet, new ResponseHandler<String>() {
				@Override
				public String handleResponse(HttpResponse httpResponse) throws IOException {
					int statusCode = httpResponse.getStatusLine().getStatusCode();
					if (statusCode != 200) {
						Log.w("MyLog", String.format("GetAppInfo statusCode: %d", statusCode));
						Log.w("MyLog", EntityUtils.toString(httpResponse.getEntity()));
						return null;
					}
					return EntityUtils.toString(httpResponse.getEntity());
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}

		AppInfo appInfo = new AppInfo();
		if (data == null)	return appInfo;

		try {
			JSONObject jsonObject = new JSONObject(data);
			appInfo.setVersionCode(jsonObject.getInt(AppInfo.KEY_VERSION_CODE));
			appInfo.setVersionName(jsonObject.getString(AppInfo.KEY_VERSION_NAME));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return appInfo;
	}

	@Override
	protected void onPostExecute(AppInfo appInfo) {
		dialogFragment.getDialog().dismiss();
		listener.onPostExecute(appInfo);
	}

	public interface OnPostExecuteListener {
		void onPostExecute(AppInfo appInfo);
	}
}
