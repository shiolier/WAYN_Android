package jp.gr.java_conf.shiolier.wayn;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import java.util.UUID;

import jp.gr.java_conf.shiolier.wayn.asynctask.GetAppInfoAsyncTask;
import jp.gr.java_conf.shiolier.wayn.asynctask.UserRegisterAsyncTask;
import jp.gr.java_conf.shiolier.wayn.entity.AppInfo;
import jp.gr.java_conf.shiolier.wayn.entity.User;
import jp.gr.java_conf.shiolier.wayn.fragment.EditTextDialogFragment;
import jp.gr.java_conf.shiolier.wayn.util.MySharedPref;


public class StartActivity extends ActionBarActivity {
	private int versionCode;
	private String versionName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);

		PackageManager packageManager = getPackageManager();
		try {
			PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), PackageManager.GET_ACTIVITIES);
			versionCode = packageInfo.versionCode;
			versionName = packageInfo.versionName;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}

		checkId();
	}

	private void checkId() {
		MySharedPref mySharedPref = new MySharedPref(this);
		int id = mySharedPref.getUserId(0);

		if (id == 0) {
			showNameDialog();
		} else {
			checkVersion();
		}
	}

	private void showNameDialog() {
		final EditText editText = new EditText(this);
		editText.setHint("ユーザー名");

		EditTextDialogFragment dialogFragment = EditTextDialogFragment.newInstance("ユーザー登録", "ユーザー名を入力してください。\n後で変更できます");
		dialogFragment.setOnOkClickListener(new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String name = editText.getText().toString();
				if (name.length() < 2) {
					Toast.makeText(StartActivity.this, "2文字以上にしてください", Toast.LENGTH_SHORT).show();
					showNameDialog();
				} else {
					new MySharedPref(StartActivity.this).setUserName(name);
					register(name);
				}
			}
		});
		dialogFragment.setEditText(editText);
		dialogFragment.show(getFragmentManager(), "dialog");
	}

	private void register(String name) {
		UserRegisterAsyncTask asyncTask = new UserRegisterAsyncTask(this, new UserRegisterAsyncTask.OnPostExecuteListener() {
			@Override
			public void onPostExecute(User user) {
				if (user.getId() != 0) {
					MySharedPref mySharedPref = new MySharedPref(StartActivity.this);
					mySharedPref.setUserId(user.getId());
					checkVersion();
				} else {
					Toast.makeText(StartActivity.this, "通信失敗", Toast.LENGTH_SHORT).show();
					finish();
				}
			}
		});

		String password = UUID.randomUUID().toString();
		new MySharedPref(StartActivity.this).setUserPassword(password);

		User user = new User();
		user.setName(name);
		user.setPassword(password);

		asyncTask.execute(user);
	}

	private void checkVersion() {
		GetAppInfoAsyncTask asyncTask = new GetAppInfoAsyncTask(this, new GetAppInfoAsyncTask.OnPostExecuteListener() {
			@Override
			public void onPostExecute(AppInfo appInfo) {
				if (appInfo.getVersionCode() == 0) {
					Toast.makeText(StartActivity.this, "通信失敗\n", Toast.LENGTH_SHORT).show();
					finish();
				} else if (appInfo.getVersionCode() > versionCode) {
					// TODO: アップデート処理
					Toast.makeText(StartActivity.this, "バージョンが古いです\nアップデートしてください", Toast.LENGTH_SHORT).show();
					finish();
				} else {
					startMainActivity();
				}
			}
		});
		asyncTask.execute();
	}

	private void startMainActivity() {
		Intent intent = new Intent(StartActivity.this, MainActivity.class);
		startActivity(intent);
		finish();
	}
}
