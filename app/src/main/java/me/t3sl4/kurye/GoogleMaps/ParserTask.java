package me.t3sl4.kurye.GoogleMaps;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ParserTask extends AsyncTask<String, Integer, List<List<LatLng>>> {
    OnTaskDoneListener taskListener;

    public ParserTask(OnTaskDoneListener taskListener) {
        this.taskListener = taskListener;
    }

    @Override
    protected List<List<LatLng>> doInBackground(String... jsonData) {
        JSONObject jObject;
        List<List<LatLng>> routes = null;
        try {
            jObject = new JSONObject(jsonData[0]);
            DirectionsJSONParser parser = new DirectionsJSONParser();
            routes = parser.parse(jObject);
        } catch (JSONException e) {
            Log.e("ParserTask", e.toString());
        }
        return routes;
    }

    @Override
    protected void onPostExecute(List<List<LatLng>> result) {
        PolylineOptions lineOptions = new PolylineOptions();
        for (int i = 0; i < result.size(); i++) {
            lineOptions.addAll(result.get(i));
        }
        taskListener.onTaskDone(lineOptions);
    }
}
