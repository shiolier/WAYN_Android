package jp.gr.java_conf.shiolier.wayn;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import jp.gr.java_conf.shiolier.wayn.util.MySharedPref;

public class NameChangeActivity extends ActionBarActivity {

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
				String name = edtName.getText().toString();

				if (name.length() <= 1) {
					Toast.makeText(NameChangeActivity.this, "2文字以上にしてください", Toast.LENGTH_SHORT);
					return;
				}

			}
		});
	}
}
