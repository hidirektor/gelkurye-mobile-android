package me.t3sl4.kurye.UI.Screens.Authentication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.hbb20.CountryCodePicker;
import com.irozon.sneaker.Sneaker;
import com.zpj.widget.checkbox.ZCheckBox;

import org.json.JSONException;
import org.json.JSONObject;

import me.t3sl4.kurye.Model.User.Carrier;
import me.t3sl4.kurye.R;
import me.t3sl4.kurye.UI.Components.EditText.EditTextUtil;
import me.t3sl4.kurye.UI.Components.NavigationBar.NavigationBarUtil;
import me.t3sl4.kurye.UI.Components.PasswordField.PasswordFieldUtil;
import me.t3sl4.kurye.UI.Screens.General.Dashboard;
import me.t3sl4.kurye.UI.Screens.PasswordReset.Reset1;
import me.t3sl4.kurye.Util.ReqUtil;

public class Login extends AppCompatActivity {
    private CountryCodePicker phoneNumberCode;
    private EditText phoneNumberField;
    private EditText passwordField;
    private Button loginButton;
    private ZCheckBox rememberMe;
    private TextView sifremiUnuttumButton;

    private boolean isRemembered = false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        NavigationBarUtil.hideNavigationBar(this);

        phoneNumberCode = findViewById(R.id.phoneNumberContryCode);
        phoneNumberField = findViewById(R.id.phoneNumberEditText);
        passwordField = findViewById(R.id.editTextPassword);
        rememberMe = findViewById(R.id.beniHatirlaCheckBox);
        sifremiUnuttumButton = findViewById(R.id.sifremiUnuttumText);
        loginButton = findViewById(R.id.loginButton);

        isRemembered = rememberMe.isChecked();

        PasswordFieldUtil.setChangeablePasswordField(passwordField, getApplicationContext());
        EditTextUtil.blockFirstZeroCharacter(phoneNumberField, Login.this);
        loginButton.setOnClickListener(v -> {
            try {
                loginUser();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });

        sifremiUnuttumButton.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, Reset1.class);
            startActivity(intent);
        });
    }

    private void loginUser() throws JSONException {
        JSONObject params = new JSONObject();
        String inputPhoneNumber = phoneNumberField.getText().toString();
        String inputPassword = passwordField.getText().toString();
        if(inputPhoneNumber.isEmpty() || inputPassword.isEmpty()) {
            Sneaker.with(Login.this).setTitle("Hata !").setMessage("Lütfen gerekli alanları kontrol edin!").sneakError();
        } else {
            params.put("phoneNumber", phoneNumberCode.getSelectedCountryCode() + phoneNumberField.getText().toString());
            params.put("password", passwordField.getText().toString());

            ReqUtil.loginReq(this, params, new ReqUtil.GeneralCallback() {
                @Override
                public void onSuccess() {
                    ReqUtil.getProfileReq(Login.this, phoneNumberCode.getSelectedCountryCode() + phoneNumberField.getText().toString(), new ReqUtil.ProfileCallback() {
                        @Override
                        public void onSuccess(Carrier profile) {
                            Intent dashboardIntent = new Intent(Login.this, Dashboard.class);
                            dashboardIntent.putExtra("profile", profile);
                            startActivity(dashboardIntent);
                        }

                        @Override
                        public void onError() {
                            Sneaker.with(Login.this).setTitle("Hata !").setMessage("Profil alınamadı!").sneakError();
                        }
                    });
                }

                @Override
                public void onError() {
                    Sneaker.with(Login.this).setTitle("Hata !").setMessage("Giriş başarısız!").sneakError();
                }
            });
        }
    }

    private void saveTokens(String accessToken, String refreshToken) {
        SharedPreferences sharedPreferences = getSharedPreferences("TokenPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("accessToken", accessToken);
        editor.putString("refreshToken", refreshToken);
        editor.apply();
    }
}