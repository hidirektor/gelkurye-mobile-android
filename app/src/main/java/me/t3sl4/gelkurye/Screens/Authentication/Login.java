package me.t3sl4.gelkurye.Screens.Authentication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.hbb20.CountryCodePicker;
import com.irozon.sneaker.Sneaker;
import com.zpj.widget.checkbox.ZCheckBox;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import me.t3sl4.gelkurye.R;
import me.t3sl4.gelkurye.Screens.General.Dashboard;
import me.t3sl4.gelkurye.Screens.PasswordReset.Reset1;
import me.t3sl4.gelkurye.Util.Component.PasswordField.PasswordFieldTouchListener;
import me.t3sl4.gelkurye.Util.HTTP.HTTPHelper;
import me.t3sl4.gelkurye.Util.HTTP.HTTPResponseListener;
import me.t3sl4.gelkurye.Util.HTTP.TokenManager;
import me.t3sl4.gelkurye.Util.Utils;

public class Login extends AppCompatActivity {
    private CountryCodePicker phoneNumberCode;
    private EditText phoneNumberField;
    private EditText passwordField;
    private Button loginButton;
    private ZCheckBox rememberMe;
    private TextView sifremiUnuttumButton;

    private boolean isRemembered = false;
    private HTTPHelper httpHelper;
    private TokenManager tokenManager;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_login);

        Utils.getInstance().getNavigationBar().hideNavigationBar(this);

        phoneNumberCode = findViewById(R.id.phoneNumberContryCode);
        phoneNumberField = findViewById(R.id.phoneNumberEditText);
        passwordField = findViewById(R.id.editTextPassword);
        rememberMe = findViewById(R.id.beniHatirlaCheckBox);
        sifremiUnuttumButton = findViewById(R.id.sifremiUnuttumText);
        loginButton = findViewById(R.id.loginButton);

        httpHelper = HTTPHelper.getInstance(this);
        tokenManager = new TokenManager(this);

        isRemembered = rememberMe.isChecked();

        PasswordFieldTouchListener.setChangeablePasswordField(passwordField, getApplicationContext());
        loginButton.setOnClickListener(v -> loginUser());

        sifremiUnuttumButton.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, Reset1.class);
            startActivity(intent);
        });
    }

    private void loginUser() {
        String endpoint = "login";

        Map<String, String> params = new HashMap<>();
        params.put("phoneNumber", phoneNumberField.getText().toString());
        params.put("password", passwordField.getText().toString());

        httpHelper.makeRequest(
                com.android.volley.Request.Method.POST,
                endpoint,
                new JSONObject(params),
                false,
                new HTTPResponseListener() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        try {
                            String accessToken = response.getString("accessToken");
                            String refreshToken = response.getString("refreshToken");
                            // Tokenları saklayın veya işleyin
                            Log.d("LoginSuccess", "AccessToken: " + accessToken);
                            Log.d("LoginSuccess", "RefreshToken: " + refreshToken);
                            saveTokens(accessToken, refreshToken);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Sneaker.with(Login.this).setTitle("Hata !").setMessage("Yanıt işlenirken bir hata oluştu!").sneakError();
                        }
                    }

                    @Override
                    public void onError(VolleyError error) {
                        error.printStackTrace();
                        Sneaker.with(Login.this).setTitle("Hata !").setMessage("Giriş işlemi başarısız!").sneakError();
                    }
                },
                tokenManager
        );

        Intent intent = new Intent(Login.this, Dashboard.class);
        startActivity(intent);
    }

    private void saveTokens(String accessToken, String refreshToken) {
        SharedPreferences sharedPreferences = getSharedPreferences("TokenPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("accessToken", accessToken);
        editor.putString("refreshToken", refreshToken);
        editor.apply();
    }
}