package jp.gr.java_conf.shiolier.wayn;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import jp.gr.java_conf.shiolier.wayn.asynctask.UpdateGroupNameAsyncTask;
import jp.gr.java_conf.shiolier.wayn.asynctask.UpdateUserNameAsyncTask;
import jp.gr.java_conf.shiolier.wayn.entity.Group;
import jp.gr.java_conf.shiolier.wayn.entity.User;
import jp.gr.java_conf.shiolier.wayn.util.MySharedPref;


public class UpdateGroupNameActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_group_name);

		Intent intent = getIntent();
		final Group group = (Group)intent.getSerializableExtra(GroupManageLeaderActivity.EXTRA_GROUP);

		EditText edtName = (EditText)findViewById(R.id.edt_name);
		edtName.setText(group.getName());

		Button btnChange = (Button)findViewById(R.id.btn_change);
		btnChange.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText edtName = (EditText)findViewById(R.id.edt_name);
				final String name = edtName.getText().toString();

				if (name.length() <= 1) {
					Toast.makeText(UpdateGroupNameActivity.this, "2文字以上にしてください", Toast.LENGTH_SHORT).show();
					return;
				}

				UpdateGroupNameAsyncTask asyncTask = new UpdateGroupNameAsyncTask(UpdateGroupNameActivity.this, new UpdateGroupNameAsyncTask.OnPostExecuteListener() {
					@Override
					public void onPostExecute(boolean result) {
						if (result) {
							Toast.makeText(UpdateGroupNameActivity.this, "成功", Toast.LENGTH_SHORT).show();
							finish();
						} else {
							Toast.makeText(UpdateGroupNameActivity.this, "失敗", Toast.LENGTH_SHORT).show();
						}
					}
				});

				group.setName(name);
				group.setLeader(new MySharedPref(UpdateGroupNameActivity.this).getUser());
				asyncTask.execute(group);
			}
		});
	}
}
