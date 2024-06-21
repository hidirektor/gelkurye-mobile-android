package me.t3sl4.kurye.UI.Screens.Merchant.Navbar.Profile;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import me.t3sl4.kurye.Model.User.UserModel;
import me.t3sl4.kurye.R;
import me.t3sl4.kurye.UI.Components.NavigationBar.NavigationBarUtil;
import me.t3sl4.kurye.UI.Components.StateProgressBar.StateProgressBar;
import me.t3sl4.kurye.UI.Screens.General.Authentication.RegisterFragments.Step1Fragment;
import me.t3sl4.kurye.UI.Screens.General.Authentication.RegisterFragments.Step2Fragment;
import me.t3sl4.kurye.UI.Screens.General.Authentication.RegisterFragments.Step3Fragment;
import me.t3sl4.kurye.UI.Screens.General.Authentication.RegisterFragments.Step4Fragment;
import me.t3sl4.kurye.Util.ReqUtil;
import me.t3sl4.kurye.Util.Utils;
import me.t3sl4.kurye.kurye.UI.Components.Sneaker.Sneaker;

public class EditProfile extends AppCompatActivity {
    private UserModel currentProfile;
    // Main Variables:
    String[] stateNames;

    // Header Section:
    private ImageView backButtonImage;
    private StateProgressBar editProfileStateBar;
    private ViewPager2 editProfileViewPager;

    // Bottom Section:
    private ImageView stepOncekiImageView;
    private ImageView stepSonrakiImageView;
    private Button editProfileButton;

    // Fragment Management:
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

        currentProfile = Utils.getFromSharedPreferences(this);
        initializeUser();
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

            if (!allDataValid()) {
                Sneaker.with(EditProfile.this)
                        .setTitle("Hata!")
                        .setMessage("Lütfen eksik kısımları kontrol edin!")
                        .sneakError();
                logFragmentData();
                return;
            }

            if (!dataChanged()) {
                Sneaker.with(EditProfile.this)
                        .setTitle("Uyarı")
                        .setMessage("Herhangi bir değişiklik yapılmadı.")
                        .sneakWarning();
                return;
            }

            UserModel profile = Utils.getFromSharedPreferences(EditProfile.this);
            if (profile != null) {
                for (int i = 0; i < fragmentData.size(); i++) {
                    Bundle data = fragmentData.get(i);
                    if (data != null) {
                        if (i == 0) {
                            profile.setUserName(data.getString("username"));
                            profile.seteMail(data.getString("email"));
                        } else if (i == 1) {
                            profile.setProfilePhoto(data.getString("profilePhoto"));
                            profile.setNameSurname(data.getString("nameSurname"));
                            profile.setPhoneNumber(data.getString("phoneNumber"));
                            profile.setAddress(data.getString("address"));
                        } else if (i == 2) {
                            profile.setRelativeNameSurname(data.getString("relativeNameSurname"));
                            profile.setRelativePhoneNumber(data.getString("relativePhoneNumber"));
                        } else if (i == 3) {
                            profile.setLicenseFrontFace(data.getString("licenseFront"));
                            profile.setLicenseBackFace(data.getString("licenseBack"));
                        }
                    }
                }
                profile.saveToSharedPreferences(EditProfile.this);
            }

            ReqUtil.updateProfileReq(EditProfile.this, new ReqUtil.GeneralCallback() {
                @Override
                public void onSuccess() {
                    Sneaker.with(EditProfile.this)
                            .setTitle("Başarılı!")
                            .setMessage("Profil başarıyla güncellendi.")
                            .sneakSuccess();
                    new Handler(Looper.getMainLooper()).postDelayed(() -> finish(), 2000);
                }

                @Override
                public void onError() {
                    Sneaker.with(EditProfile.this)
                            .setTitle("Hata!")
                            .setMessage("Profil güncellenirken bir hata oluştu.")
                            .sneakError();
                }
            });
        });
    }

    private void headerComponentsClickListeners() {
        backButtonImage.setOnClickListener(v -> {
            finish();
        });
    }

    private void initializeUser() {
        if (currentProfile != null) {
            Bundle step1Data = new Bundle();
            step1Data.putString("username", currentProfile.getUserName());
            step1Data.putString("email", currentProfile.geteMail());
            fragmentData.put(0, step1Data);

            Bundle step2Data = new Bundle();
            step2Data.putString("profilePhoto", currentProfile.getProfilePhoto());
            step2Data.putString("nameSurname", currentProfile.getNameSurname());
            step2Data.putString("phoneNumber", currentProfile.getPhoneNumber());
            step2Data.putString("address", currentProfile.getAddress());
            fragmentData.put(1, step2Data);

            Bundle step3Data = new Bundle();
            step3Data.putString("relativeNameSurname", currentProfile.getRelativeNameSurname());
            step3Data.putString("relativePhoneNumber", currentProfile.getRelativePhoneNumber());
            fragmentData.put(2, step3Data);

            Bundle step4Data = new Bundle();
            step4Data.putString("licenseFront", currentProfile.getLicenseFrontFace());
            step4Data.putString("licenseBack", currentProfile.getLicenseBackFace());
            fragmentData.put(3, step4Data);
        }
    }

    private boolean allDataValid() {
        for (int i = 0; i < fragmentData.size(); i++) {
            Bundle data = fragmentData.get(i);
            if (data != null) {
                for (String key : data.keySet()) {
                    if (data.getString(key) == null || Objects.requireNonNull(data.getString(key)).isEmpty()) {
                        Log.d("allDataValid", key + " is invalid");
                        return false;
                    }
                }
            } else {
                Log.d("allDataValid", "data for fragment " + i + " is null");
                return false;
            }
        }
        return true;
    }

    private boolean dataChanged() {
        UserModel profile = Utils.getFromSharedPreferences(this);
        if (profile != null) {
            for (int i = 0; i < fragmentData.size(); i++) {
                Bundle data = fragmentData.get(i);
                if (data != null) {
                    if (i == 0) {
                        if (!Objects.equals(profile.getUserName(), data.getString("username")) ||
                                !Objects.equals(profile.geteMail(), data.getString("email"))) {
                            return true;
                        }
                    } else if (i == 1) {
                        if (!Objects.equals(profile.getProfilePhoto(), data.getString("profilePhoto")) ||
                                !Objects.equals(profile.getNameSurname(), data.getString("nameSurname")) ||
                                !Objects.equals(profile.getPhoneNumber(), data.getString("phoneNumber")) ||
                                !Objects.equals(profile.getAddress(), data.getString("address"))) {
                            return true;
                        }
                    } else if (i == 2) {
                        if (!Objects.equals(profile.getRelativeNameSurname(), data.getString("relativeNameSurname")) ||
                                !Objects.equals(profile.getRelativePhoneNumber(), data.getString("relativePhoneNumber"))) {
                            return true;
                        }
                    } else if (i == 3) {
                        if (!Objects.equals(profile.getLicenseFrontFace(), data.getString("licenseFront")) ||
                                !Objects.equals(profile.getLicenseBackFace(), data.getString("licenseBack"))) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}