package net.tedes.kodekompis;

import android.annotation.SuppressLint;
import android.content.Context;

public class ManageClipboard {

	private static String CLIPBOARD_LABEL = "TedesClip";
	
	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	public static void setClipboard(Context context, String content) {
		int sdk = android.os.Build.VERSION.SDK_INT;
		if(sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
		    android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
		    clipboard.setText(content);
		} else {
		    android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE); 
		    android.content.ClipData clip = android.content.ClipData.newPlainText(CLIPBOARD_LABEL,content);
		    clipboard.setPrimaryClip(clip);
		}
	}
}
