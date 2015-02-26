package jp.gr.java_conf.shiolier.wayn;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import jp.gr.java_conf.shiolier.wayn.asynctask.GetGroupRequestsAsyncTask;
import jp.gr.java_conf.shiolier.wayn.entity.Group;
import jp.gr.java_conf.shiolier.wayn.entity.GroupRequest;
import jp.gr.java_conf.shiolier.wayn.util.MySharedPref;

public class GroupRequestListActivity extends ActionBarActivity {
	public static final String EXTRA_GROUP_REQUEST = "GROUP_REQUEST";

	private int groupId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_request_list);

		Intent intent = getIntent();
		Group group = (Group)intent.getSerializableExtra(GroupManageLeaderActivity.EXTRA_GROUP);
		groupId = group.getId();

		ListView listView = (ListView)findViewById(R.id.list_view);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				GroupRequest request = (GroupRequest)parent.getItemAtPosition(position);

				Intent intent = new Intent(GroupRequestListActivity.this, GroupRequestDetailActivity.class);
				intent.putExtra(EXTRA_GROUP_REQUEST, request);
				startActivity(intent);
			}
		});

		getRequests();
	}

	private void getRequests() {
		GetGroupRequestsAsyncTask asyncTask = new GetGroupRequestsAsyncTask(groupId, this, new GetGroupRequestsAsyncTask.OnPostExecuteListener() {
			@Override
			public void onPostExecute(ArrayList<GroupRequest> requestList) {
				ListView listView = (ListView)findViewById(R.id.list_view);
				listView.setAdapter(new RequestArrayAdapter(requestList));
			}
		});
		asyncTask.execute(new MySharedPref(this).getUser());
	}

	private class RequestArrayAdapter extends ArrayAdapter<GroupRequest> {

		public RequestArrayAdapter(ArrayList<GroupRequest> requestList) {
			super(GroupRequestListActivity.this, R.layout.request_list_item, requestList);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			GroupRequest request = getItem(position);

			if (convertView == null) {
				convertView = getLayoutInflater().inflate(R.layout.request_list_item, null);
			}

			TextView txtUserId = (TextView)convertView.findViewById(R.id.txt_user_id);
			if (txtUserId != null) {
				txtUserId.setText(Integer.toString(request.getUser().getId()));
			}

			TextView txtUserName = (TextView)convertView.findViewById(R.id.txt_user_name);
			if (txtUserName != null) {
				txtUserName.setText(request.getUser().getName());
			}

			return convertView;
		}
	}
}
