package jp.gr.java_conf.shiolier.wayn;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SettingActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);

		ListView listView = (ListView)findViewById(R.id.list_view);
		listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new String[]{
				"名前を変更",
				"グループに入る",
				"グループを作る",
				"グループを管理",
		}));

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (position == 0) {
					// 名前を変更
					Intent intent = new Intent(SettingActivity.this, UpdateUserNameActivity.class);
					startActivity(intent);
				} else if (position == 1) {
					// グループに入る
					Intent intent = new Intent(SettingActivity.this, GroupSearchActivity.class);
					startActivity(intent);
				} else if (position == 2) {
					// グループを作る
					Intent intent = new Intent(SettingActivity.this, GroupCreateActivity.class);
					startActivity(intent);
				} else if (position == 3) {
					// グループを管理
					Intent intent = new Intent(SettingActivity.this, GroupManageActivity.class);
					startActivity(intent);
				} else {
					throw new RuntimeException("クリック処理未実装");
				}
			}
		});
	}
}
