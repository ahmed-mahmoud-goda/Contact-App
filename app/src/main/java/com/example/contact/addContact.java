package com.example.contact;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class addContact extends AppCompatActivity {

    private EditText nametxt;
    private EditText phonetxt;
    private Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        nametxt = findViewById(R.id.editTextText);
        phonetxt = findViewById(R.id.editTextPhone);
        MyDatabase dbHelper = new MyDatabase(this);

        btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nametxt.getText().toString().trim();
                String phoneNumber = phonetxt.getText().toString().trim();

                if (name.isEmpty() || phoneNumber.isEmpty()){
                    Toast.makeText(addContact.this, "Please enter both name and phone number", Toast.LENGTH_SHORT).show();
                    return;
                }

                long result = dbHelper.addContact(name, phoneNumber);
                if (result != -1) {
                    Toast.makeText(addContact.this, "Contact added", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(addContact.this,MainActivity.class);
                    finish();
                    startActivity(i);
                } else {
                    Toast.makeText(addContact.this, "Error adding contact", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }
}