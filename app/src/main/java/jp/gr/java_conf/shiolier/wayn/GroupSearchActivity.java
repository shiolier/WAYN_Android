package jp.gr.java_conf.shiolier.wayn;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class GroupSearchActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_search);

		Button btnSearch = (Button)findViewById(R.id.btn_search);
		btnSearch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Button btnRequest = (Button)findViewById(R.id.btn_request);
				btnRequest.setEnabled(false);

				EditText editText = (EditText)findViewById(R.id.edt_group_id);
				int groupId = Integer.parseInt(editText.getText().toString());
			}
		});

		Button btnRequest = (Button)findViewById(R.id.btn_request);
		btnRequest.setEnabled(false);
		btnRequest.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
	}
}
