package net.tedes.kodekompis;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomMainArrayAdapter extends ArrayAdapter<DataBolk> {
	
	private final LayoutInflater mInflater;
	
	public CustomMainArrayAdapter(Context context){
		super(context, android.R.layout.simple_list_item_2);
		mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public void setData(List<DataBolk> data) {
		clear();
		if(data != null){
			for(DataBolk appEntry : data) {
				add(appEntry);
			}
		}
	}
	
	public void setDataBolk(DataBolk data){
		add(data);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		View view;
		
		if(convertView == null) {
			view = mInflater.inflate(R.layout.liste_bolk, parent, false);
		}else{
			view = convertView;
		}
		
		DataBolk item = getItem(position);
		((TextView)view.findViewById(R.id.dataBolk_sted)).setText(item.getmSted());
		((TextView)view.findViewById(R.id.dataBolk_bruker)).setText(item.getmBrukernavn());
		((TextView)view.findViewById(R.id.dataBolk_passord)).setText(item.getmPassord());
		
		return view;
		
	}
	
}
