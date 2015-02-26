package jp.gr.java_conf.shiolier.wayn;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import jp.gr.java_conf.shiolier.wayn.asynctask.LeaveGroupAsyncTask;
import jp.gr.java_conf.shiolier.wayn.entity.Group;
import jp.gr.java_conf.shiolier.wayn.util.MySharedPref;

public class GroupManageMemperActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_manage_memper);

		Intent intent = getIntent();
		final Group group = (Group)intent.getSerializableExtra(GroupManageActivity.EXTRA_GROUP);

		TextView txtGroupId = (TextView)findViewById(R.id.txt_group_id);
		txtGroupId.setText(Integer.toString(group.getId()));

		TextView txtGroupName = (TextView)findViewById(R.id.txt_group_name);
		txtGroupName.setText(group.getName());

		TextView txtLeaderId = (TextView)findViewById(R.id.txt_leader_id);
		txtLeaderId.setText(Integer.toString(group.getLeader().getId()));

		TextView txtLeaderName = (TextView)findViewById(R.id.txt_leader_name);
		txtLeaderName.setText(group.getLeader().getName());

		Button btnLeave = (Button)findViewById(R.id.btn_leave);
		btnLeave.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				LeaveGroupAsyncTask asyncTask = new LeaveGroupAsyncTask(group.getId(), GroupManageMemperActivity.this, new LeaveGroupAsyncTask.OnPostExecuteListener() {
					@Override
					public void onPostExecute(boolean result) {
						if (result) {
							Toast.makeText(GroupManageMemperActivity.this, "退会成功", Toast.LENGTH_SHORT).show();
							setResult(GroupManageActivity.RESULT_CHANGED);
							finish();
						} else {
							Toast.makeText(GroupManageMemperActivity.this, "退会失敗", Toast.LENGTH_SHORT).show();
						}
					}
				});

				asyncTask.execute(new MySharedPref(GroupManageMemperActivity.this).getUser());
			}
		});
	}
}
