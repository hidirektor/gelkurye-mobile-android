package me.t3sl4.gelkurye.Screens.Order;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Looper;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.Polyline;

import me.t3sl4.gelkurye.R;
import me.t3sl4.gelkurye.Util.Util.GoogleMaps.FetchURL;
import me.t3sl4.gelkurye.Util.Util.GoogleMaps.OnTaskDoneListener;

public class CurrentOrder extends FragmentActivity implements OnMapReadyCallback, OnTaskDoneListener {

    private GoogleMap mMap;
    private String origin = "37.85068712946069,27.25600338766238"; // Başlangıç noktası
    private String destination = "37.86565628149133,27.262955034100262"; // Varış noktası
    private Polyline currentPolyline;
    private Marker originMarker;
    private Marker destinationMarker;
    private Marker courierMarker;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    private LatLng courierLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_current);

        TextView orderIdText = findViewById(R.id.order_id);
        TextView orderDetailsText = findViewById(R.id.order_details_text);

        orderIdText.setText("Order ID: 12345");
        orderDetailsText.setText("Details: 2 Pizzas, 1 Coke");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        requestLocationUpdates();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng originLatLng = new LatLng(37.85068712946069, 27.25600338766238);
        LatLng destinationLatLng = new LatLng(37.86565628149133, 27.262955034100262);

        originMarker = mMap.addMarker(new MarkerOptions()
                .position(originLatLng)
                .title("Origin")
                .icon(BitmapDescriptorFactory.fromBitmap(resizeBitmap(R.drawable.carrier_ikon, 100, 100))));

        destinationMarker = mMap.addMarker(new MarkerOptions()
                .position(destinationLatLng)
                .title("Destination")
                .icon(BitmapDescriptorFactory.fromBitmap(resizeBitmap(R.drawable.destination_ikon, 100, 100))));

        courierMarker = mMap.addMarker(new MarkerOptions()
                .position(originLatLng)
                .title("Courier")
                .icon(BitmapDescriptorFactory.fromBitmap(resizeBitmap(R.drawable.carrier_ikon, 100, 100))));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(originLatLng, 10));

        String url = getDirectionsUrl(origin, destination);
        new FetchURL(CurrentOrder.this).execute(url, "driving");
    }

    private String getDirectionsUrl(String origin, String destination) {
        String str_origin = "origin=" + origin;
        String str_dest = "destination=" + destination;
        String mode = "mode=driving";
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        String output = "json";
        String key = getString(R.string.google_maps_key);
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + key;
        return url;
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        int routeColor = ContextCompat.getColor(this, R.color.navbarButtonColor);
        currentPolyline = mMap.addPolyline(((PolylineOptions) values[0]).width(10).color(routeColor));
    }

    private Bitmap resizeBitmap(int drawableRes, int width, int height) {
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(), drawableRes);
        return Bitmap.createScaledBitmap(imageBitmap, width, height, false);
    }

    private void requestLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null) {
                    courierLocation = new LatLng(locationResult.getLastLocation().getLatitude(), locationResult.getLastLocation().getLongitude());
                    courierMarker.setPosition(courierLocation);
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(courierLocation, 15));
                }
            }
        }, Looper.getMainLooper());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requestLocationUpdates();
            } else {
                Toast.makeText(this, "Location permission is required", Toast.LENGTH_SHORT).show();
            }
        }
    }
}