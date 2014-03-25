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
//				entries.add(new DataBolk(counter,"Facebook" + counter, "Joeran","Rasshøl"));
//				counter++;
//			}
			return entries;
			
		}
	
		
		/**
		*Metode som blir kalt når det er ny data som skal til klienten
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
			
			//På dette tidspunktet kan vi se bort fra resources knyttet til "oldApps"
			//Siden det nye resultatet er levert vet vi at det ikke er i bruk
			if (oldApps != null){
				onReleaseResources(oldApps);
			}
		}
		
		/**
		*
		*Håndterer forespørseln om å starte loader
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
				//ELLER at det ikke er tilgjengelig, begynn å laste inn.
				forceLoad();
			}
		}
		
		/**
		*Håndterer forespørsel om å stoppe loader
		*/
		@Override 
		protected void onStopLoading(){
			//Prøver å stoppe loadingen om det er mulig
			cancelLoad();
		}
		
		/**
		*Håndterer forespørsel om å avbryte loader
		*/
		@Override
		public void onCanceled(List<DataBolk> apps){
			//På dette tidspunktet kan vi gi slipp på resources assosiert med "apps"
			//hvis nødvendig
			onReleaseResources(apps);
		}
		
		/**
		*Håndterer forespørsel om å resette loader fullstendig
		*/
		@Override
		protected void onReset(){
			super.onReset();
			
			//Forsikrer oss om at loader er stoppet
			onStopLoading();
			
			//På dette tidspunktet kan vi gi slipp på resources assosiert med "apps"
			//hvis nødvendig
			if(mDataBolks !=null){
				onReleaseResources(mDataBolks);
				mDataBolks = null;
			}
		}
		
		/**
         * Hjelper metode som tar hånd om å gi slipp på resources assosiert
         * med en actively loaded data set.
         */
        protected void onReleaseResources(List<DataBolk> apps) {
        	
        }
		
}
