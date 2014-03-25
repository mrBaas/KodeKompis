package net.tedes.kodekompis;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import android.util.Base64;
import android.util.Log;

public class Security {
	
	//These final values could eventually be written to preferences upon first start.
	private static final String TAG = Security.class.getSimpleName();
	private static final int SALT_BYTE_SIZE = 32;
	private static final String DELIMITER = "]";
	private static int KEY_LENGTH = 256;
    //iteration count determines run time for en-/decryption
    private static int ITERATION_COUNT = 1000;
    private static final String ALGORITHM_PBKDF2 = "PBKDF2WithHmacSHA1";
    private static final String ALGORITHM_CIPHER = "AES/CBC/PKCS5Padding";
	
	
	//Salt and IV is kept alongside with the encrypted text, separated by DELIMITER, in Base64 format.
		
	public static SecretKey generateKey(String password, byte[] salt) {
        try {
            KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, ITERATION_COUNT, KEY_LENGTH);
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(ALGORITHM_PBKDF2);
            byte[] keyBytes = secretKeyFactory.generateSecret(keySpec).getEncoded();
            SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");
            return secretKey;
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }
	
	public static String krypter(String plaintext, SecretKey key, byte[] salt) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM_CIPHER);
            byte[] iv = generateIv(cipher.getBlockSize());
            IvParameterSpec ivParams = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, key, ivParams);
            byte[] cipherText = cipher.doFinal(plaintext.getBytes("UTF-8"));
            return String.format("%s%s%s%s%s", toBase64(salt), DELIMITER, toBase64(iv), DELIMITER, toBase64(cipherText));
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
	
	public static String dekrypter(String kryptert, String password) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		String[] fields = kryptert.split("]");
		byte[] salt = fromBase64(fields[0]);
		byte[] iv = fromBase64(fields[1]);
		byte[] cipherBytes = fromBase64(fields[2]);
		
		SecretKey key = generateKey(password, salt);
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		IvParameterSpec ivParams = new IvParameterSpec(iv);
		cipher.init(Cipher.DECRYPT_MODE, key, ivParams);
		byte[] plaintext = cipher.doFinal(cipherBytes);
		String plainStr = new String(plaintext , "UTF-8");
		return plainStr;
	}

  //Generate a random salt. Remember to keep salt stored safely.
	public static byte[] generateSalt() {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[SALT_BYTE_SIZE];
		random.nextBytes(salt);
		return salt;
	}
	
    public static byte[] generateIv(int length) {
        SecureRandom random = new SecureRandom();
        byte[] iv = new byte[length];
        random.nextBytes(iv);
        return iv;
    }
	
    public static String toHex(byte[] bytes) {
        StringBuffer buff = new StringBuffer();
        for (byte b : bytes) {
            buff.append(String.format("%02X", b));
        }
        return buff.toString();
    }
    
	public static String toBase64(byte[] bytes) {
        return Base64.encodeToString(bytes, Base64.NO_WRAP);
    }
	
	public static byte[] fromBase64(String base64) {
        return Base64.decode(base64, Base64.NO_WRAP);
    }
}
