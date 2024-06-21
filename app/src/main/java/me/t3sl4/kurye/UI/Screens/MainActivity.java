package me.t3sl4.kurye.UI.Screens;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import me.t3sl4.kurye.R;
import me.t3sl4.kurye.UI.Components.NavigationBar.NavigationBarUtil;
import me.t3sl4.kurye.UI.Screens.General.Authentication.Login;
import me.t3sl4.kurye.UI.Screens.General.Authentication.MerchantRegister;
import me.t3sl4.kurye.UI.Screens.General.Authentication.Register;
import me.t3sl4.kurye.Util.Utils;
import me.t3sl4.kurye.kurye.UI.Components.Sneaker.Sneaker;

public class MainActivity extends AppCompatActivity {

    private final int locationPermissioCode = 123;
    String[] permissionsList = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION};

    boolean permissionGranted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_register_selection);

        NavigationBarUtil.hideNavigationBar(this);

        Button loginButton = findViewById(R.id.loginButton);
        Button registerButton = findViewById(R.id.registerButton);
        Button merhcantRegisterButton = findViewById(R.id.merchantRegisterButton);

        permissionGranted = Utils.checkPermissions(MainActivity.this, permissionsList, locationPermissioCode);

        loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Login.class);
            if(permissionGranted) {
                startActivity(intent);
            } else {
                Sneaker.with(MainActivity.this).setTitle("Hata !").setMessage("Uygulamayı kullanabilmek için lokasyon izni gereklidir. Lütfen yeniden başlatın!").sneakError();
            }
        });

        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Register.class);
            startActivity(intent);
        });

        merhcantRegisterButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MerchantRegister.class);
            startActivity(intent);
        });
    }
}
