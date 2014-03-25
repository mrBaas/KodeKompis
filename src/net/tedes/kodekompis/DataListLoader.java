package net.tedes.kodekompis;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public class DataListLoader extends AsyncTaskLoader<List<DataBolk>> {
	
		List<DataBolk> mDataBolks;
		
		public DataListLoader(Context context){
			super(context);
		}

		@Override
		public List<DataBolk> loadInBackground() {
			//Metode som laster inn data i bakgrunnen
			
			//Test av entries
			//REMEMBER TO IMPLEMENT DECRYPTION THROUGH SECURITY.JAVA HERE, AFTER FIRST READ/WRITE TESTS.
			//NB: HARDCODED PASSWORD BELOW!
			
			ArrayList<DataBolk> entries = InternalStorage.readList(getContext(), "1234");
			
			
//			int counter = 0;
//			while(counter < 1000){
//				entries.add(new DataBolk(counter,"Facebook" + counter, "Joeran","Rassh�l"));
//				counter++;
//			}
			return entries;
			
		}
	
		
		/**
		*Metode som blir kalt n�r det er ny data som skal til klienten
		*
		*
		*/
		@Override
		public void deliverResult(List<DataBolk> listOfData) {
			if(isReset()){
				if(listOfData != null){
					onReleaseResources(listOfData);
				}
			}
			List<DataBolk> oldApps = listOfData;
			mDataBolks = listOfData;
			
			if(isStarted()){
				//Hvis Loader allerede er startet, kan vi umiddelbart 
				//levere resultatet
				super.deliverResult(listOfData);
			}
			
			//P� dette tidspunktet kan vi se bort fra resources knyttet til "oldApps"
			//Siden det nye resultatet er levert vet vi at det ikke er i bruk
			if (oldApps != null){
				onReleaseResources(oldApps);
			}
		}
		
		/**
		*
		*H�ndterer foresp�rseln om � starte loader
		*
		*/
		@Override
		protected void onStartLoading(){
			if(mDataBolks != null){
				//Hvis vi har et resultat tilgjengelig, lever det umiddelbart
				deliverResult(mDataBolks);
			}
			
			if (takeContentChanged() || mDataBolks == null){
				//Hvis dataen er endret siden sist det ble lastet inn,
				//ELLER at det ikke er tilgjengelig, begynn � laste inn.
				forceLoad();
			}
		}
		
		/**
		*H�ndterer foresp�rsel om � stoppe loader
		*/
		@Override 
		protected void onStopLoading(){
			//Pr�ver � stoppe loadingen om det er mulig
			cancelLoad();
		}
		
		/**
		*H�ndterer foresp�rsel om � avbryte loader
		*/
		@Override
		public void onCanceled(List<DataBolk> apps){
			//P� dette tidspunktet kan vi gi slipp p� resources assosiert med "apps"
			//hvis n�dvendig
			onReleaseResources(apps);
		}
		
		/**
		*H�ndterer foresp�rsel om � resette loader fullstendig
		*/
		@Override
		protected void onReset(){
			super.onReset();
			
			//Forsikrer oss om at loader er stoppet
			onStopLoading();
			
			//P� dette tidspunktet kan vi gi slipp p� resources assosiert med "apps"
			//hvis n�dvendig
			if(mDataBolks !=null){
				onReleaseResources(mDataBolks);
				mDataBolks = null;
			}
		}
		
		/**
         * Hjelper metode som tar h�nd om � gi slipp p� resources assosiert
         * med en actively loaded data set.
         */
        protected void onReleaseResources(List<DataBolk> apps) {
        	
        }
		
}
