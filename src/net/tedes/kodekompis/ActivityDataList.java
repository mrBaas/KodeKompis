package net.tedes.kodekompis;

import java.util.ArrayList;
import java.util.Collections;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class ActivityDataList extends FragmentActivity 
					implements InterfaceBolkManager,
							   InterfaceEditDialog {
	
	private Menu menu;
	private FragmentExpandableList listFragment;
	private String kode;
	
	@Override
	public void addDataBolk(DataBolk bolken) {
		listFragment.addDataBolk(bolken);
	}
	
	@Override
	public void deleteDataBolk(DataBolk bolken) {
		listFragment.deleteDataBolk(bolken);
	}
	
	@Override
	public ArrayList<String> getPasswords() {
		ArrayList<String> passwords = new ArrayList<String>();
		for(DataBolk bolk : listFragment.getAdapter().getData()){
			if(!passwords.contains(bolk.getPassord())) {
				passwords.add(bolk.getPassord());
			}
		}
		Collections.sort(passwords);
		return passwords;
	}

	@Override
	public ArrayList<String> getUsernames() {
		ArrayList<String> usernames = new ArrayList<String>();
		for(DataBolk bolk : listFragment.getAdapter().getData()){
			if(!usernames.contains(bolk.getBrukernavn())) {
				usernames.add(bolk.getBrukernavn());
			}
		}
		Collections.sort(usernames);
		return usernames;
	}
	
	@Override
	public void sortDataBolkList(DataBolk.SortMethod sortMethod) {
		listFragment.sortDataBolkList(sortMethod);
	}
	
	@Override
	public void updateDataBolk(DataBolk bolken) {
		listFragment.updateDataBolk(bolken);
	}
	

	@Override
	public void openEditDialog(DataBolk bolken) {
		FragmentManager fm = getSupportFragmentManager();
		boolean check = ManageSecurity.comparePassword(getBaseContext(), kode);
		if(check){
			FragmentLeggTilListe leggTil = FragmentLeggTilListe.editBolk(bolken);
	        leggTil.show(fm, "fragment_dialog_create");
		} else {
			//Do nothing, wrong password.
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.add_input, menu);
		this.menu = menu;
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		boolean check = ManageSecurity.comparePassword(getBaseContext(), kode);
		if(!check){
			return true;
		}
		
		switch (item.getItemId()) {
			case R.id.menu_leggtil:
				FragmentManager fm = getSupportFragmentManager();
				FragmentLeggTilListe leggTil = new FragmentLeggTilListe();
			    leggTil.show(fm, "fragment_dialog_create");
			    break;
			case R.id.menu_sort:
				DataBolk.SortMethod smCurr = ManagePreferences.getSortMethod(this, Tedes.DATABOLK_SORTING_METHOD);
				DataBolk.SortMethod smNext = DataBolk.SortMethod.getNext(smCurr);
				ManagePreferences.setSortMethod(this, Tedes.DATABOLK_SORTING_METHOD, smNext);
				sortDataBolkList(smNext);
				menu.findItem(R.id.menu_sort).setIcon(DataBolk.SortMethod.getIcon(smNext));
				break;
			default:
				//Do nothing
				break;	
		}
		
		return true;
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		
		//Finish activity when out of sight -> returns to login screen on resume.
		this.finish();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Picking up kode from startscreen
		Bundle extras = getIntent().getExtras();
		String kode = extras.getString("kode");
		this.kode = kode;
		Log.d("Martin", "activity-kode: "+kode);
				
		setContentView(R.layout.activity_layout_code_list);
		
		//Henter ut getSupportFragmentManager() i stede for getFragmentManager()
		//Støtter tidligere android versjoner
		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
		
		if (fragment == null) {
			//fragment = new FragmentDataList();
			fragment = new FragmentExpandableList();
			listFragment = (FragmentExpandableList)fragment;
			listFragment.setKode(kode, getBaseContext());
			fm.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
		}
	}
}
