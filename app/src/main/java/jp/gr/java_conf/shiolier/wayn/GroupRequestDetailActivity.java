package jp.gr.java_conf.shiolier.wayn;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import jp.gr.java_conf.shiolier.wayn.entity.GroupRequest;


public class GroupRequestDetailActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_request_detail);

		Intent intent = getIntent();
		GroupRequest request = (GroupRequest)intent.getSerializableExtra(GroupRequestListActivity.EXTRA_GROUP_REQUEST);

		TextView txtUserId = (TextView)findViewById(R.id.txt_user_id);
		txtUserId.setText(Integer.toString(request.getUser().getId()));

		TextView txtUserName = (TextView)findViewById(R.id.txt_user_name);
		txtUserName.setText(request.getUser().getName());

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
