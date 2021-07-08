package com.example.mobileshopapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MortyDetail extends AppCompatActivity {
    private TextView nameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morty_detail);
        Intent intent = getIntent();
        Morty morty = (Morty) intent.getSerializableExtra("mortyObject");
        Log.d("Shop", "This is a test");

        nameTextView = findViewById(R.id.item_page_name);
        nameTextView.setText(morty.getName());
    }

    public void incrementItem(View view) {
        EditText currentItemCount = findViewById(R.id.item_count);
        Integer currentCount = Integer.parseInt(currentItemCount.getText().toString());
        currentCount++;
        currentItemCount.setText(currentCount.toString());
    }

    public void decrementItem(View view) {
        EditText currentItemCount = findViewById(R.id.item_count);
        Integer currentCount = Integer.parseInt(currentItemCount.getText().toString());
        if (currentCount == 0) {
            Toast toast = Toast.makeText(getApplicationContext(), "Item count can't be negative", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            currentCount--;
            currentItemCount.setText(currentCount.toString());
        }
    }
}