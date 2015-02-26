package jp.gr.java_conf.shiolier.wayn;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import jp.gr.java_conf.shiolier.wayn.adapter.GroupArrayAdapter;
import jp.gr.java_conf.shiolier.wayn.asynctask.GetBelongGroupAsyncTask;
import jp.gr.java_conf.shiolier.wayn.entity.Group;
import jp.gr.java_conf.shiolier.wayn.entity.User;
import jp.gr.java_conf.shiolier.wayn.util.MySharedPref;

public class GroupManageActivity extends ActionBarActivity {
	public static final String EXTRA_GROUP = "GROUP";
	public static final int RESULT_CHANGED = 0x010;
	private static final int REQUEST_LEADER = 0x01;
	private static final int REQUEST_MEMBER = 0x02;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_manage);

		ListView listView = (ListView)findViewById(R.id.list_view);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Group group = (Group)parent.getItemAtPosition(position);

				Intent intent = new Intent();
				intent.putExtra(EXTRA_GROUP, group);
				if (new MySharedPref(GroupManageActivity.this).getUserId(0) == group.getLeader().getId()) {
					intent.setClass(GroupManageActivity.this, GroupManageLeaderActivity.class);
					startActivityForResult(intent, REQUEST_LEADER);
				} else {
					intent.setClass(GroupManageActivity.this, GroupManageMemperActivity.class);
					startActivityForResult(intent, REQUEST_MEMBER);
				}
			}
		});

		getBelongGroups();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_CHANGED) {
			getBelongGroups();
		}
	}

	private void getBelongGroups() {
		GetBelongGroupAsyncTask asyncTask = new GetBelongGroupAsyncTask(this, new GetBelongGroupAsyncTask.OnPostExecuteListener() {
			@Override
			public void onPostExecute(ArrayList<Group> groupList) {
				ListView listView = (ListView)findViewById(R.id.list_view);
				listView.setAdapter(new GroupArrayAdapter(GroupManageActivity.this, groupList, true));
			}
		});

		asyncTask.execute(new MySharedPref(this).getUser());
	}
}
