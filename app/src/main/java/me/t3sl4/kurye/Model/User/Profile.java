package me.t3sl4.kurye.Model.User;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;

public class Profile {
    private int id;
    private String userID;
    private String userName;
    private String eMail;
    private String userType;
    private String nameSurname;
    private String phoneNumber;
    private String address;
    private String password;
    private String profilePhoto;
    private String relativeNameSurname;
    private String relativePhoneNumber;
    private String lastPasswordChange;
    private String createdAt;
    private String licenseFrontFace;
    private String licenseBackFace;
    private boolean nightMode;
    private boolean selectedLanguage;
    private String firstBreakTime;
    private String secondBreakTime;
    private int userRating;

    // Constructor
    public Profile(int id, String userID, String userName, String eMail, String userType, String nameSurname,
                   String phoneNumber, String address, String password, String profilePhoto,
                   String relativeNameSurname, String relativePhoneNumber, String lastPasswordChange,
                   String createdAt, String licenseFrontFace, String licenseBackFace, boolean nightMode,
                   boolean selectedLanguage, String firstBreakTime, String secondBreakTime, int userRating) {
        this.id = id;
        this.userID = userID;
        this.userName = userName;
        this.eMail = eMail;
        this.userType = userType;
        this.nameSurname = nameSurname;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.password = password;
        this.profilePhoto = profilePhoto;
        this.relativeNameSurname = relativeNameSurname;
        this.relativePhoneNumber = relativePhoneNumber;
        this.lastPasswordChange = lastPasswordChange;
        this.createdAt = createdAt;
        this.licenseFrontFace = licenseFrontFace;
        this.licenseBackFace = licenseBackFace;
        this.nightMode = nightMode;
        this.selectedLanguage = selectedLanguage;
        this.firstBreakTime = firstBreakTime;
        this.secondBreakTime = secondBreakTime;
        this.userRating = userRating;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public String geteMail() {
        return eMail;
    }

    public String getUserType() {
        return userType;
    }

    public String getNameSurname() {
        return nameSurname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getPassword() {
        return password;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public String getRelativeNameSurname() {
        return relativeNameSurname;
    }

    public String getRelativePhoneNumber() {
        return relativePhoneNumber;
    }

    public String getLastPasswordChange() {
        return lastPasswordChange;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getLicenseFrontFace() {
        return licenseFrontFace;
    }

    public String getLicenseBackFace() {
        return licenseBackFace;
    }

    public boolean isNightMode() {
        return nightMode;
    }

    public boolean isSelectedLanguage() {
        return selectedLanguage;
    }

    public String getFirstBreakTime() {
        return firstBreakTime;
    }

    public String getSecondBreakTime() {
        return secondBreakTime;
    }

    public int getUserRating() {
        return userRating;
    }

    // Gson to JSON
    public String toJson() {
        return new Gson().toJson(this);
    }

    // SharedPreferences
    private static final String PREF_NAME = "MyProfilePref";
    private static final String KEY_PROFILE_GSON = "profileGSON";

    public void saveToSharedPreferences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_PROFILE_GSON, toJson());
        editor.apply();
    }
}
