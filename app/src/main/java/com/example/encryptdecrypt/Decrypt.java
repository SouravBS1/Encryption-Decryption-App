package com.example.encryptdecrypt;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Decrypt extends AppCompatActivity {
    EditText txt1,txt2;
    TextView decryptedText;
    ClipboardManager cp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decrypt);
        txt1 = findViewById(R.id.etVar1);
        txt2 = findViewById(R.id.etVar2);
        decryptedText = findViewById(R.id.tvVar2);
        cp = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void dec(View v) throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException,
            BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException {
        String ciphertext = txt1.getText().toString();
        String secretkey = txt2.getText().toString();
        String plaintext = Decrypt.givenString_whenEncrypt_thenSuccess(ciphertext,secretkey);
        Toast.makeText(this, "Decrypted successfully!", Toast.LENGTH_SHORT).show();
        decryptedText.setText(plaintext);
    }
    public void copy1(View v){
        String data = decryptedText.getText().toString().trim();
        if(!data.isEmpty()){
            ClipData temp = ClipData.newPlainText("text",data);
            cp.setPrimaryClip(temp);
            Toast.makeText(this,"Copied",Toast.LENGTH_SHORT).show();
        }
    }
    public static SecretKey generateKey(int n) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(n);
        SecretKey key = keyGenerator.generateKey();
        return key;
    }

//    public static IvParameterSpec generateIv() {
//        byte[] iv = new byte[16];
//        new SecureRandom().nextBytes(iv);
//        return new IvParameterSpec(iv);
//    }

//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public static String encrypt(String algorithm, String input, SecretKey key,
//                                 IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
//            InvalidAlgorithmParameterException, InvalidKeyException,
//            BadPaddingException, IllegalBlockSizeException {
//
//        Cipher cipher = Cipher.getInstance(algorithm);
//        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
//        byte[] cipherText = cipher.doFinal(input.getBytes());
//        return Base64.getEncoder().encodeToString(cipherText);
//    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String decrypt(String algorithm, String cipherText, SecretKey key,
                                 IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(cipherText));
        return new String(plainText);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String givenString_whenEncrypt_thenSuccess(String cipherText,String secretkey)
            throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException,
            BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException {

        byte[] decoKey = Base64.getDecoder().decode(secretkey);
        SecretKey key = new SecretKeySpec(decoKey, 0, decoKey.length, "AES");

        //SecretKey key = Decrypt.generateKey(128);
        //IvParameterSpec ivParameterSpec = Encrypt.generateIv();
        String encodedKey = "e7yGowuZf0W29W6oslUQAg==";
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(decodedKey, 0, decodedKey.length);
        String algorithm = "AES/CBC/PKCS5Padding";
        //String cipherText = Encrypt.encrypt(algorithm, input, key, new IvParameterSpec(decodedKey, 0, decodedKey.length));

         String plainText = Decrypt.decrypt(algorithm, cipherText, key, ivParameterSpec );
        return plainText;
    }
}