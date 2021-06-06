package ru.mirea.starcev.yandexmaps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKit;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.layers.ObjectEvent;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.user_location.UserLocationLayer;
import com.yandex.mapkit.user_location.UserLocationObjectListener;
import com.yandex.mapkit.user_location.UserLocationView;
import com.yandex.runtime.image.ImageProvider;

public class MainActivity extends AppCompatActivity implements UserLocationObjectListener {
    private MapView mapView;
    private final String MAPKIT_API_KEY = "4109d94c-d7ef-4e3f-b967-8feaf367ce9c";
    private UserLocationLayer userLocationLayer;
    private String[] Permissions = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!hasPermissions(this, Permissions))
            requestPermissions(Permissions, 100);
        MapKitFactory.setApiKey(MAPKIT_API_KEY);
        MapKitFactory.initialize(this);
        setContentView(R.layout.activity_main);
        mapView = findViewById(R.id.mapview);
        mapView.getMap().move(
                new CameraPosition(new Point(55.751574, 37.573856), 8.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
    }
    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
        MapKitFactory.getInstance().onStart();
        loadUserLocationLayer();
    }

    private boolean hasPermissions(Context context, String[] permissions) {
        for(String p : permissions)
            if (ActivityCompat.checkSelfPermission(context, p) != PackageManager.PERMISSION_GRANTED)
                return false;
        return true;
    }

    private void loadUserLocationLayer(){
        MapKit mapKit = MapKitFactory.getInstance();
        userLocationLayer = mapKit.createUserLocationLayer(mapView.getMapWindow());
        userLocationLayer.setVisible(true);
        userLocationLayer.setHeadingEnabled(true);
        userLocationLayer.setObjectListener(this);
    }

    @Override
    public void onObjectAdded(@NonNull UserLocationView userLocationView) {
        userLocationLayer.setAnchor(
                new PointF((float)(mapView.getWidth() * 0.5), (float)(mapView.getHeight() *
                        0.5)),
                new PointF((float)(mapView.getWidth() * 0.5), (float)(mapView.getHeight() *
                        0.83)));
        // При определении направления движения устанавливается следующая иконка
        userLocationView.getArrow().setIcon(ImageProvider.fromResource(
                this,android.R.drawable.star_big_on ));
        // При получении координат местоположения устанавливается следующая иконка
        userLocationView.getPin().setIcon(ImageProvider.fromResource(
                this, android.R.drawable.ic_menu_mylocation));
        userLocationView.getAccuracyCircle().setFillColor(Color.BLUE);
    }

    @Override
    public void onObjectRemoved(@NonNull UserLocationView userLocationView) {

    }

    @Override
    public void onObjectUpdated(@NonNull UserLocationView userLocationView, @NonNull ObjectEvent objectEvent) {

    }
}