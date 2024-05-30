plugins {
    id("com.android.application")
}

android {
    namespace = "me.t3sl4.kurye"
    compileSdk = 34

    sourceSets {
        getByName("main") {
            res.srcDirs(
                "src/main/res/layouts/merchant",
                "src/main/res/layouts/dashboard",
                "src/main/res/layouts/order",
                "src/main/res/layouts/earning",
                "src/main/res/layouts/profile",
                "src/main/res/layouts/hamburger",
                "src/main/res/layouts/general",
                "src/main/res/layouts/onboard",
                "src/main/res/layouts/auth",
                "src/main/res/layouts",
                "src/main/res",
            )
        }
    }

    defaultConfig {
        applicationId = "me.t3sl4.kurye"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        buildConfigField("String", "BASE_URL", "\"http://85.95.231.92:4000/api/v1/\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            isShrinkResources = false
            buildFeatures.buildConfig = true

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
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.security:security-crypto:1.1.0-alpha06")

    implementation("com.google.android.material:material:1.12.0")
    implementation("com.google.firebase:protolite-well-known-types:18.0.0")
    implementation("com.google.android.gms:play-services-base:18.5.0")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("com.google.android.gms:play-services-location:21.3.0")
    implementation("com.google.maps.android:android-maps-utils:2.3.0")
    implementation("com.google.code.gson:gson:2.10.1")

    implementation("com.android.volley:volley:1.2.1")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.16.0")

    implementation("io.github.chaosleung:pinview:1.4.4")
    implementation("com.iarcuschin:simpleratingbar:0.1.5")
    implementation("com.github.Z-P-J:ZCheckBox:1.0.0")
    implementation("com.github.sigma1326:NiceSwitch:1.0")
    implementation("com.kofigyan.stateprogressbar:stateprogressbar:1.0.0")
    implementation("com.hbb20:ccp:2.7.3")
    implementation("com.mikhaellopez:circularimageview:3.2.0")

    implementation("org.cactoos:cactoos:0.55.0")

    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation("com.irozon.sneaker:sneaker:2.0.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}