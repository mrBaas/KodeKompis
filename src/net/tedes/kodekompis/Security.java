package net.tedes.kodekompis;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Security {

	//When creating PIN, save securekey and salt on internal storage.
	//Use same method when comparing user pin input for access later.
	
	public static final int SALT_BYTE_SIZE = 32;
	
	public static byte[] generateSalt() {
		//Generate a random salt. Remember to keep salt stored safely.
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[SALT_BYTE_SIZE];
		random.nextBytes(salt);
		return salt;
		
	}
	
	//The salt should be a random string, again generated using SecureRandom
	//and persisted on internal storage alongside any encrypted data.
	//A new random salt must be generated each time a user creates an account or changes their password. 
	
	public static SecretKey generateKey(char[] passphraseOrPin, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
	    // Number of PBKDF2 hardening rounds to use. Larger values increase
	    // computation time. You should select a value that causes computation
	    // to take >100ms.
	    final int iterations = 1000; 

	    // Generate a 256-bit key
	    final int outputKeyLength = 256;

	    SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
	    KeySpec keySpec = new PBEKeySpec(passphraseOrPin, salt, iterations, outputKeyLength);
	    SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);
	    return secretKey;
	}
	
}
