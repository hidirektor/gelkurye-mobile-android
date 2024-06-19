package me.t3sl4.kurye.Model.User;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.PhoneNumberUtils;

import com.google.gson.Gson;

import java.util.Locale;

import javax.annotation.Nullable;

public class UserModel implements Parcelable {
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
    private String selectedLanguage;
    private String firstBreakTime;
    private String secondBreakTime;
    private int userRating;

    private String merchantID;
    private String merchantName;
    private String merchantAddress;
    private String merchantPhoneNumber;

    private String trendyolSupplierID;
    private String trendyolAPIKey;
    private String trendyolAPISecretKey;
    private String getirYemekMerchantToken;
    private String yemekSepetiUsername;
    private String yemekSepetiPassword;

    // Constructor
    public UserModel(int id, String userID, String userName, String eMail, String userType, String nameSurname,
                     String phoneNumber, String address, String password, String profilePhoto,
                     String relativeNameSurname, String relativePhoneNumber, String lastPasswordChange,
                     String createdAt, String licenseFrontFace, String licenseBackFace, boolean nightMode,
                     String selectedLanguage, String firstBreakTime, String secondBreakTime, int userRating) {
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

    public UserModel(int id, String userID, String userName, String eMail, String userType, String nameSurname,
                     String phoneNumber, String address, String password, String profilePhoto,
                     String relativeNameSurname, String relativePhoneNumber, String lastPasswordChange,
                     String createdAt, boolean nightMode,
                     String selectedLanguage, String firstBreakTime, String secondBreakTime) {
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
        this.nightMode = nightMode;
        this.selectedLanguage = selectedLanguage;
        this.firstBreakTime = firstBreakTime;
        this.secondBreakTime = secondBreakTime;
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

    public String getFormattedPhoneNumber() {
        return PhoneNumberUtils.formatNumber("+" +phoneNumber, Locale.getDefault().getCountry());
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

    public String isSelectedLanguage() {
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

    public void setId(int id) {
        this.id = id;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setNameSurname(String nameSurname) {
        this.nameSurname = nameSurname;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public void setRelativeNameSurname(String relativeNameSurname) {
        this.relativeNameSurname = relativeNameSurname;
    }

    public void setRelativePhoneNumber(String relativePhoneNumber) {
        this.relativePhoneNumber = relativePhoneNumber;
    }

    public void setLastPasswordChange(String lastPasswordChange) {
        this.lastPasswordChange = lastPasswordChange;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setLicenseFrontFace(String licenseFrontFace) {
        this.licenseFrontFace = licenseFrontFace;
    }

    public void setLicenseBackFace(String licenseBackFace) {
        this.licenseBackFace = licenseBackFace;
    }

    public void setNightMode(boolean nightMode) {
        this.nightMode = nightMode;
    }

    public void setSelectedLanguage(String selectedLanguage) {
        this.selectedLanguage = selectedLanguage;
    }

    public void setFirstBreakTime(String firstBreakTime) {
        this.firstBreakTime = firstBreakTime;
    }

    public void setSecondBreakTime(String secondBreakTime) {
        this.secondBreakTime = secondBreakTime;
    }

    public void setUserRating(int userRating) {
        this.userRating = userRating;
    }

    public String getMerchantID() {
        return merchantID;
    }

    public void setMerchantID(String merchantID) {
        this.merchantID = merchantID;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantAddress() {
        return merchantAddress;
    }

    public void setMerchantAddress(String merchantAddress) {
        this.merchantAddress = merchantAddress;
    }

    public String getMerchantPhoneNumber() {
        return merchantPhoneNumber;
    }

    public void setMerchantPhoneNumber(String merchantPhoneNumber) {
        this.merchantPhoneNumber = merchantPhoneNumber;
    }

    public String getTrendyolSupplierID() {
        return trendyolSupplierID;
    }

    public void setTrendyolSupplierID(String trendyolSupplierID) {
        this.trendyolSupplierID = trendyolSupplierID;
    }

    public String getTrendyolAPIKey() {
        return trendyolAPIKey;
    }

    public void setTrendyolAPIKey(String trendyolAPIKey) {
        this.trendyolAPIKey = trendyolAPIKey;
    }

    public String getTrendyolAPISecretKey() {
        return trendyolAPISecretKey;
    }

    public void setTrendyolAPISecretKey(String trendyolAPISecretKey) {
        this.trendyolAPISecretKey = trendyolAPISecretKey;
    }

    public String getGetirYemekMerchantToken() {
        return getirYemekMerchantToken;
    }

    public void setGetirYemekMerchantToken(String getirYemekMerchantToken) {
        this.getirYemekMerchantToken = getirYemekMerchantToken;
    }

    public String getYemekSepetiUsername() {
        return yemekSepetiUsername;
    }

    public void setYemekSepetiUsername(String yemekSepetiUsername) {
        this.yemekSepetiUsername = yemekSepetiUsername;
    }

    public String getYemekSepetiPassword() {
        return yemekSepetiPassword;
    }

    public void setYemekSepetiPassword(String yemekSepetiPassword) {
        this.yemekSepetiPassword = yemekSepetiPassword;
    }

    // Gson to JSON
    public String toJson() {
        return new Gson().toJson(this);
    }

    protected UserModel(Parcel in) {
        id = in.readInt();
        userID = in.readString();
        userName = in.readString();
        eMail = in.readString();
        userType = in.readString();
        nameSurname = in.readString();
        phoneNumber = in.readString();
        address = in.readString();
        password = in.readString();
        profilePhoto = in.readString();
        relativeNameSurname = in.readString();
        relativePhoneNumber = in.readString();
        lastPasswordChange = in.readString();
        createdAt = in.readString();
        licenseFrontFace = in.readString();
        licenseBackFace = in.readString();
        nightMode = in.readByte() != 0;
        selectedLanguage = in.readString();
        firstBreakTime = in.readString();
        secondBreakTime = in.readString();
        userRating = in.readInt();
        merchantID = in.readString();
        merchantName = in.readString();
        merchantAddress = in.readString();
        merchantPhoneNumber = in.readString();
        trendyolSupplierID = in.readString();
        trendyolAPIKey = in.readString();
        trendyolAPISecretKey = in.readString();
        getirYemekMerchantToken = in.readString();
        yemekSepetiUsername = in.readString();
        yemekSepetiPassword = in.readString();
    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel in) {
            return new UserModel(in);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(userID);
        dest.writeString(userName);
        dest.writeString(eMail);
        dest.writeString(userType);
        dest.writeString(nameSurname);
        dest.writeString(phoneNumber);
        dest.writeString(address);
        dest.writeString(password);
        dest.writeString(profilePhoto);
        dest.writeString(relativeNameSurname);
        dest.writeString(relativePhoneNumber);
        dest.writeString(lastPasswordChange);
        dest.writeString(createdAt);
        dest.writeString(licenseFrontFace);
        dest.writeString(licenseBackFace);
        dest.writeByte((byte) (nightMode ? 1 : 0));
        dest.writeString(selectedLanguage);
        dest.writeString(firstBreakTime);
        dest.writeString(secondBreakTime);
        dest.writeInt(userRating);
        dest.writeString(merchantID);
        dest.writeString(merchantName);
        dest.writeString(merchantAddress);
        dest.writeString(merchantPhoneNumber);
        dest.writeString(trendyolSupplierID);
        dest.writeString(trendyolAPIKey);
        dest.writeString(trendyolAPISecretKey);
        dest.writeString(getirYemekMerchantToken);
        dest.writeString(yemekSepetiUsername);
        dest.writeString(yemekSepetiPassword);
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