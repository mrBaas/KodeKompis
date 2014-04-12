package net.tedes.kodekompis;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.ExpandableListContextMenuInfo;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

public class FragmentExpandableList extends Fragment
			implements LoaderManager.LoaderCallbacks<List<DataBolk>> {

	private ExpandableListAdapter listAdapter;
    private ExpandableListView expListView;
    	
    
	//private CustomMainArrayAdapter mAdapter;
	private String kode;
	private boolean korrektKode;
	
	public void addDataBolk(DataBolk bolken){
		listAdapter.addDataBolk(bolken);
		listAdapter.sortDataBolkList(ManagePreferences.getSortMethod(getActivity(), Tedes.DATABOLK_SORTING_METHOD));
		listAdapter.notifyDataSetChanged();
		//TODO: Make async task here maybe?
		ManageStorage.writeList(getActivity().getBaseContext(), (ArrayList<DataBolk>)listAdapter.getData(), kode);
	}
	
	public void deleteDataBolk(DataBolk bolken){
		//TODO: Implement confirmation dialog here, rather than several other places. Return true/false on delete instead.
		listAdapter.deleteDataBolk(bolken);
		listAdapter.notifyDataSetChanged();
		//TODO: Make async task here maybe?
		ManageStorage.writeList(getActivity().getBaseContext(), (ArrayList<DataBolk>)listAdapter.getData(), kode);
	}
	
	public void sortDataBolkList(DataBolk.SortMethod sortMethod) {
        listAdapter.sortDataBolkList(sortMethod);
        listAdapter.notifyDataSetChanged();
	}
	
	public void updateDataBolk(DataBolk bolken){
		listAdapter.updateDataBolk(bolken);
		listAdapter.notifyDataSetChanged();
		//TODO: Make async task here maybe?
		ManageStorage.writeList(getActivity().getBaseContext(), (ArrayList<DataBolk>)listAdapter.getData(), kode);
	}
	
    public void setKode(String kode, Context context) {
    	this.kode = kode;
    	Log.d("Martin", "setKode kode: "+kode);
    	this.korrektKode = ManageSecurity.comparePassword(context, kode);
    	Log.d("Martin", "kode: "+kode+", korrektkode: "+korrektKode);
    	if(!korrektKode){
    		int i = ManagePreferences.getInt(context, Tedes.FAILED_LOGINS);
    		ManagePreferences.setInt(context, Tedes.FAILED_LOGINS, ++i);
    		Log.d("Martin", "failedlogins: "+i);
    		Toast.makeText(context, "Failed Logins: "+i, Toast.LENGTH_LONG).show();
    	} else {
    		//Kode godkjent. Reset failedloginscounter, og inkrementer startcounter.
    		int startcounter = ManagePreferences.getInt(context, Tedes.START_COUNTER);
			ManagePreferences.setInt(context, Tedes.START_COUNTER, ++startcounter);
    		ManagePreferences.setInt(context, Tedes.FAILED_LOGINS, 0);
    		ManagePreferences.setInt(context, Tedes.FAILED_LOGINS_ITERATOR, 0);
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
        
        //Adding context menu for longclicks on list elements
        registerForContextMenu(expListView);
        
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

	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		
		MenuInflater inflater = getActivity().getMenuInflater();
		inflater.inflate(R.menu.menu_list_contextmenu, menu);
		
		ExpandableListView.ExpandableListContextMenuInfo info =	(ExpandableListView.ExpandableListContextMenuInfo) menuInfo;
		
		int group = ExpandableListView.getPackedPositionGroup(info.packedPosition);
		
		
		// If we want to trigger longpress only on list children, uncomment below
//		int type  = ExpandableListView.getPackedPositionType(info.packedPosition);
//		int child = ExpandableListView.getPackedPositionChild(info.packedPosition);
//		if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
//			String content = (String)listAdapter.getChild(group, child);
//			menu.setHeaderTitle(content);
//		}
		
		menu.setHeaderTitle(((DataBolk)listAdapter.getGroup(group)).getSted());
	}
	
	public boolean onContextItemSelected(MenuItem menuItem) {
		ExpandableListContextMenuInfo info = (ExpandableListContextMenuInfo) menuItem.getMenuInfo();
		
		int groupPos = ExpandableListView.getPackedPositionGroup(info.packedPosition);
		
		// If we want to trigger longpress only on list children, uncomment below
//		int groupPos = 0, childPos = 0;
//		int type = ExpandableListView.getPackedPositionType(info.packedPosition);
//		if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
//			groupPos = ExpandableListView.getPackedPositionGroup(info.packedPosition);
//			childPos = ExpandableListView.getPackedPositionChild(info.packedPosition);
//		}
		
		// Pull values from the adapter
		DataBolk bolk  = (DataBolk)listAdapter.getGroup(groupPos);
		String mSted   = (String)bolk.getSted();
		String mBruker = (String)listAdapter.getChild(groupPos, 0);
		String mPass   = (String)listAdapter.getChild(groupPos, 1);
		
		switch (menuItem.getItemId()) {
			case R.id.list_contextmenu_copy_user:
				Toast.makeText(getActivity(), "Brukernavn " + mBruker + " kopiert til utklippstavle", Toast.LENGTH_LONG).show();
				ManageClipboard.setClipboard(getActivity(), mBruker);
				return true;
			case R.id.list_contextmenu_copy_pass:
				Toast.makeText(getActivity(), "Brukernavn " + mPass + " kopiert til utklippstavle", Toast.LENGTH_LONG).show();
				ManageClipboard.setClipboard(getActivity(), mBruker);
				return true;
			case R.id.list_contextmenu_edit:
				// OKEI; DEN FETTA HER UNDER, DEN E EN SEXY HOOK! SJÅ PÅ DEN!
				// Få adapteret tel å rop på sin aktivitet gjennom et interface, 
				// som så opprett et parallellt fragment ferdigutfylt me informasjon herfra,
				// som e henta fra adapteret. Det nye fragmentet tar inn ny informasjon fra bruker,
				// send det telbake tel aktivitetn, som dytt det telbake inn HIT, som så send det telbake tel
				// adapteret. Ei linja. /benner.
				listAdapter.getCallback().openEditDialog(bolk);
				return true;
			case R.id.list_contextmenu_delete:
				Toast.makeText(getActivity(), "Element " + mSted + " slettet", Toast.LENGTH_LONG).show();
				//TODO: Implement confirmation dialog.
				deleteDataBolk(bolk);
				return true;
			default:
				Toast.makeText(getActivity(), "default", Toast.LENGTH_LONG).show();
				return super.onContextItemSelected(menuItem);
		  }
	}
	
	@Override
	public Loader<List<DataBolk>> onCreateLoader(int arg0, Bundle arg1) {
		return new DataListLoader(getActivity(), kode);
	} 

	@Override
	public void onLoadFinished(Loader<List<DataBolk>> arg0, List<DataBolk> data) { 
        listAdapter = new ExpandableListAdapter(getActivity(), data);
        listAdapter.sortDataBolkList(ManagePreferences.getSortMethod(getActivity(), Tedes.DATABOLK_SORTING_METHOD));
        
        //Setting list adapter
        expListView.setAdapter(listAdapter);
		
        //Adding listener to Group elements (parent nodes), to keep only one Group expanded.
        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
            	//NB: groupPosition is consequent,
            	//but the arrangement of Views inside the List is NOT.
            	//Storing Views inside adapter to interact with caused unexpected behavior.
            	
            	//Storing previous active Group for closing caused misbehavior with rapid clicking.
            	//Solved by iterating through all list Group elements instead.
            	
                for(int i = 0; i < listAdapter.getGroupCount(); i++){
                	if(i != groupPosition) {
                		//((ImageButton)listAdapter.getGroupView(i).findViewById(R.id.listitem_editbutton)).setVisibility(View.INVISIBLE);
                		expListView.collapseGroup(i);
                		//Log.d("Martin", "Collapsed and Disabled: "+i);
                	}
                }
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

