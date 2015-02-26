package jp.gr.java_conf.shiolier.wayn;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import jp.gr.java_conf.shiolier.wayn.asynctask.UserNameUpdateAsyncTask;
import jp.gr.java_conf.shiolier.wayn.entity.User;
import jp.gr.java_conf.shiolier.wayn.util.MySharedPref;

public class UserNameChangeActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_name_change);

		String userName = new MySharedPref(this).getUserName(null);
		if (userName != null) {
			EditText edtName = (EditText)findViewById(R.id.edt_name);
			edtName.setText(userName);
		}

		Button btnChange = (Button)findViewById(R.id.btn_change);
		btnChange.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText edtName = (EditText)findViewById(R.id.edt_name);
				final String name = edtName.getText().toString();

				if (name.length() <= 1) {
					Toast.makeText(UserNameChangeActivity.this, "2文字以上にしてください", Toast.LENGTH_SHORT).show();
					return;
				}

				UserNameUpdateAsyncTask asyncTask = new UserNameUpdateAsyncTask(UserNameChangeActivity.this, new UserNameUpdateAsyncTask.OnPostExecuteListener() {
					@Override
					public void onPostExecute(boolean result) {
						if (result) {
							Toast.makeText(UserNameChangeActivity.this, "成功", Toast.LENGTH_SHORT).show();
							new MySharedPref(UserNameChangeActivity.this).setUserName(name);
						} else {
							Toast.makeText(UserNameChangeActivity.this, "失敗", Toast.LENGTH_SHORT).show();
						}
					}
				});

				MySharedPref sharedPref = new MySharedPref(UserNameChangeActivity.this);
				User user = new User();
				user.setId(sharedPref.getUserId(0));
				user.setPassword(sharedPref.getUserPassword(""));
				user.setName(name);

				asyncTask.execute(user);
			}
		});
	}
}
