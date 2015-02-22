package jp.gr.java_conf.shiolier.wayn;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.UUID;

import jp.gr.java_conf.shiolier.wayn.asynctask.UserRegistAsyncTask;
import jp.gr.java_conf.shiolier.wayn.entity.User;
import jp.gr.java_conf.shiolier.wayn.util.MySharedPref;

public class MainActivity extends ActionBarActivity {
	public static final String SHARED_PREF = "SHARED_PREF";

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

		ToggleButton tglbtnPostLocation = (ToggleButton)findViewById(R.id.tglbtn_post_location_enable);
		tglbtnPostLocation.setOnClickListener(new View.OnClickListener() {
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

		MySharedPref mySharedPref = new MySharedPref(this);
		if (mySharedPref.getUserId(0) == 0) {
			regist();
		}
		Log.i("MyLog", String.format("User ID: %d", mySharedPref.getUserId(0)));
	}

	private void regist() {
		UserRegistAsyncTask asyncTask = new UserRegistAsyncTask(this, new UserRegistAsyncTask.OnPostExecuteListener() {
			@Override
			public void onPostExecute(User user) {
				if (user.getUserId() != 0) {
					MySharedPref mySharedPref = new MySharedPref(MainActivity.this);
					mySharedPref.setUserId(user.getUserId());
				} else {
					registFailure();
				}
			}
		});

		String password = UUID.randomUUID().toString();
		MySharedPref mySharedPref = new MySharedPref(MainActivity.this);
		mySharedPref.setUserPassword(password);

		asyncTask.execute("SampleName", password);
	}

	private void registFailure() {
		Toast.makeText(this, "初期化に失敗しました。", Toast.LENGTH_SHORT).show();
		finish();
	}
}
