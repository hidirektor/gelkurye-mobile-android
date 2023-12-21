package me.t3sl4.gelkurye.Util.Util;

import okhttp3.OkHttpClient;

public class Singleton {

    private static OkHttpClient instance;

    private Singleton() {
    }

    public static OkHttpClient getInstance() {
        if (instance == null) {
            instance = new OkHttpClient();
        }
        return instance;
    }
}
