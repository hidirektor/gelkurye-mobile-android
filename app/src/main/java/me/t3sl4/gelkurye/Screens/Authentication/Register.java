package me.t3sl4.gelkurye.Screens.Authentication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.kofigyan.stateprogressbar.StateProgressBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.t3sl4.gelkurye.R;
import me.t3sl4.gelkurye.Screens.Authentication.RegisterFragments.Step1Fragment;
import me.t3sl4.gelkurye.Screens.Authentication.RegisterFragments.Step2Fragment;
import me.t3sl4.gelkurye.Screens.Authentication.RegisterFragments.Step3Fragment;
import me.t3sl4.gelkurye.Screens.Authentication.RegisterFragments.Step4Fragment;

public class Register extends AppCompatActivity {
    String[] stateNames = {"Hesap\nBilgileri", "Kişisel\nBilgiler", "Yakınınız", "Yasal"};

    //Header Components:
    private ViewPager2 registerViewPager;
    private StateProgressBar registerStateBar;

    //Bottom Buttons:
    private Button registerButton;
    private ImageView stepOncekiImageView, stepSonrakiImageView;

    //Fragment Management:
    private List<Fragment> fragmentList = new ArrayList<>();
    private Map<Integer, Bundle> fragmentData = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initializeComponents();

        fragmentAdapterInitialize();

        bottomComponentsClickListeners();
    }

    private void saveFragmentData(int position) {
        Fragment fragment = fragmentList.get(position);
        if (fragment != null && fragment.getView() != null) {
            Bundle data = new Bundle();
            if (fragment instanceof Step1Fragment) {
                data.putString("username", ((Step1Fragment) fragment).getUserName());
                data.putString("email", ((Step1Fragment) fragment).getEmail());
                data.putString("password", ((Step1Fragment) fragment).getPassword());
                data.putString("passwordRepeat", ((Step1Fragment) fragment).getPasswordRepeat());
            } else if (fragment instanceof Step2Fragment) {
                data.putString("profilePhoto", ((Step2Fragment) fragment).getProfilePhoto());
                data.putString("nameSurname", ((Step2Fragment) fragment).getNameSurname());
                data.putString("phoneNumber", ((Step2Fragment) fragment).getPhoneNumber());
                data.putString("address", ((Step2Fragment) fragment).getAddress());
            } else if (fragment instanceof Step3Fragment) {
                data.putString("relativeNameSurname", ((Step3Fragment) fragment).getRelativeNameSurname());
                data.putString("relativePhoneNumber", ((Step3Fragment) fragment).getRelativePhoneNumber());
            } else if (fragment instanceof Step4Fragment) {
                data.putString("licenseFront", ((Step4Fragment) fragment).getLicenseFront());
                data.putString("licenseBack", ((Step4Fragment) fragment).getLicenseBack());
            }
            fragmentData.put(position, data);
        }
    }

    private void logFragmentData() {
        for (int i = 0; i < fragmentData.size(); i++) {
            Bundle data = fragmentData.get(i);
            if (data != null) {
                for (String key : data.keySet()) {
                    Log.d("RegisterData", key + ": " + data.getString(key));
                }
            }
        }
    }

    private void initializeComponents() {
        registerViewPager = findViewById(R.id.registerViewPager);
        registerButton = findViewById(R.id.registerButton);
        stepOncekiImageView = findViewById(R.id.stepOncekiImageView);
        stepSonrakiImageView = findViewById(R.id.stepSonrakiImageView);
        registerStateBar = findViewById(R.id.registerStateBar);

        registerStateBar.setStateDescriptionData(stateNames);

        //Fragment Definition:
        fragmentList.add(new Step1Fragment());
        fragmentList.add(new Step2Fragment());
        fragmentList.add(new Step3Fragment());
        fragmentList.add(new Step4Fragment());
    }

    private void fragmentAdapterInitialize() {
        FragmentStateAdapter adapter = new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                Fragment fragment = fragmentList.get(position);
                fragment.setArguments(fragmentData.get(position));
                return fragment;
            }

            @Override
            public int getItemCount() {
                return fragmentList.size();
            }
        };

        registerViewPager.setAdapter(adapter);
        registerViewPager.setUserInputEnabled(false); // Disable swipe
    }

    private void bottomComponentsClickListeners() {
        stepSonrakiImageView.setOnClickListener(v -> {
            int currentItem = registerViewPager.getCurrentItem();
            if (currentItem < fragmentList.size() - 1) {
                saveFragmentData(currentItem);
                registerViewPager.setCurrentItem(currentItem + 1);
                registerStateBar.setCurrentStateNumber(StateProgressBar.StateNumber.values()[currentItem + 1]);
                if (currentItem + 1 == fragmentList.size() - 1) {
                    registerButton.setVisibility(View.VISIBLE);
                    stepSonrakiImageView.setVisibility(View.GONE);
                }
                stepOncekiImageView.setVisibility(View.VISIBLE);
            }
        });

        stepOncekiImageView.setOnClickListener(v -> {
            int currentItem = registerViewPager.getCurrentItem();
            if (currentItem > 0) {
                registerViewPager.setCurrentItem(currentItem - 1);
                registerStateBar.setCurrentStateNumber(StateProgressBar.StateNumber.values()[currentItem - 1]);
                if (currentItem - 1 == 0) {
                    stepOncekiImageView.setVisibility(View.GONE);
                }
                registerButton.setVisibility(View.GONE);
                stepSonrakiImageView.setVisibility(View.VISIBLE);
            }
        });

        registerButton.setOnClickListener(v -> {
            saveFragmentData(registerViewPager.getCurrentItem());
            logFragmentData();
        });
    }
}