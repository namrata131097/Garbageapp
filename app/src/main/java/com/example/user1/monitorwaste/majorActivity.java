package com.example.user1.monitorwaste;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class majorActivity extends AppCompatActivity implements LocationListener {
    private TextView topText, bottomText;
    private static final int IMAGE_RES = 6;
    private Button buttonUpload,button1;
    private ImageView imgWaste;
    private Location location;
    LocationManager mLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_major);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        topText = (TextView) findViewById(R.id.textView2);
        bottomText = (TextView) findViewById(R.id.textView4);
        buttonUpload = (Button) findViewById(R.id.buttonUpload);
        button1 = (Button) findViewById(R.id.button2);
        buttonUpload.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // we expect image
                startActivityForResult(cameraIntent, IMAGE_RES);
            }
        });

        imgWaste = (ImageView) findViewById(R.id.photoreceived);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent mapIntent = new Intent(majorActivity.this, MapsActivity.class);
            startActivity(mapIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IMAGE_RES) {
            bottomText.setVisibility(View.INVISIBLE);
            button1.setVisibility(View.INVISIBLE);
            if (resultCode == 0) {
                Toast.makeText(majorActivity.this, "No image received",
                        Toast.LENGTH_LONG).show();

            }
            else {
                imgWaste.setImageBitmap((Bitmap) data.getExtras().get("data"));
                topText.setText("");
                button1.setVisibility(View.VISIBLE);
                button1.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        locationJob();
                    }
                });
            }

        }
    }

    protected  void locationJob() {
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            locationnotfound();
        } else {
            location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null && location.getTime() > Calendar.getInstance().getTimeInMillis() - 2 * 60 * 1000){
                locationfound(location); }
            else {
                bottomText.setText("looking for location");
                bottomText.setVisibility(View.VISIBLE);
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            }
        }

    }
    public void onProviderDisabled(String arg0) {}
    public void onProviderEnabled(String arg0) {}
    public void onStatusChanged(String arg0, int arg1, Bundle arg2) {}
    void locationnotfound(){
        Toast.makeText(majorActivity.this, "No location available", Toast.LENGTH_LONG).show();
    }

    void locationfound(Location location) {
        Toast.makeText(majorActivity.this, "location found", Toast.LENGTH_LONG).show();
        bottomText.setText("Latitude is "+location.getLatitude()+"\nLongitude is "+location.getLongitude());
        bottomText.setVisibility(View.VISIBLE);
    }

    public void onLocationChanged(Location location) {
        if (location != null) {
            mLocationManager.removeUpdates(this);
            locationfound(location);
        } else locationnotfound();
    }

}
