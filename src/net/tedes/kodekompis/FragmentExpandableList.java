package net.tedes.kodekompis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class FragmentExpandableList extends Fragment implements LoaderManager.LoaderCallbacks<List<DataBolk>> {

	private ExpandableListAdapter listAdapter;
    private ExpandableListView expListView;
	
    
	//private CustomMainArrayAdapter mAdapter;
	private String kode;
	private boolean korrektKode;
	
	public void mottaBolken(DataBolk bolken){
		listAdapter.addDataBolk(bolken);
		listAdapter.notifyDataSetChanged();
		//TODO: Make async task here maybe?
		InternalStorage.writeList(getActivity().getBaseContext(), (ArrayList<DataBolk>)listAdapter.getData(), kode);
	}
	
    public void setKode(String kode, Context context) {
    	this.kode = kode;
    	Log.d("Martin", "setKode kode: "+kode);
    	this.korrektKode = Security.comparePassword(context, kode);
    	Log.d("Martin", "kode: "+kode+", korrektkode: "+korrektKode);
    	if(!korrektKode){
    		int i = PreferencesManager.getInt(context, "failedlogins");
    		PreferencesManager.setInt(context, "failedlogins", ++i);
    		Log.d("Martin", "failedlogins: "+i);
    		Toast.makeText(context, "Failed Logins: "+i, Toast.LENGTH_LONG).show();
    	} else {
    		//Kode godkjent. Reset failedloginscounter, og inkrementer startcounter.
    		int startcounter = PreferencesManager.getInt(context, "startcounter");
			PreferencesManager.setInt(context, "startcounter", ++startcounter);
    		PreferencesManager.setInt(context, "failedlogins", 0);
    		PreferencesManager.setInt(context, "failedloginsN", 0);
    		Toast.makeText(context, "login success", Toast.LENGTH_LONG).show();
    	}
    }
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
 
		//Start loader to fetch and decrypt stored data.
		getLoaderManager().initLoader(0, null, this);
		
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_layout_expandablelist, parent, false);
        expListView = (ExpandableListView) v.findViewById(R.id.lvExp);
        //expListView.setItemsCanFocus(true);
 
		return v;
	}
	
//	@Override
//	public void onListItemClick(ListView l, View v, int position, long id){
//		//Hva skjer når et item blir trykket på? 
//		//Detaljefremvisning i ny activity skal vises
//		View view = l.getChildAt(position);
//		LinearLayout hideLayout = (LinearLayout)view.findViewById(R.id.hide_list);
//		Fader.slideUp(getActivity(), hideLayout.getId());
//		
//	}

	@Override
	public Loader<List<DataBolk>> onCreateLoader(int arg0, Bundle arg1) {
		return new DataListLoader(getActivity(), kode);
	} 

	@Override
	public void onLoadFinished(Loader<List<DataBolk>> arg0, List<DataBolk> data) { 
        listAdapter = new ExpandableListAdapter(getActivity(), data);
 
        //Setting list adapter
        expListView.setAdapter(listAdapter);
		
        //Adding listener to Group elements (parent nodes), to keep only one Group expanded.
        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {
            //int previousGroup = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
            	
            	//Show editButton on expanded group.
            	//Log.d("Martin", "onGroupExpand groupPosition: "+groupPosition);
            	//((ImageButton)listAdapter.getGroupView(groupPosition).findViewById(R.id.listitem_editbutton)).setVisibility(View.VISIBLE);
            	//Log.d("Martin", "Expanded and Enabled: "+groupPosition);
                
                for(int i = 0; i < listAdapter.getGroupCount(); i++){
                	if(i != groupPosition) {
                		//((ImageButton)listAdapter.getGroupView(i).findViewById(R.id.listitem_editbutton)).setVisibility(View.INVISIBLE);
                		expListView.collapseGroup(i);
                		//Log.d("Martin", "Collapsed and Disabled: "+i);
                	}
                }
                
                //if(groupPosition != previousGroup && previousGroup != -1) {
                	//TODO: Stresstest to see if problems with fast clicks.
                	//Might have to change to a loop. i < listAdapter.getGroupCount(), and i!=groupPos.
                    //expListView.collapseGroup(previousGroup);
                    
                    //Disable editButton on previously expanded Group.
                    //editButton = (ImageButton)listAdapter.getGroupView(previousGroup).findViewById(R.id.listitem_editbutton);
                    //editButton.setVisibility(View.INVISIBLE);
                //}
                //previousGroup = groupPosition;
            }
        });
        
		//Dersom feil passord, masker stedsverdier.
		//TODO: Redo
		//if(!korrektKode) { mAdapter.maskStedValues();	}
		
		//På dette tidspunktet skal listen vises
//		if(isResumed()){
//			setListShown(true);
//		}else{
//			setListShownNoAnimation(true);
//		}
	}

	@Override
	public void onLoaderReset(Loader<List<DataBolk>> arg0) {
		//mAdapter.setData(null);
	}
	
	public ExpandableListAdapter getAdapter() {
		return this.listAdapter;
	}
}

