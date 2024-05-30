package me.t3sl4.kurye.UI.Screens.User;

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

import com.irozon.sneaker.Sneaker;
import com.kofigyan.stateprogressbar.StateProgressBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import me.t3sl4.kurye.R;
import me.t3sl4.kurye.UI.Components.NavigationBar.NavigationBarUtil;
import me.t3sl4.kurye.UI.Screens.Authentication.RegisterFragments.Step1Fragment;
import me.t3sl4.kurye.UI.Screens.Authentication.RegisterFragments.Step2Fragment;
import me.t3sl4.kurye.UI.Screens.Authentication.RegisterFragments.Step3Fragment;
import me.t3sl4.kurye.UI.Screens.Authentication.RegisterFragments.Step4Fragment;

public class EditProfile extends AppCompatActivity {
    //Main Variables:
    String[] stateNames;

    //Header Section:
    private ImageView backButtonImage;
    private StateProgressBar editProfileStateBar;
    private ViewPager2 editProfileViewPager;

    //Bottom Section:
    private ImageView stepOncekiImageView;
    private ImageView stepSonrakiImageView;
    private Button editProfileButton;

    //Fragment Management:
    private List<Fragment> fragmentList = new ArrayList<>();
    private Map<Integer, Bundle> fragmentData = new HashMap<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_edit);

        NavigationBarUtil.hideNavigationBar(this);

        initializeComponents();

        fragmentAdapterInitialize();

        headerComponentsClickListeners();

        bottomComponentsClickListeners();

        if (fragmentData.containsKey(1)) {
            String encodedImage = Objects.requireNonNull(fragmentData.get(1)).getString("profilePhoto");
            if (encodedImage != null) {
                Step2Fragment step2Fragment = (Step2Fragment) fragmentList.get(1);
                step2Fragment.setProfilePhoto(encodedImage);
            }
        } else if(fragmentData.containsKey(3)) {
            String encodedLicenseFront = Objects.requireNonNull(fragmentData.get(3)).getString("licenseFront");
            String encodedLicenseBack = Objects.requireNonNull(fragmentData.get(3)).getString("licenseBack");

            Step4Fragment step4Fragment = (Step4Fragment) fragmentList.get(3);

            if (encodedLicenseFront != null) {
                step4Fragment.setLicenseFront(encodedLicenseFront);
            }

            if(encodedLicenseBack != null) {
                step4Fragment.setLicenseBack(encodedLicenseBack);
            }
        }
    }

    private void initializeComponents() {
        backButtonImage = findViewById(R.id.backButtonImage);
        editProfileStateBar = findViewById(R.id.editProfileStateBar);
        editProfileViewPager = findViewById(R.id.editProfileViewPager);

        stepOncekiImageView = findViewById(R.id.stepOncekiImageView);
        stepSonrakiImageView = findViewById(R.id.stepSonrakiImageView);
        editProfileButton = findViewById(R.id.editProfileButton);

        stateNames = new String[]{
                getString(R.string.register_fragment_auth),
                getString(R.string.register_fragment_personal),
                getString(R.string.register_fragment_relative),
                getString(R.string.register_fragment_license)
        };

        editProfileStateBar.setStateDescriptionData(stateNames);

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

        editProfileViewPager.setAdapter(adapter);
        editProfileViewPager.setUserInputEnabled(false); // Disable swipe
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

    private void bottomComponentsClickListeners() {
        stepSonrakiImageView.setOnClickListener(v -> {
            int currentItem = editProfileViewPager.getCurrentItem();
            if (currentItem < fragmentList.size() - 1) {
                saveFragmentData(currentItem);
                editProfileViewPager.setCurrentItem(currentItem + 1);
                editProfileStateBar.setCurrentStateNumber(StateProgressBar.StateNumber.values()[currentItem + 1]);
                if (currentItem + 1 == fragmentList.size() - 1) {
                    editProfileButton.setVisibility(View.VISIBLE);
                    stepSonrakiImageView.setVisibility(View.INVISIBLE);
                }
                stepOncekiImageView.setVisibility(View.VISIBLE);
            }
        });

        stepOncekiImageView.setOnClickListener(v -> {
            int currentItem = editProfileViewPager.getCurrentItem();
            if (currentItem > 0) {
                editProfileViewPager.setCurrentItem(currentItem - 1);
                editProfileStateBar.setCurrentStateNumber(StateProgressBar.StateNumber.values()[currentItem - 1]);
                if (currentItem - 1 == 0) {
                    stepOncekiImageView.setVisibility(View.GONE);
                }
                editProfileButton.setVisibility(View.GONE);
                stepSonrakiImageView.setVisibility(View.VISIBLE);
            }
        });

        editProfileButton.setOnClickListener(v -> {
            saveFragmentData(editProfileViewPager.getCurrentItem());
            if(allDataValid()) {
                logFragmentData();
            } else {
                Sneaker.with(EditProfile.this).setTitle("Hata !").setMessage("Lütfen eksik kısımları kontrol edin!").sneakError();
            }
        });
    }

    private void headerComponentsClickListeners() {
        backButtonImage.setOnClickListener(v -> {
            finish();
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
}