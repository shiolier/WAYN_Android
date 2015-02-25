package jp.gr.java_conf.shiolier.wayn;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_manage);

		ListView listView = (ListView)findViewById(R.id.list_view);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

			}
		});

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
