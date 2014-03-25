package net.tedes.kodekompis;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

public final class InternalStorage {

	private InternalStorage() {}
	private static final String FILENAME = "tedesliste";

	public static ArrayList<DataBolk> readList(Context context, String password) {
		ArrayList<DataBolk> bolks = Security.dekrypterListe(readListEncrypted(context), password);
		return bolks;
	}
	
	public static ArrayList<DataBolkEncrypted> readListEncrypted(Context context){
		ArrayList<DataBolkEncrypted> entries = null;
			try {
				Object objectFromFile = readObject(context, FILENAME);
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
	
	public static void writeListEncrypted(Context context, ArrayList<DataBolkEncrypted> entries){
		try {
			writeObject(context, FILENAME, entries);
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
