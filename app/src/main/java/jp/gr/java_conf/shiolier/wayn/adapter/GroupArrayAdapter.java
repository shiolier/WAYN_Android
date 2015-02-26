package jp.gr.java_conf.shiolier.wayn.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import jp.gr.java_conf.shiolier.wayn.R;
import jp.gr.java_conf.shiolier.wayn.entity.Group;
import jp.gr.java_conf.shiolier.wayn.util.MySharedPref;

public class GroupArrayAdapter extends ArrayAdapter<Group> {
	private int userId;
	private boolean leaderHighlight;
	private LayoutInflater layoutInflater;

	public GroupArrayAdapter(Context context, ArrayList<Group> groupList, boolean leaderHighlight) {
		super(context, R.layout.group_list_item, groupList);

		userId = new MySharedPref(context).getUserId(0);
		this.leaderHighlight = leaderHighlight;
		layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Group group = getItem(position);

		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.group_list_item, null);
		}

		TextView txtGroupId = (TextView)convertView.findViewById(R.id.txt_group_id);
		if (txtGroupId != null) {
			txtGroupId.setText(Integer.toString(group.getId()));
		}

		TextView txtGroupName = (TextView)convertView.findViewById(R.id.txt_group_name);
		if (txtGroupName != null) {
			txtGroupName.setText(group.getName());
		}

		if (leaderHighlight && userId == group.getLeader().getId()) {
			convertView.setBackgroundColor(Color.LTGRAY);
		} else {
			convertView.setBackgroundColor(Color.WHITE);
		}

		return convertView;
	}
}
