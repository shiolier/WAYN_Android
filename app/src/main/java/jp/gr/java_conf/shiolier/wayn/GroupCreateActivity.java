package jp.gr.java_conf.shiolier.wayn;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import jp.gr.java_conf.shiolier.wayn.asynctask.CreateGroupAsyncTask;
import jp.gr.java_conf.shiolier.wayn.entity.Group;
import jp.gr.java_conf.shiolier.wayn.util.MySharedPref;

public class GroupCreateActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_create);

		Button btnCreate = (Button)findViewById(R.id.btn_create);
		btnCreate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText edtGroupName = (EditText)findViewById(R.id.edt_group_name);
				String groupName = edtGroupName.getText().toString();

				if (groupName.length() < 2) {
					Toast.makeText(GroupCreateActivity.this, "2文字以上にしてください", Toast.LENGTH_SHORT).show();
				} else {
					createGroup(groupName);
				}
			}
		});
	}

	private void createGroup(String groupName) {
		CreateGroupAsyncTask asyncTask = new CreateGroupAsyncTask(this, new CreateGroupAsyncTask.OnPostExecuteListener() {
			@Override
			public void onPostExecute(Group group) {
				if (group.getId() != 0) {
					Toast.makeText(GroupCreateActivity.this, "成功", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(GroupCreateActivity.this, "通信失敗", Toast.LENGTH_SHORT).show();
				}
			}
		});

		MySharedPref mySharedPref = new MySharedPref(this);

		Group group = new Group(groupName, mySharedPref.getUserId(0), mySharedPref.getUserPassword(""));
		asyncTask.execute(group);
	}
}
