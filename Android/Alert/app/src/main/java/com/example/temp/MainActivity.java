package com.example.temp;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button button;
    EditText fname, lname;
    SharedPreferences sp;
    AlertDialog.Builder alert;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sp = getSharedPreferences("MyPref", MODE_PRIVATE);

        String fullName = sp.getString("full_name", null);
        if (fullName != null) {
            switchActivity(fullName);
        }

        setContentView(R.layout.activity_main);

        button = findViewById(R.id.submitData);
        fname = findViewById(R.id.fName);
        lname = findViewById(R.id.lName);

        alert = new AlertDialog.Builder(this);
        alert.setTitle("Save Data");
        alert.setMessage("Are you sure you want to save this data?");

        alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String firstName = fname.getText().toString().trim();
                String lastName = lname.getText().toString().trim();

                if (!firstName.isEmpty() && !lastName.isEmpty()) {
                    String fullNameSave = firstName + ", " + lastName;

                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("full_name", fullNameSave);
                    editor.apply();
                    switchActivity(fullNameSave);
                } else {
                    Toast.makeText(MainActivity.this, "Please Enter Full Name", Toast.LENGTH_SHORT).show();
                }
            }
        });

        alert.setNegativeButton("Rewrite", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                alert.setCancelable(false);
            }
        });

        button.setOnClickListener(v -> {
            alert.show();
        });
    }

    @SuppressLint("ObsoleteSdkInt")
    private void switchActivity(String fullName) {
        boolean api35 = false;
        int ApiCode = Build.VERSION.SDK_INT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            api35 = true;
        }
        Intent intent = new Intent(MainActivity.this, TempActivity.class);
        intent.putExtra("full_name", fullName);
        intent.putExtra("api35", api35);
        intent.putExtra("ApiCode", ApiCode);
        startActivity(intent);
        finish();
    }
}