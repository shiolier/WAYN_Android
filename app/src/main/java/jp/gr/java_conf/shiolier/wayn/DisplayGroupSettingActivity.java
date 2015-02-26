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
import jp.gr.java_conf.shiolier.wayn.util.MySharedPref;

public class DisplayGroupSettingActivity extends ActionBarActivity {
	public static final String KEY_GROUP_ID = "GROUP_ID";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_group_setting);

		ListView listView = (ListView)findViewById(R.id.list_view);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Group group = (Group)parent.getItemAtPosition(position);

				Intent intent = new Intent();
				intent.putExtra(KEY_GROUP_ID, group.getId());
				setResult(RESULT_OK, intent);
				finish();
			}
		});

		getBelongGroups();
	}

	private void getBelongGroups() {
		GetBelongGroupAsyncTask asyncTask = new GetBelongGroupAsyncTask(this, new GetBelongGroupAsyncTask.OnPostExecuteListener() {
			@Override
			public void onPostExecute(ArrayList<Group> groupList) {
				ListView listView = (ListView)findViewById(R.id.list_view);
				listView.setAdapter(new GroupArrayAdapter(DisplayGroupSettingActivity.this, groupList, false));
			}
		});

		asyncTask.execute(new MySharedPref(this).getUser());
	}
}
