package jp.gr.java_conf.shiolier.wayn;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class GroupRequestListActivity extends ActionBarActivity {
	public static final String KEY_USER_ID = "USER_ID";
	public static final String KEY_USER_NAME = "USER_NAME";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_request_list);

		ListView listView = (ListView)findViewById(R.id.list_view);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(GroupRequestListActivity.this, GroupRequestDetailActivity.class);
				// リクエスト情報受け渡し
				startActivity(intent);
			}
		});
	}
}
