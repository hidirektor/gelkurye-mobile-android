package me.t3sl4.gelkurye.Util.Util.GoogleMaps;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DirectionsJSONParser {

    public List<List<LatLng>> parse(JSONObject jObject) {
        List<List<LatLng>> routes = new ArrayList<>();
        JSONArray jRoutes;
        JSONArray jLegs;
        JSONArray jSteps;
        try {
            jRoutes = jObject.getJSONArray("routes");
            for (int i = 0; i < jRoutes.length(); i++) {
                jLegs = ((JSONObject) jRoutes.get(i)).getJSONArray("legs");
                List<LatLng> path = new ArrayList<>();
                for (int j = 0; j < jLegs.length(); j++) {
                    jSteps = ((JSONObject) jLegs.get(j)).getJSONArray("steps");
                    for (int k = 0; k < jSteps.length(); k++) {
                        String polyline = (String) ((JSONObject) ((JSONObject) jSteps.get(k)).get("polyline")).get("points");
                        List<LatLng> list = PolyUtil.decode(polyline);
                        path.addAll(list);
                    }
                }
                routes.add(path);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return routes;
    }
}