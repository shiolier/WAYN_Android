package jp.gr.java_conf.shiolier.wayn;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import jp.gr.java_conf.shiolier.wayn.asynctask.GetGroupInfoAsyncTask;
import jp.gr.java_conf.shiolier.wayn.asynctask.PostGroupRequestAsyncTask;
import jp.gr.java_conf.shiolier.wayn.entity.Group;
import jp.gr.java_conf.shiolier.wayn.util.MySharedPref;

public class GroupSearchActivity extends ActionBarActivity {
	private int groupId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_search);

		Button btnRequest = (Button)findViewById(R.id.btn_request);
		btnRequest.setEnabled(false);
		btnRequest.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				setRequestButtonEnable(false);

				postGroupRequest();
			}
		});

		Button btnSearch = (Button)findViewById(R.id.btn_search);
		btnSearch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				setRequestButtonEnable(false);

				EditText editText = (EditText)findViewById(R.id.edt_group_id);
				getGroupInfo(Integer.parseInt(editText.getText().toString()));
			}
		});
	}

	private void getGroupInfo(int groupId) {
		GetGroupInfoAsyncTask asyncTask = new GetGroupInfoAsyncTask(groupId, GroupSearchActivity.this, new GetGroupInfoAsyncTask.OnPostExecuteListener() {
			@Override
			public void onPostExecute(Group group) {
				if (group.getId() != 0) {
					GroupSearchActivity.this.groupId = group.getId();
					showGroupInfo(group);
				} else {
					Toast.makeText(GroupSearchActivity.this, "情報取得失敗", Toast.LENGTH_SHORT).show();
				}
			}
		});

		asyncTask.execute(new MySharedPref(this).getUser());
	}

	private void showGroupInfo(Group group) {
		TextView txtGroupId = (TextView)findViewById(R.id.txt_group_id);
		txtGroupId.setText(Integer.toString(group.getId()));
		TextView txtGroupName = (TextView)findViewById(R.id.txt_group_name);
		txtGroupName.setText(group.getName());

		TextView txtLeaderId = (TextView)findViewById(R.id.txt_leader_id);
		txtLeaderId.setText(Integer.toString(group.getLeader().getId()));
		TextView txtLeaderName = (TextView)findViewById(R.id.txt_leader_name);
		txtLeaderName.setText(group.getLeader().getName());

		setRequestButtonEnable(true);
	}

	private void postGroupRequest() {
		PostGroupRequestAsyncTask asyncTask = new PostGroupRequestAsyncTask(groupId, this, new PostGroupRequestAsyncTask.OnPostExecuteListener() {
			@Override
			public void onPostExecute(boolean result) {
				if (result) {
					Toast.makeText(GroupSearchActivity.this, "リーダーにリクエストを送りました", Toast.LENGTH_SHORT).show();
					finish();
				} else {
					Toast.makeText(GroupSearchActivity.this, "リクエスト送信失敗", Toast.LENGTH_SHORT).show();
					setRequestButtonEnable(true);
				}
			}
		});

		asyncTask.execute(new MySharedPref(this).getUser());
	}

	private void setRequestButtonEnable(boolean enable) {
		Button btnRequest = (Button)findViewById(R.id.btn_request);
		btnRequest.setEnabled(enable);
	}
}
