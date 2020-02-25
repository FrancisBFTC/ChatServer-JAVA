package chat.cryptography;


import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class encryptAES {
	
    
	public static byte[] encrypt(String text, String key, String iv) throws Exception{
		Cipher encrypt = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
		SecretKeySpec secretKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
		encrypt.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv.getBytes("UTF-8")));
		return encrypt.doFinal(text.getBytes("UTF-8"));
	}
	
	public static String decrypt(byte[] decrypted, String key, String iv) throws Exception{
        Cipher decript = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
        SecretKeySpec SecretKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
        decript.init(Cipher.DECRYPT_MODE, SecretKey,new IvParameterSpec(iv.getBytes("UTF-8")));
        return new String(decript.doFinal(decrypted), "UTF-8");
	 }
}
