package net.tedes.kodekompis;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class DataListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<List<DataBolk>> {

	CustomMainArrayAdapter mAdapter;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		//I utgangspunktet er listen tom
		setEmptyText("Takk for at du bruker denne applikasjonen. Begynn å lagre dine brukernavn og passord ved å trykke på + tegnet.");
		
		//Tomt adapter blir opprettet for å vise data i listen.
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
		//Hva skjer når et item blir trykket på? 
		//Detaljefremvisning i ny activity skal vises
	}

	@Override
	public Loader<List<DataBolk>> onCreateLoader(int arg0, Bundle arg1) {
		return new DataListLoader(getActivity());
	}

	@Override
	public void onLoadFinished(Loader<List<DataBolk>> arg0, List<DataBolk> data) {
		mAdapter.setData(data);
		//På dette tidspunktet skal listen vises
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
	
}
