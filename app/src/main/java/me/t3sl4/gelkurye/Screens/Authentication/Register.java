package me.t3sl4.gelkurye.Screens.Authentication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.irozon.sneaker.Sneaker;
import com.kofigyan.stateprogressbar.StateProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import me.t3sl4.gelkurye.R;
import me.t3sl4.gelkurye.Screens.Authentication.RegisterFragments.Step1Fragment;
import me.t3sl4.gelkurye.Screens.Authentication.RegisterFragments.Step2Fragment;
import me.t3sl4.gelkurye.Screens.Authentication.RegisterFragments.Step3Fragment;
import me.t3sl4.gelkurye.Screens.Authentication.RegisterFragments.Step4Fragment;
import me.t3sl4.gelkurye.Util.HTTP.HTTPHelper;
import me.t3sl4.gelkurye.Util.HTTP.HTTPResponseListener;
import me.t3sl4.gelkurye.Util.HTTP.TokenManager;
import me.t3sl4.gelkurye.Util.Utils;

public class Register extends AppCompatActivity {
    String[] stateNames;

    // Header Components:
    private ViewPager2 registerViewPager;
    private StateProgressBar registerStateBar;

    // Bottom Buttons:
    private Button registerButton;
    private ImageView stepOncekiImageView, stepSonrakiImageView;

    // Fragment Management:
    private List<Fragment> fragmentList = new ArrayList<>();
    private Map<Integer, Bundle> fragmentData = new HashMap<>();

    // HTTPHelper and TokenManager
    private HTTPHelper httpHelper;
    private TokenManager tokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_register);

        Utils.getInstance().getNavigationBar().hideNavigationBar(this);

        initializeComponents();
        fragmentAdapterInitialize();
        bottomComponentsClickListeners();

        httpHelper = HTTPHelper.getInstance(this);
        tokenManager = new TokenManager(this);

        if (fragmentData.containsKey(1)) {
            String encodedImage = Objects.requireNonNull(fragmentData.get(1)).getString("profilePhoto");
            if (encodedImage != null) {
                Step2Fragment step2Fragment = (Step2Fragment) fragmentList.get(1);
                step2Fragment.setProfilePhoto(encodedImage);
            }
        } else if (fragmentData.containsKey(3)) {
            String encodedLicenseFront = Objects.requireNonNull(fragmentData.get(3)).getString("licenseFront");
            String encodedLicenseBack = Objects.requireNonNull(fragmentData.get(3)).getString("licenseBack");

            Step4Fragment step4Fragment = (Step4Fragment) fragmentList.get(3);

            if (encodedLicenseFront != null) {
                step4Fragment.setLicenseFront(encodedLicenseFront);
            }

            if (encodedLicenseBack != null) {
                step4Fragment.setLicenseBack(encodedLicenseBack);
            }
        }
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

        stateNames = new String[]{
                getString(R.string.register_fragment_auth),
                getString(R.string.register_fragment_personal),
                getString(R.string.register_fragment_relative),
                getString(R.string.register_fragment_license)
        };

        registerStateBar.setStateDescriptionData(stateNames);

        // Fragment Definition:
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
                    stepSonrakiImageView.setVisibility(View.INVISIBLE);
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
            if (allDataValid()) {
                logFragmentData();
                registerUser();
            } else {
                Sneaker.with(Register.this).setTitle("Hata !").setMessage("Lütfen eksik kısımları kontrol edin!").sneakError();
            }
        });
    }

    private boolean allDataValid() {
        for (int i = 0; i < fragmentData.size(); i++) {
            Bundle data = fragmentData.get(i);
            if (data != null) {
                for (String key : data.keySet()) {
                    if (data.getString(key) == null || Objects.requireNonNull(data.getString(key)).isEmpty()) {
                        return false;
                    }
                }
            } else {
                return false;
            }
        }
        return true;
    }

    private void registerUser() {
        String endpoint = "register";

        Map<String, String> params = new HashMap<>();
        params.put("username", Objects.requireNonNull(fragmentData.get(0)).getString("username"));
        params.put("email", Objects.requireNonNull(fragmentData.get(0)).getString("email"));
        params.put("password", Objects.requireNonNull(fragmentData.get(0)).getString("password"));
        params.put("nameSurname", Objects.requireNonNull(fragmentData.get(1)).getString("nameSurname"));
        params.put("phoneNumber", Objects.requireNonNull(fragmentData.get(1)).getString("phoneNumber"));
        params.put("address", Objects.requireNonNull(fragmentData.get(1)).getString("address"));
        params.put("profilePhoto", Objects.requireNonNull(fragmentData.get(1)).getString("profilePhoto"));
        params.put("relativeNameSurname", Objects.requireNonNull(fragmentData.get(2)).getString("relativeNameSurname"));
        params.put("relativePhoneNumber", Objects.requireNonNull(fragmentData.get(2)).getString("relativePhoneNumber"));
        params.put("licenseFrontFace", Objects.requireNonNull(fragmentData.get(3)).getString("licenseFront"));
        params.put("licenseBackFace", Objects.requireNonNull(fragmentData.get(3)).getString("licenseBack"));

        httpHelper.makeRequest(
                Request.Method.POST,
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
                            Log.d("RegisterSuccess", "AccessToken: " + accessToken);
                            Log.d("RegisterSuccess", "RefreshToken: " + refreshToken);
                            saveTokens(accessToken, refreshToken);
                            Sneaker.with(Register.this).setTitle("Başarılı!").setMessage("Kayıt işlemi başarılı!").sneakSuccess();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Sneaker.with(Register.this).setTitle("Hata !").setMessage("Yanıt işlenirken bir hata oluştu!").sneakError();
                        }
                    }

                    @Override
                    public void onError(VolleyError error) {
                        error.printStackTrace();
                        Sneaker.with(Register.this).setTitle("Hata !").setMessage("Kayıt işlemi başarısız!").sneakError();
                    }
                },
                tokenManager
        );
    }

    private void saveTokens(String accessToken, String refreshToken) {
        SharedPreferences sharedPreferences = getSharedPreferences("TokenPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("accessToken", accessToken);
        editor.putString("refreshToken", refreshToken);
        editor.apply();
    }
}