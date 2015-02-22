package jp.gr.java_conf.shiolier.wayn;

import android.app.ActivityManager;
import android.content.Intent;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.List;
import java.util.UUID;

import jp.gr.java_conf.shiolier.wayn.asynctask.UserRegistAsyncTask;
import jp.gr.java_conf.shiolier.wayn.entity.User;
import jp.gr.java_conf.shiolier.wayn.service.PostLocationService;
import jp.gr.java_conf.shiolier.wayn.util.MySharedPref;

public class MainActivity extends ActionBarActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button btnRadar = (Button)findViewById(R.id.btn_radar);
		btnRadar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!isPostLocationServiceRunning()) {
					runPostLocationService();
				}

				Intent intent = new Intent(MainActivity.this, RadarActivity.class);
				startActivity(intent);
			}
		});

		ToggleButton tglbtnPostLocation = (ToggleButton)findViewById(R.id.tglbtn_post_location_enable);
		if (isPostLocationServiceRunning()) {
			tglbtnPostLocation.setChecked(true);
		} else {
			tglbtnPostLocation.setChecked(false);
		}
		tglbtnPostLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					runPostLocationService();
				} else {
					stopPostLocationService();
				}
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
		Log.i("MyLog", String.format("User Password: %s", mySharedPref.getUserPassword("")));
	}

	private void regist() {
		UserRegistAsyncTask asyncTask = new UserRegistAsyncTask(this, new UserRegistAsyncTask.OnPostExecuteListener() {
			@Override
			public void onPostExecute(User user) {
				if (user.getId() != 0) {
					MySharedPref mySharedPref = new MySharedPref(MainActivity.this);
					mySharedPref.setUserId(user.getId());
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

	private boolean isPostLocationServiceRunning() {
		String serviceName = PostLocationService.class.getSimpleName();

		ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> serviceInfos = activityManager.getRunningServices(Integer.MAX_VALUE);
		if (serviceInfos != null) {
			for(ActivityManager.RunningServiceInfo serviceInfo : serviceInfos){
				if(serviceInfo.service.getClassName().endsWith(serviceName)){
					return true;
				}
			}
		}
		return false;
	}

	private void runPostLocationService() {
		Intent intent = new Intent(this, PostLocationService.class);
		startService(intent);

		ToggleButton tglbtnPostLocation = (ToggleButton)findViewById(R.id.tglbtn_post_location_enable);
		tglbtnPostLocation.setChecked(true);
	}

	private void stopPostLocationService() {
		Intent intent = new Intent(this, PostLocationService.class);
		stopService(intent);

		ToggleButton tglbtnPostLocation = (ToggleButton)findViewById(R.id.tglbtn_post_location_enable);
		tglbtnPostLocation.setChecked(false);
	}
}
