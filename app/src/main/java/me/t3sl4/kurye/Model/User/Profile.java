package me.t3sl4.kurye.Model.User;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

public class Profile implements Parcelable {
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

    protected Profile(Parcel in) {
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
        selectedLanguage = in.readByte() != 0;
        firstBreakTime = in.readString();
        secondBreakTime = in.readString();
        userRating = in.readInt();
    }

    public static final Creator<Profile> CREATOR = new Creator<Profile>() {
        @Override
        public Profile createFromParcel(Parcel in) {
            return new Profile(in);
        }

        @Override
        public Profile[] newArray(int size) {
            return new Profile[size];
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
        dest.writeByte((byte) (selectedLanguage ? 1 : 0));
        dest.writeString(firstBreakTime);
        dest.writeString(secondBreakTime);
        dest.writeInt(userRating);
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