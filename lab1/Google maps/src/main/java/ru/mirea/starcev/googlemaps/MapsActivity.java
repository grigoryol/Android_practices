package ru.mirea.starcev.googlemaps;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;

    private String[] Permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!hasPermissions(this, Permissions)) {
            requestPermissions(Permissions, 100);
        }
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(
                new LatLng(55.751244, 37.618423)).zoom(8).build();
        mMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));
        try {
            mMap.setMyLocationEnabled(true);
        }catch (Exception e){
            Toast.makeText(this, "Not enough permissions", Toast.LENGTH_SHORT).show();
        }
        mMap.setOnMapClickListener(this);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setTrafficEnabled(true);

    }

    @Override
    public void onMapClick(LatLng latLng) {
        CameraPosition cameraPosition = new CameraPosition.Builder().target(
                latLng).zoom(12).build();
        mMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));
        mMap.addMarker(new MarkerOptions().title("Где я?")
                .snippet("Новое место").position(latLng));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private boolean hasPermissions(Context context, String[] permissions) {
        for(String p : permissions)
            if (ActivityCompat.checkSelfPermission(context, p) != PackageManager.PERMISSION_GRANTED)
                return false;
        return true;
    }
}