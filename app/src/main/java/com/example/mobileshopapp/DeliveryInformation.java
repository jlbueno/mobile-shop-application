package com.example.mobileshopapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DeliveryInformation extends AppCompatActivity {
    EditText fullName;
    EditText contactNumber;
    EditText address;
    EditText baranggay;
    EditText landmark;
    EditText notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_information);

        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setTitle("Delivery Information");
        }

        fullName = findViewById(R.id.customer_name);
        contactNumber = findViewById(R.id.customer_number);
        address = findViewById(R.id.customer_address);
        baranggay = findViewById(R.id.customer_baranggay);
        landmark = findViewById(R.id.customer_landmark);
        notes = findViewById(R.id.customer_notes);
        Button checkout = findViewById(R.id.checkout_button);

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(areRelevantFieldsFilled()) {
                    // Change this with implementation for Checkout logic.
                    Toast toast = Toast.makeText(getApplicationContext(), "Successfully checked out!", Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        });
    }

    private boolean areRelevantFieldsFilled() {
        // Check if the relevant fields are filled out.
        if (TextUtils.isEmpty(fullName.getText())) {
            promptUser("Full name");
            return false;
        }

        // Add check for number format.
        if (TextUtils.isEmpty(contactNumber.getText())) {
            promptUser("Contact number");
            return false;
        } else if (!contactNumber.getText().toString().startsWith("09") || contactNumber.getText().toString().length() != 11) {
            Toast toast = Toast.makeText(getApplicationContext(), "Please follow the format 09XXXXXXXXX", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }

        if (TextUtils.isEmpty(address.getText())) {
            promptUser("Address");
            return false;
        }

        if (TextUtils.isEmpty(baranggay.getText())) {
            promptUser("Baranggay");
            return false;
        }

        return true;
    }

    private void promptUser(String field) {
        Toast toast = Toast.makeText(this, String.format("%s is required", field), Toast.LENGTH_SHORT);
        toast.show();
    }
}