package com.example.contact;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv;
    private ContactAdapter contactsAdapter;
    private ImageButton btn;
    private List<Contacts> contacts;
    private MyDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        rv = findViewById(R.id.recycleview);
        db = new MyDatabase(this);
        contacts = getAllContacts();
        btn = findViewById(R.id.imageButton);
        contactsAdapter = new ContactAdapter(this, contacts);
        RecyclerView.LayoutManager layoutmanager = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(layoutmanager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(contactsAdapter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, addContact.class);
                startActivity(intent);
            }
        });
    }
    private List<Contacts> getAllContacts() {
        List<Contacts> contactsList = new ArrayList<>();
        Cursor cursor = db.getAllContacts();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow("phone"));
            contactsList.add(new Contacts(id, name, phoneNumber));
        }

        cursor.close();
        return contactsList;
    }
}