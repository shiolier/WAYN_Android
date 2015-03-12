package jp.gr.java_conf.shiolier.wayn;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import jp.gr.java_conf.shiolier.wayn.entity.Group;

public class GroupManageLeaderActivity extends ActionBarActivity {
	public static final String EXTRA_GROUP = "GROUP";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_manage_leader);

		Intent intent = getIntent();
		final Group group = (Group)intent.getSerializableExtra(GroupManageActivity.EXTRA_GROUP);

		Button btnRequestList = (Button)findViewById(R.id.btn_request_list);
		btnRequestList.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(GroupManageLeaderActivity.this, GroupRequestListActivity.class);
				intent.putExtra(EXTRA_GROUP, group);
				startActivity(intent);
			}
		});

		Button btnUpdateGroupName = (Button)findViewById(R.id.btn_update_group_name);
		btnUpdateGroupName.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(GroupManageLeaderActivity.this, UpdateGroupNameActivity.class);
				intent.putExtra(EXTRA_GROUP, group);
				startActivity(intent);
			}
		});
	}
}
