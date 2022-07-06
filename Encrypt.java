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

public class Encrypt extends AppCompatActivity {
    TextView encryptedText;
    ClipboardManager cp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encrypt);
        encryptedText = findViewById(R.id.tvVar1);
        cp = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void enc(View v) throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException,
            BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException{
        String ciphertext = Encrypt.givenString_whenEncrypt_thenSuccess();
        encryptedText.setText(ciphertext);
    }
    public void copy(View v){
        String data = encryptedText.getText().toString().trim();
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

    public static IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String encrypt(String algorithm, String input, SecretKey key,
                                 IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] cipherText = cipher.doFinal(input.getBytes());
        return Base64.getEncoder().encodeToString(cipherText);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String givenString_whenEncrypt_thenSuccess()
            throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException,
            BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException {

        String input = "baeldung";
        SecretKey key = Encrypt.generateKey(128);
        //IvParameterSpec ivParameterSpec = Encrypt.generateIv();
        String encodedKey = "e7yGowuZf0W29W6oslUQAg==";
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);

        String algorithm = "AES/CBC/PKCS5Padding";
        String cipherText = Encrypt.encrypt(algorithm, input, key, new IvParameterSpec(decodedKey, 0, decodedKey.length));
        return cipherText;
       // String plainText = Encrypt.decrypt(algorithm, cipherText, key, ivParameterSpec);
       // Assertions.assertEquals(input, plainText);
    }


}