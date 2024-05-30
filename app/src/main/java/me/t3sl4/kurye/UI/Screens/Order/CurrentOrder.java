package me.t3sl4.kurye.UI.Screens.Order;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.OnMapsSdkInitializedCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.kofigyan.stateprogressbar.StateProgressBar;

import me.t3sl4.kurye.R;
import me.t3sl4.kurye.UI.Components.NavigationBar.NavigationBarUtil;
import me.t3sl4.kurye.Util.GoogleMaps.FetchURL;
import me.t3sl4.kurye.Util.GoogleMaps.OnTaskDoneListener;

public class CurrentOrder extends FragmentActivity implements OnMapReadyCallback, OnTaskDoneListener, OnMapsSdkInitializedCallback {

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

    //UI Components:
    private StateProgressBar orderStateBar;
    String[] stateNames;
    private TextView orderETATextView;
    private TextView orderIDTextView;
    private TextView orderDetailsTextView;
    private TextView orderTotalPriceTextView;
    private TextView clientNameSurnameTextView;
    private TextView clientAddressTextView;
    private Button callClientButton;
    private Button deliverOrderButton;
    private LinearLayout orderDetailsLayout;

    private BottomSheetBehavior<LinearLayout> bottomSheetBehavior;

    //Location Variables:
    LatLng originLatLng;
    LatLng destinationLatLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_order);

        NavigationBarUtil.hideNavigationBar(this);

        initializeComponents();

        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int googlePlayServicesStatus = apiAvailability.isGooglePlayServicesAvailable(this);
        if (googlePlayServicesStatus == ConnectionResult.SUCCESS) {
            MapsInitializer.initialize(getApplicationContext(), MapsInitializer.Renderer.LATEST, this);
        } else {
            Log.e("MapsDemo", "Google Play Services are not available: " + apiAvailability.getErrorString(googlePlayServicesStatus));
            if (apiAvailability.isUserResolvableError(googlePlayServicesStatus)) {
                apiAvailability.getErrorDialog(this, googlePlayServicesStatus, 0).show();
            }
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
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
        originLatLng = new LatLng(37.85068712946069, 27.25600338766238);
        destinationLatLng = new LatLng(37.86565628149133, 27.262955034100262);

        String originText = getApplicationContext().getString(R.string.map_text_origin);
        String destinationText = getApplicationContext().getString(R.string.map_text_origin);
        String courierText = getApplicationContext().getString(R.string.map_text_origin);

        originMarker = mMap.addMarker(new MarkerOptions()
                .position(originLatLng)
                .title(originText)
                .icon(BitmapDescriptorFactory.fromBitmap(resizeBitmap(R.drawable.ikon_carrier, 100, 100))));

        destinationMarker = mMap.addMarker(new MarkerOptions()
                .position(destinationLatLng)
                .title(destinationText)
                .icon(BitmapDescriptorFactory.fromBitmap(resizeBitmap(R.drawable.ikon_destination, 100, 100))));

        courierMarker = mMap.addMarker(new MarkerOptions()
                .position(originLatLng)
                .title(courierText)
                .icon(BitmapDescriptorFactory.fromBitmap(resizeBitmap(R.drawable.ikon_carrier, 100, 100))));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(originLatLng, 18));

        String url = getDirectionsUrl(origin, destination);
        new FetchURL(CurrentOrder.this).execute(url, "driving");
    }

    @Override
    public void onMapsSdkInitialized(MapsInitializer.Renderer renderer) {
        switch (renderer) {
            case LATEST:
                Log.d("MapsDemo", "The latest version of the renderer is used.");
                break;
            case LEGACY:
                Log.d("MapsDemo", "The legacy version of the renderer is used.");
                break;
        }
    }

    private String getDirectionsUrl(String origin, String destination) {
        String str_origin = "origin=" + origin;
        String str_dest = "destination=" + destination;
        String mode = "mode=driving";
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        String output = "json";
        String key = getString(R.string.google_maps_key);
        return "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + key;
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        int routeColor = ContextCompat.getColor(this, R.color.primaryColor);
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
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(courierLocation, 18));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(originLatLng, 18));
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

    private void initializeComponents() {
        orderStateBar = findViewById(R.id.orderStateBar);
        orderETATextView = findViewById(R.id.orderETATextView);
        orderIDTextView = findViewById(R.id.orderIDTextView);
        orderDetailsTextView = findViewById(R.id.orderDetailsTextView);
        orderTotalPriceTextView = findViewById(R.id.orderTotalPriceTextView);
        clientNameSurnameTextView = findViewById(R.id.clientNameSurnameTextView);
        clientAddressTextView = findViewById(R.id.clientAddressTextView);
        callClientButton = findViewById(R.id.callClientButton);
        deliverOrderButton = findViewById(R.id.deliverOrderButton);
        orderDetailsLayout = findViewById(R.id.order_details);

        stateNames = new String[]{
                getString(R.string.current_taken),
                getString(R.string.current_on_way),
                getString(R.string.current_delivered)
        };

        orderStateBar.setStateDescriptionData(stateNames);

        orderETATextView.setText("Teslime Kalan Süre: \n4 dk");
        orderIDTextView.setText("#23124123123");
        orderDetailsTextView.setText("Kapıda Ödeme");
        orderTotalPriceTextView.setText("123.45 ₺");

        orderDetailsLayout.post(() -> {
            bottomSheetBehavior = BottomSheetBehavior.from(orderDetailsLayout);
            bottomSheetBehavior.setPeekHeight(findViewById(R.id.orderStateBar).getHeight() + 24); //marginTop + marginBottom
            bottomSheetBehavior.setHideable(false);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

            bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    } else if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                        // Handle the bottom sheet expanded state if needed
                    } else if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                        // Handle the bottom sheet collapsed state if needed
                    }
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                    // Handle slide events
                }
            });
        });
    }
}