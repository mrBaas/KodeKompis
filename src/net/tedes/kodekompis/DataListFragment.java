package net.tedes.kodekompis;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class DataListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<List<DataBolk>> {

	private CustomMainArrayAdapter mAdapter;
	private String kode;
	private boolean korrektKode;
	
	public void mottaBolken(DataBolk bolken){
		mAdapter.addDataBolk(bolken);
		mAdapter.notifyDataSetChanged();
		//TODO: Make async task here maybe?
		InternalStorage.writeList(getActivity().getBaseContext(), mAdapter.getData(), kode);
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
    		Toast.makeText(context, "login success", Toast.LENGTH_LONG).show();
    	}
    }
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		//I utgangspunktet er listen tom
		setEmptyText("Takk for at du *rap* bruker denne applikasjonen, Morty. Begynn � lagre dine brukernavn og passord ved � trykke p� + tegnet, Morty.");
		
		//Tomt adapter blir opprettet for � vise data i listen.
		mAdapter = new CustomMainArrayAdapter(getActivity());
	
		setListAdapter(mAdapter);
		
		//Progress loader
		setListShown(false);
		
		//Forbereder loader. Enten kobles til eksisterenede
		//eller start ny.
		getLoaderManager().initLoader(0, null, this);
		
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id){
		//Hva skjer n�r et item blir trykket p�? 
		//Detaljefremvisning i ny activity skal vises
	}

	@Override
	public Loader<List<DataBolk>> onCreateLoader(int arg0, Bundle arg1) {
		return new DataListLoader(getActivity(), kode);
	}

	@Override
	public void onLoadFinished(Loader<List<DataBolk>> arg0, List<DataBolk> data) {
		mAdapter.setData(data);
		
		//Dersom feil passord, masker stedsverdier.
		if(!korrektKode) { mAdapter.maskStedValues();	}
		
		//P� dette tidspunktet skal listen vises
		if(isResumed()){
			setListShown(true);
		}else{
			setListShownNoAnimation(true);
		}
	}

	@Override
	public void onLoaderReset(Loader<List<DataBolk>> arg0) {
		mAdapter.setData(null);
		
	}
	
	public CustomMainArrayAdapter getAdapter() {
		return this.mAdapter;
	}
	
}
