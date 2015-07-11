package com.example.contact.business;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.contact.R;

public class UserAdapter extends BaseAdapter {

	private LayoutInflater infalter;
	private ArrayList<Contact> list;

	public UserAdapter(Context context, ArrayList<Contact> list) {
		infalter = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Contact getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		final ViewHolder holder;
		if (convertView == null) {

			convertView = infalter.inflate(R.layout.layout_item_list_contact,
					null);
			holder = new ViewHolder();
			holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
			holder.tvPhone = (TextView) convertView.findViewById(R.id.tvPhone);

			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tvPhone.setText(list.get(position).getPhone());
		holder.tvName.setText(list.get(position).getContact_name());
		return convertView;
	}

	public class ViewHolder {
		TextView tvName;
		TextView tvPhone;

	}

}