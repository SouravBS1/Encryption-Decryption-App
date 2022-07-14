package com.example.encryptdecrypt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void encrypt(View v){
        Intent i = new Intent(this,Encrypt.class);
        startActivity(i);
    }
    public void decrypt(View v){
        Intent i = new Intent(this,Decrypt.class);
        startActivity(i);
    }
}