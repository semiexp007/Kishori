package com.uietsocial.kishori;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class vcmessage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vcmessage);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    @Override
    public boolean onSupportNavigateUp() {

        // startActivity(new Intent( Searchadd.this,Home.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
        return super.onSupportNavigateUp();
    }
}