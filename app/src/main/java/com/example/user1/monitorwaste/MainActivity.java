package com.example.user1.monitorwaste;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void mapClicked (View v) {
        Intent mapIntent = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(mapIntent);
    }
    public void loginClicked (View v) {
        Intent logIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(logIntent);
    }

}
