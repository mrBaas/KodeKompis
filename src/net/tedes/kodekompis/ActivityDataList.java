package net.tedes.kodekompis;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class ActivityDataList extends FragmentActivity 
					implements FragmentLeggTilListe.BolkManager,
							   ExpandableListAdapter.EditDialog {
	
	private FragmentExpandableList listFragment;
	private String kode;
	
	@Override
	public void addDataBolk(DataBolk bolken) {
		listFragment.mottaBolken(bolken);
	}
	
	@Override
	public void updateDataBolk(DataBolk bolken) {
		listFragment.updateDataBolk(bolken);
	}
	
	@Override
	public void openEditDialog(DataBolk bolken) {
		FragmentManager fm = getSupportFragmentManager();
		boolean check = Security.comparePassword(getBaseContext(), kode);
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
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		FragmentManager fm = getSupportFragmentManager();
		boolean check = Security.comparePassword(getBaseContext(), kode);
		if(check){
			FragmentLeggTilListe leggTil = new FragmentLeggTilListe();
	        leggTil.show(fm, "fragment_dialog_create");
		} else {
			//Do nothing, wrong password.
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
