package com.example.mobileshopapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobileshopapp.models.ShopItem;

import java.util.ArrayList;

public class DeliveryInformation extends AppCompatActivity {

    ArrayList<ShopItem> userCart;

    EditText fullName;
    EditText contactNumber;
    EditText address;
    EditText baranggay;
    EditText landmark;
    EditText notes;
    Button checkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_information);

        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setTitle("Delivery Information");
        }

        initializeAttributes();
        Intent intent = getIntent();
        getValuesFromIntent(intent);

        checkout.setOnClickListener(v -> {
            if (areRelevantFieldsFilled()) {
                Toast toast = Toast.makeText(getApplicationContext(), "Successfully checked out!", Toast.LENGTH_SHORT);
                toast.show();

                Intent intent1 = new Intent(getApplicationContext(), HomePage.class);
                startActivity(intent1);
            }

        });
    }

    /**
     * Initializing of attributes, "binding" to variables
     */
    private void initializeAttributes() {
        fullName = findViewById(R.id.customer_name);
        contactNumber = findViewById(R.id.customer_number);
        address = findViewById(R.id.customer_address);
        baranggay = findViewById(R.id.customer_baranggay);
        landmark = findViewById(R.id.customer_landmark);
        notes = findViewById(R.id.customer_notes);
        checkout = findViewById(R.id.checkout_button);
    }

    /**
     * Get values from the Intent from the parent activity
     *
     * @param intent the intent sent by the parent activity
     */
    private void getValuesFromIntent(Intent intent) {
        userCart = (ArrayList<ShopItem>) intent.getSerializableExtra("userCart");
        if (intent.hasExtra("deliveryInfo")) {
            ArrayList<String> info = intent.getStringArrayListExtra("deliveryInfo");
            if (info.size() > 0) {
                fullName.setText(info.get(0));
                contactNumber.setText(info.get(1));
                address.setText(info.get(2));
                baranggay.setText(info.get(3));
                landmark.setText(info.get(4));
                notes.setText(info.get(5));
            }
        }
    }

    /**
     * Check if the relevant fields are filled up by the user;
     *
     * @return boolean value which will decide if the user can check out or not
     */
    private boolean areRelevantFieldsFilled() {
        if (TextUtils.isEmpty(fullName.getText())) {
            promptUser("Full name");
            return false;
        }

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

    /**
     * Get a String from the selected EditText field
     *
     * @param field the chosen EditText field by the user
     * @return the String which the user wrote
     */
    private String getDataFromField(EditText field) {
        if (!TextUtils.isEmpty(field.getText())) {
            return field.getText().toString();
        }
        return "";
    }

    /**
     * Show toast with the appropriate prompt for the user. Invoked when relevant fields are empty.
     *
     * @param field the field that the user is required to answer
     */
    private void promptUser(String field) {
        Toast toast = Toast.makeText(this, String.format("%s is required", field), Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * Create Intent to pass data to the parent activity when using the Up button in Ancestral Navigation
     *
     * @param item the item chosen by the user from the action bar
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent data = new Intent();

            data.putExtra("fullName", getDataFromField(fullName));
            data.putExtra("contactNumber", getDataFromField(contactNumber));
            data.putExtra("address", getDataFromField(address));
            data.putExtra("baranggay", getDataFromField(baranggay));
            data.putExtra("landmark", getDataFromField(landmark));
            data.putExtra("notes", getDataFromField(notes));
            data.putExtra("userCart", userCart);

            setResult(RESULT_OK, data);
            finish();
            return true;
        }
        return (super.onOptionsItemSelected(item));
    }
}