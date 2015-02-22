package jp.gr.java_conf.shiolier.wayn;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class GroupRequestDetailActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_request_detail);

		Intent intent = getIntent();

		TextView txtUserId = (TextView)findViewById(R.id.txt_user_id);
		txtUserId.setText(String.format("ユーザーID: %d", intent.getIntExtra(GroupRequestListActivity.KEY_USER_ID, 0)));

		TextView txtUserName = (TextView)findViewById(R.id.txt_user_name);
		txtUserName.setText(String.format("ユーザー名: %s", intent.getStringExtra(GroupRequestListActivity.KEY_USER_NAME)));

		Button btnApprove = (Button)findViewById(R.id.btn_approve);
		btnApprove.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});

		Button btnReject = (Button)findViewById(R.id.btn_reject);
		btnReject.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
	}
}
