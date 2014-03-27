package net.tedes.kodekompis;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import android.content.Context;

public final class InternalStorage {

	private InternalStorage() {}
	private static final String FILENAME_OBJECTLIST = "tedesliste";
	private static final String FILENAME_KODELIST = "rotliste";

	public static ArrayList<DataBolk> readList(Context context, String password) {
		ArrayList<DataBolk> bolks = Security.dekrypterListe(readListEncrypted(context), password);
		return bolks;
	}
	
	public static ArrayList<DataBolkEncrypted> readListEncrypted(Context context){
		ArrayList<DataBolkEncrypted> entries = null;
			try {
				Object objectFromFile = readObject(context, FILENAME_OBJECTLIST);
				entries = (ArrayList<DataBolkEncrypted>) objectFromFile;
			} catch (StreamCorruptedException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return entries;
	}
	
	public static void writeList(Context context, ArrayList<DataBolk> bolks, String password){
		ArrayList<DataBolkEncrypted> bolksEncrypted = Security.krypterListe(bolks, password);
		writeListEncrypted(context, bolksEncrypted);
	}
	
	public static void writeListEncrypted(Context context, ArrayList<DataBolkEncrypted> entries){
		try {
			writeObject(context, FILENAME_OBJECTLIST, entries);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//WARNING!, invoking this method will reset the stored list!
	public static void deleteList(Context context) {
		ArrayList<DataBolkEncrypted> temp = new ArrayList<DataBolkEncrypted>();
		writeListEncrypted(context, temp);
	}
	
	public static void deletePassword(Context context){
		String password = "";
		writeKode(context, password);
	}
	
	public static String readKode(Context context){
		String hash = null;
			try {
				Object objectFromFile = readObject(context, FILENAME_KODELIST);
				hash = (String) objectFromFile;
			} catch (StreamCorruptedException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(hash.equals("") || hash == null) {
				throw new RuntimeException();
			}
			
		return hash;
	}
	
	public static void writeKode(Context context, String hash){
		try {
			writeObject(context, FILENAME_KODELIST, hash);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void writeObject(Context context, String key, Object object) throws IOException {
		FileOutputStream fos = context.openFileOutput(key, Context.MODE_PRIVATE);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(object);
		oos.close();
		fos.close();
	}

	private static Object readObject(Context context, String key) throws FileNotFoundException, StreamCorruptedException, IOException, ClassNotFoundException {
		FileInputStream fis = context.openFileInput(key);
		ObjectInputStream ois = new ObjectInputStream(fis);
		Object object = ois.readObject();
		return object;
	}
}
