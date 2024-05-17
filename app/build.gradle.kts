plugins {
    id("com.android.application")
}

android {
    namespace = "me.t3sl4.gelkurye"
    compileSdk = 34

    defaultConfig {
        applicationId = "me.t3sl4.gelkurye"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation("io.github.chaosleung:pinview:1.4.4")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.16.0")
    implementation("com.google.maps:google-maps-services:0.15.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.google.firebase:protolite-well-known-types:18.0.0")
    implementation("com.iarcuschin:simpleratingbar:0.1.5")
    implementation("com.github.Z-P-J:ZCheckBox:1.0.0")
    implementation("com.github.sigma1326:NiceSwitch:1.0")
    implementation("com.kofigyan.stateprogressbar:stateprogressbar:1.0.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}