package jp.gr.java_conf.shiolier.wayn;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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
				int groupId = 0;

				Intent intent = new Intent();
				intent.putExtra(KEY_GROUP_ID, groupId);
				setResult(RESULT_OK, intent);
				setResult(RESULT_OK, intent);
			}
		});
	}
}
