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

	public static List<DataBolk> getDataBolks(Context context){
		List<DataBolk> entries = null;
			try {
				Object objectFromFile = readObject(context, FILENAME);
				entries = (ArrayList<DataBolk>) objectFromFile;
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
