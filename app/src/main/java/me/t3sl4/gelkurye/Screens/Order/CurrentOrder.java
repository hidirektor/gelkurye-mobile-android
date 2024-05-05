package me.t3sl4.gelkurye.Screens.Order;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.TravelMode;
import com.google.maps.model.Unit;
import com.google.type.DateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import me.t3sl4.gelkurye.R;

public class CurrentOrder extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private static final int overview = 0;
    private final int PLACE_AUTOCOMPLETE_REQUEST_CODE_FROM = 201;
    private final int PLACE_AUTOCOMPLETE_REQUEST_CODE_TO = 202;
    private String TAG = getClass().getSimpleName();
    private GoogleMap mMap;
    private TextView mTvFrom;
    private TextView mTvTo;
    //private String GOOGLE_PLACES_API_KEY = "YOUR GOOGLE PLACES API KEY";

    private List<Marker> markers;
    private Polyline polyline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_current);
        bindActivity();
    }

    private void bindActivity() {
        mTvFrom = findViewById(R.id.tv_from);
        mTvTo = findViewById(R.id.tv_to);

        findViewById(R.id.get_directions).setOnClickListener(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mTvFrom.setOnClickListener(this);
        mTvTo.setOnClickListener(this);
    }

    private void setupGoogleMapScreenSettings(GoogleMap mMap) {
        mMap.setBuildingsEnabled(true);
        UiSettings mUiSettings = mMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(true);
        mUiSettings.setCompassEnabled(true);
        mUiSettings.setMyLocationButtonEnabled(true);
        mUiSettings.setScrollGesturesEnabled(true);
        mUiSettings.setZoomGesturesEnabled(true);
        mUiSettings.setTiltGesturesEnabled(true);
        mUiSettings.setRotateGesturesEnabled(true);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setupGoogleMapScreenSettings(googleMap);
        LatLng latLng = new LatLng(13.060530, 80.242329);
        mMap.addMarker(new MarkerOptions().position(latLng).title("Nungambakkam, Chennai, Tamil Nadu"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
    }

    @Override
    public void onClick(View v) {

    }

    /*@Override
    public void onClick(View view) {
        if(view.getId() == R.id.tv_from) {
            pickLocation(PLACE_AUTOCOMPLETE_REQUEST_CODE_FROM);
        } else if(view.getId() == R.id.tv_to) {
            pickLocation(PLACE_AUTOCOMPLETE_REQUEST_CODE_TO);
        } else if(view.getId() == R.id.get_directions) {
            if (mTvFrom.length() <= 0) {
                showMessage("Please pick from address");
                return;
            }
            if (mTvTo.length() <= 0) {
                showMessage("Please pick to address");
                return;
            }
            getResult();
        }
    }

    private void getResult() {
        DirectionsResult results = getDirectionsDetails(mTvFrom.getText().toString(), mTvTo.getText().toString(), TravelMode.DRIVING);
        if (results != null) {
            addPolyline(results);
            addMarkersToMap(results);
            positionCamera(results.routes[overview]);
        }
    }

    private void addMarkersToMap(DirectionsResult results) {
        Marker markerSrc = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(results.routes[overview].legs[overview].startLocation.lat, results.routes[overview].legs[overview].startLocation.lng))
                .title(results.routes[overview].legs[overview].startAddress));
        Marker markerDes = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(results.routes[overview].legs[overview].endLocation.lat, results.routes[overview].legs[overview].endLocation.lng))
                .title(results.routes[overview].legs[overview].endAddress)
                .snippet(getEndLocationTitle(results)));
        markers = new ArrayList<>();
        markers.add(markerSrc);
        markers.add(markerDes);
    }

    private void positionCamera(DirectionsRoute route) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker marker : markers) {
            builder.include(marker.getPosition());
        }
        LatLngBounds bounds = builder.build();
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 150);
        mMap.animateCamera(cu);
    }*/

    /*private void addPolyline(DirectionsResult results) {
        if (polyline != null) {
            polyline.remove();
        }
        List<LatLng> decodedPath = PolyUtil.decode(results.routes[overview].overviewPolyline.getEncodedPath());
        polyline = mMap.addPolyline(new PolylineOptions().addAll(decodedPath));
    }

    private String getEndLocationTitle(DirectionsResult results) {
        return "Time :" + results.routes[overview].legs[overview].duration.humanReadable + " Distance :" + results.routes[overview].legs[overview].distance.humanReadable;
    }

    private GeoApiContext.Builder getGeoContext() {
        return new GeoApiContext.Builder()
                .apiKey(getString(R.string.google_maps_key))
                .queryRateLimit(3)
                .queryRateLimitPeriod(1, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS);
    }

    private DirectionsResult getDirectionsDetails(String origin, String destination, TravelMode mode) {
        try {
            return DirectionsApi.newRequest(getGeoContext().build())
                    .mode(mode)
                    .origin(origin)
                    .destination(destination)
                    .departureTime(new DateTime())
                    .await();
        } catch (ApiException | InterruptedException | IOException e) {
            e.printStackTrace();
            showMessage(e.getMessage());
            return null;
        }
    }

    private void pickLocation(int requestCode) {
        try {
            AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                    .setCountry("IN")
                    .build();

            Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS))
                    .setTypeFilter(typeFilter)
                    .build(this);
            startActivityForResult(intent, requestCode);
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            showMessage(e.getMessage());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Place place = Autocomplete.getPlaceFromIntent(data);
            Log.i(TAG, "Place: " + place.getName());
            setResultText(place, requestCode);
        } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
            Status status = Autocomplete.getStatusFromIntent(data);
            showMessage(status.getStatusMessage());
        }
    }

    private void setResultText(Place place, int requestCode) {
        //TODO: Implement setResultText method
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }*/
}