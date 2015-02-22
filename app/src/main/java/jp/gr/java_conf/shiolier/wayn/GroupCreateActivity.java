package jp.gr.java_conf.shiolier.wayn;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class GroupCreateActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_create);

		Button btnCreate = (Button)findViewById(R.id.btn_create);
		btnCreate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText edtGroupName = (EditText)findViewById(R.id.edt_group_name);
				String groupName = edtGroupName.getText().toString();
			}
		});
	}
}
