package me.t3sl4.kurye.Util.HTTP;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

public interface HTTPResponseListener {
    void onSuccess(JSONObject response) throws JSONException;
    void onError(VolleyError error);
}
