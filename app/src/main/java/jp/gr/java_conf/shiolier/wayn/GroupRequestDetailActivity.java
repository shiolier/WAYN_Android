package jp.gr.java_conf.shiolier.wayn;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import jp.gr.java_conf.shiolier.wayn.asynctask.ResponseRequestGroupAsyncTask;
import jp.gr.java_conf.shiolier.wayn.entity.GroupRequest;
import jp.gr.java_conf.shiolier.wayn.util.MySharedPref;


public class GroupRequestDetailActivity extends ActionBarActivity {
	private GroupRequest request;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_request_detail);

		Intent intent = getIntent();
		request = (GroupRequest)intent.getSerializableExtra(GroupRequestListActivity.EXTRA_GROUP_REQUEST);

		TextView txtUserId = (TextView)findViewById(R.id.txt_user_id);
		txtUserId.setText(Integer.toString(request.getUser().getId()));

		TextView txtUserName = (TextView)findViewById(R.id.txt_user_name);
		txtUserName.setText(request.getUser().getName());

		Button btnApprove = (Button)findViewById(R.id.btn_approve);
		btnApprove.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				responseRequest(true);
			}
		});

		Button btnReject = (Button)findViewById(R.id.btn_reject);
		btnReject.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				responseRequest(false);
			}
		});
	}

	private void responseRequest(boolean approve) {
		ResponseRequestGroupAsyncTask asyncTask = new ResponseRequestGroupAsyncTask(request.getId(), approve, this, new ResponseRequestGroupAsyncTask.OnPostExecuteListener() {
			@Override
			public void onPostExecute(boolean result) {
				if (result) {
					Toast.makeText(GroupRequestDetailActivity.this, "成功", Toast.LENGTH_SHORT).show();
					setResult(GroupRequestListActivity.RESULT_CHANGED);
					finish();
				} else {
					Toast.makeText(GroupRequestDetailActivity.this, "失敗", Toast.LENGTH_SHORT).show();
				}
			}
		});
		asyncTask.execute(new MySharedPref(this).getUser());
	}
}
