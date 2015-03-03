package jp.gr.java_conf.shiolier.wayn;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import jp.gr.java_conf.shiolier.wayn.asynctask.UpdateUserNameAsyncTask;
import jp.gr.java_conf.shiolier.wayn.entity.User;
import jp.gr.java_conf.shiolier.wayn.util.MySharedPref;

public class UpdateUserNameActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_user_name);

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
					Toast.makeText(UpdateUserNameActivity.this, "2文字以上にしてください", Toast.LENGTH_SHORT).show();
					return;
				}

				UpdateUserNameAsyncTask asyncTask = new UpdateUserNameAsyncTask(UpdateUserNameActivity.this, new UpdateUserNameAsyncTask.OnPostExecuteListener() {
					@Override
					public void onPostExecute(boolean result) {
						if (result) {
							Toast.makeText(UpdateUserNameActivity.this, "成功", Toast.LENGTH_SHORT).show();
							new MySharedPref(UpdateUserNameActivity.this).setUserName(name);
							finish();
						} else {
							Toast.makeText(UpdateUserNameActivity.this, "失敗", Toast.LENGTH_SHORT).show();
						}
					}
				});

				MySharedPref sharedPref = new MySharedPref(UpdateUserNameActivity.this);
				User user = new User();
				user.setId(sharedPref.getUserId(0));
				user.setPassword(sharedPref.getUserPassword(""));
				user.setName(name);

				asyncTask.execute(user);
			}
		});
	}
}
