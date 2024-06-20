package me.t3sl4.kurye.UI.Screens.General.Authentication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.VolleyError;
import com.irozon.sneaker.Sneaker;
import me.t3sl4.kurye.UI.Components.StateProgressBar.StateProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import me.t3sl4.kurye.R;
import me.t3sl4.kurye.UI.Components.NavigationBar.NavigationBarUtil;
import me.t3sl4.kurye.UI.Screens.General.Authentication.RegisterFragments.Step1Fragment;
import me.t3sl4.kurye.UI.Screens.General.Authentication.RegisterFragments.Step2Fragment;
import me.t3sl4.kurye.UI.Screens.General.Authentication.RegisterFragments.Step5Fragment;
import me.t3sl4.kurye.Util.HTTP.HTTPHelper;
import me.t3sl4.kurye.Util.HTTP.HTTPResponseListener;
import me.t3sl4.kurye.Util.HTTP.TokenManager;
import me.t3sl4.kurye.Util.ReqUtil;

public class MerchantRegister extends AppCompatActivity {
    String[] stateNames;

    // Header Components:
    private ViewPager2 registerViewPager;
    private StateProgressBar registerStateBar;

    // Bottom Buttons:
    private Button registerButton;
    private LinearLayout stepOncekiImageView, stepSonrakiImageView;

    // Fragment Management:
    private List<Fragment> fragmentList = new ArrayList<>();
    private Map<Integer, Bundle> fragmentData = new HashMap<>();

    // HTTPHelper and TokenManager
    private HTTPHelper httpHelper;
    private TokenManager tokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.merchant_register);

        NavigationBarUtil.hideNavigationBar(this);

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
            } else if (fragment instanceof Step5Fragment) {
                data.putString("merchantName", ((Step5Fragment) fragment).getMerchantNameEditText());
                data.putString("merchantAddress", ((Step5Fragment) fragment).getMerchantAddressEditText());
                data.putString("merchantPhoneNumber", ((Step5Fragment) fragment).getMerchantPhoneNumberEditText());
            }
            fragmentData.put(position, data);
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
                getString(R.string.register_fragment_merchant)
        };

        registerStateBar.setStateDescriptionData(stateNames);

        // Fragment Definition:
        fragmentList.add(new Step1Fragment());
        fragmentList.add(new Step2Fragment());
        fragmentList.add(new Step5Fragment());
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
                try {
                    registerUser();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            } else {
                Sneaker.with(MerchantRegister.this).setTitle("Hata !").setMessage("Lütfen eksik kısımları kontrol edin!").sneakError();
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

    private void registerUser() throws JSONException {
        JSONObject params = new JSONObject();
        params.put("userName", Objects.requireNonNull(fragmentData.get(0)).getString("username"));
        params.put("eMail", Objects.requireNonNull(fragmentData.get(0)).getString("email"));
        params.put("userType", "MERCHANT");
        params.put("NameSurname", Objects.requireNonNull(fragmentData.get(1)).getString("nameSurname"));
        params.put("phoneNumber", Objects.requireNonNull(fragmentData.get(1)).getString("phoneNumber"));
        params.put("address", Objects.requireNonNull(fragmentData.get(1)).getString("address"));
        params.put("password", Objects.requireNonNull(fragmentData.get(0)).getString("password"));
        params.put("profilePhoto", Objects.requireNonNull(fragmentData.get(1)).getString("profilePhoto"));
        params.put("merchantName", Objects.requireNonNull(fragmentData.get(2)).getString("merchantName"));
        params.put("merchantAddress", Objects.requireNonNull(fragmentData.get(2)).getString("merchantAddress"));
        params.put("contactNumber", Objects.requireNonNull(fragmentData.get(2)).getString("merchantPhoneNumber"));

        ReqUtil.registerReq(MerchantRegister.this, params, new HTTPResponseListener() {
            @Override
            public void onSuccess(JSONObject response) {
                Sneaker.with(MerchantRegister.this).setTitle("Başarılı!").setMessage("Kayıt işlemi başarılı!").sneakSuccess();
                Intent loginIntent = new Intent(MerchantRegister.this, Login.class);
                startActivity(loginIntent);
                finish();
            }

            @Override
            public void onError(VolleyError error) {
                Log.d("registererror", error.toString());
                Sneaker.with(MerchantRegister.this).setTitle("Hata !").setMessage("Kayıt işlemi başarısız!").sneakError();
            }
        });
    }

    private void saveTokens(String accessToken, String refreshToken) {
        SharedPreferences sharedPreferences = getSharedPreferences("TokenPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("accessToken", accessToken);
        editor.putString("refreshToken", refreshToken);
        editor.apply();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) && view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                ((InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);
    }
}