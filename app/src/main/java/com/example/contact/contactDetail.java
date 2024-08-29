package com.example.contact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class contactDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);
        MyDatabase db = new MyDatabase(this);
        TextView nameTextView = findViewById(R.id.textView2);
        TextView phoneNumberTextView = findViewById(R.id.textView3);
        ImageButton btn = findViewById(R.id.imageButton);
        ImageButton btn2 = findViewById(R.id.imageButton2);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteContact(getIntent().getIntExtra("id",-1));
                Intent i = new Intent(contactDetail.this,MainActivity.class);
                finish();
                startActivity(i);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePhoneCall(getIntent().getStringExtra("phone"));
            }
        });

        String name = getIntent().getStringExtra("name");
        String phoneNumber = getIntent().getStringExtra("phone");

        nameTextView.setText(name);
        phoneNumberTextView.setText(phoneNumber);
    }
    private void makePhoneCall(String phoneNumber) {
        if (phoneNumber != null && phoneNumber.trim().length() > 0) {
            if (ContextCompat.checkSelfPermission(contactDetail.this,
                    Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(callIntent);
            } else {
                ActivityCompat.requestPermissions(contactDetail.this,
                        new String[]{Manifest.permission.CALL_PHONE}, 1);
            }
        } else {
            Toast.makeText(this, "Phone number is invalid", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall(getIntent().getStringExtra("phone"));
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
