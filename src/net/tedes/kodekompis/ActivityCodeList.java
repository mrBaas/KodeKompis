package net.tedes.kodekompis;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class ActivityCodeList extends FragmentActivity 
					implements FragmentLeggTilListe.OnNewBolkFinished {
	
	private DataListFragment datalistFragment;
	
	@Override
	public void sendBolkenVidere(DataBolk bolken) {
		datalistFragment.mottaBolken(bolken);
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
		FragmentLeggTilListe leggTil = new FragmentLeggTilListe();
        leggTil.show(fm, "fragment_dialog_create");
        
		return true;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Picking up kode from startscreen
		//this.kode = getIntent().getStringExtra("kode");
				
		setContentView(R.layout.activity_layout_code_list);
		
		//Henter ut getSupportFragmentManager() i stede for getFragmentManager()
		//Støtter tidligere android versjoner
		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
		
		if (fragment == null) {
			fragment = new DataListFragment();
			datalistFragment = (DataListFragment)fragment;
			fm.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
		}
	}
	
}
