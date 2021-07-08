package com.example.mobileshopapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MortyDetail extends AppCompatActivity {
    private TextView nameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morty_detail);
        Intent intent = getIntent();
        Morty morty = (Morty)intent.getSerializableExtra("mortyObject");
        Log.d("Shop", "This is a test");

        nameTextView = findViewById(R.id.item_page_name);
        nameTextView.setText(morty.getName());
    }
}