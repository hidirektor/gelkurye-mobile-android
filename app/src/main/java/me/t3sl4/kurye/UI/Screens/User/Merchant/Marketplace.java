package me.t3sl4.kurye.UI.Screens.User.Merchant;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import me.t3sl4.kurye.Model.User.UserModel;
import me.t3sl4.kurye.R;
import me.t3sl4.kurye.UI.Components.NavigationBar.NavigationBarUtil;
import me.t3sl4.kurye.UI.Components.NiceSwitch.NiceSwitch;
import me.t3sl4.kurye.Util.Utils;

public class Marketplace extends AppCompatActivity {
    private UserModel currentProfile;

    private NiceSwitch synchTrendyol;
    private NiceSwitch synchGetirYemek;
    private NiceSwitch synchYemekSepeti;

    private LinearLayout findTrendyol;
    private LinearLayout findGetirYemek;
    private LinearLayout findYemekSepeti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marketplace);

        NavigationBarUtil.hideNavigationBar(this);

        componentInitialize();

        currentProfile = Utils.getFromSharedPreferences(this);
    }

    private void componentInitialize() {
        synchTrendyol = findViewById(R.id.trendyolYemekSwitch);
        synchGetirYemek = findViewById(R.id.getirYemekSwitch);
        synchYemekSepeti = findViewById(R.id.yemeksepetiSwitch);

        findTrendyol = findViewById(R.id.findTrendyolButton);
        findGetirYemek = findViewById(R.id.findGetirYemekButton);
        findYemekSepeti = findViewById(R.id.findYemekSepetiButton);
    }
}
