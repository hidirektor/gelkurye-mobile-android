package me.t3sl4.gelkurye.Screens.Order;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;

import me.t3sl4.gelkurye.R;

public class CurrentOrder extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng deviceLocation = new LatLng(38.49812851901897, 27.706092943097573);
    private LatLng targetLocation = new LatLng(38.491881428107426, 27.705858825909353);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_current);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        Bitmap deviceIcon = BitmapFactory.decodeResource(getResources(), R.drawable.navbar_home_ikon);
        if (deviceIcon != null) {
            mMap.addMarker(new MarkerOptions()
                    .position(deviceLocation)
                    .title("Hedef Konum")
                    .icon(BitmapDescriptorFactory.fromBitmap(deviceIcon)));
        }

        Bitmap targetIcon = BitmapFactory.decodeResource(getResources(), R.drawable.logo_main);
        if (targetIcon != null) {
            mMap.addMarker(new MarkerOptions()
                    .position(targetLocation)
                    .title("Cihaz Konumu")
                    .icon(BitmapDescriptorFactory.fromBitmap(targetIcon)));
        }

        mMap.setOnCameraIdleListener(() -> mMap.animateCamera(CameraUpdateFactory.zoomTo(mMap.getCameraPosition().zoom)));

        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyBwOPs2oc0XZfNc3ndeZmZpiL3BaH2R4pk")
                .build();
        DirectionsApiRequest request = DirectionsApi.getDirections(context, deviceLocation.latitude + "," + deviceLocation.longitude,
                        targetLocation.latitude + "," + targetLocation.longitude)
                .mode(TravelMode.DRIVING);
        request.setCallback(new PendingResult.Callback<DirectionsResult>() {
            @Override
            public void onResult(DirectionsResult result) {
                if (result.routes != null && result.routes.length > 0) {
                    PolylineOptions polylineOptions = new PolylineOptions();
                    for (com.google.maps.model.LatLng point : result.routes[0].overviewPolyline.decodePath()) {
                        polylineOptions.add(new LatLng(point.lat, point.lng));
                    }
                    Polyline polyline = mMap.addPolyline(polylineOptions);
                    polyline.setColor(Color.rgb(255, 165, 0));
                }
            }

            @Override
            public void onFailure(Throwable e) {
                runOnUiThread(() -> Toast.makeText(CurrentOrder.this, "Yol tarifi alınamadı.", Toast.LENGTH_SHORT).show());
            }
        });
    }
}