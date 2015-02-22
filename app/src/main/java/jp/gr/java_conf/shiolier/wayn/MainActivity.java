package jp.gr.java_conf.shiolier.wayn;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button btnRadar = (Button)findViewById(R.id.btn_radar);
		btnRadar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// サービス起動処理


				Intent intent = new Intent(MainActivity.this, RadarActivity.class);
				startActivity(intent);
			}
		});

		Button btnPostLocation = (Button)findViewById(R.id.btn_post_location);
		btnPostLocation.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});

		Button btnSetting = (Button)findViewById(R.id.btn_setting);
		btnSetting.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, SettingActivity.class);
				startActivity(intent);
			}
		});
	}
}
