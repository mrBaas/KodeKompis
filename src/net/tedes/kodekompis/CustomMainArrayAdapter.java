package net.tedes.kodekompis;

import java.util.ArrayList;
import java.util.Arrays;
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
	
	public ArrayList<DataBolk> getData() {
		ArrayList<DataBolk> bolks = new ArrayList<DataBolk>();
		for(int i=0; i < this.getCount();i++){
			bolks.add(this.getItem(i));
		}
		return bolks;
	}
	
	public void setData(List<DataBolk> data) {
		clear();
		if(data != null){
			for(DataBolk appEntry : data) {
				add(appEntry);
			}
		}
	}
	
	//Maskering av Sted-verdi. Merk at den fortsatt ligger lagret i klartekst i internminnet.
	public void maskStedValues(){
		ArrayList<DataBolk> bolks = getData();
		//DataBolk bolk;
		String sted, nySted,s1,s2,s3;
		for(int i = 0; i < bolks.size(); i++){
			sted = bolks.get(i).getSted();
			int j = sted.length();
			if(j < 5){
				bolks.get(i).setSted("****");
			} else {
				s1 = sted.substring(0, 2);
			
				char[] chars = new char[j-4];
				Arrays.fill(chars, '*');
				s2 = new String(chars);

				s3 = sted.substring(j-2,j);
				
				nySted = s1+s2+s3;
				bolks.get(i).setSted(nySted);
			}
			//bolks.get(i).setmSted("Not Available");
		}
	}
	
	
	public void addDataBolk(DataBolk data){
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
		((TextView)view.findViewById(R.id.dataBolk_sted)).setText(item.getSted());
		((TextView)view.findViewById(R.id.dataBolk_bruker)).setText(item.getBrukernavn());
		((TextView)view.findViewById(R.id.dataBolk_passord)).setText(item.getPassord());
		
		//saved
		return view;
		
	}
	
}
