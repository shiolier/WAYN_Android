package jp.gr.java_conf.shiolier.wayn;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import jp.gr.java_conf.shiolier.wayn.view.RadarView;

public class RadarActivity extends ActionBarActivity {
	private static final int REQUEST_GROUP_SELECT = 0x01;

	private RadarView radarView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_radar);

		radarView = (RadarView)findViewById(R.id.radar_view);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_radar, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		if (id == R.id.action_display_group_setting) {
			Intent intent = new Intent(this, DisplayGroupSettingActivity.class);
			startActivityForResult(intent, REQUEST_GROUP_SELECT);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == REQUEST_GROUP_SELECT && resultCode == RESULT_OK) {
			int groupId = data.getIntExtra(DisplayGroupSettingActivity.KEY_GROUP_ID, 0);
		}
	}
}
