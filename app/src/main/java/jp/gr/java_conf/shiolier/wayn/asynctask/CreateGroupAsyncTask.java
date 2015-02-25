package jp.gr.java_conf.shiolier.wayn.asynctask;

import android.app.Activity;
import android.os.AsyncTask;

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
		return null;
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
