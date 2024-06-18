package me.t3sl4.kurye.Util.GoogleMaps;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ParserTask {
    private final OnTaskDoneListener taskListener;
    private final Executor executor;
    private final Handler handler;

    public ParserTask(OnTaskDoneListener taskListener) {
        this.taskListener = taskListener;
        this.executor = Executors.newSingleThreadExecutor();
        this.handler = new Handler(Looper.getMainLooper());
    }

    public void execute(String... jsonData) {
        executor.execute(() -> {
            JSONObject jObject;
            List<List<LatLng>> routes = null;
            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();
                routes = parser.parse(jObject);
            } catch (JSONException e) {
                Log.e("ParserTask", e.toString());
            }
            List<List<LatLng>> finalRoutes = routes;
            handler.post(() -> {
                PolylineOptions lineOptions = new PolylineOptions();
                if (finalRoutes != null) {
                    for (List<LatLng> route : finalRoutes) {
                        lineOptions.addAll(route);
                    }
                }
                taskListener.onTaskDone(lineOptions);
            });
        });
    }
}