package com.svmexample.rx_retrofit;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefUtils {

    public PrefUtils() {
    }

    public static SharedPreferences getSharedPreference(Context context){
        return context.getSharedPreferences("APP-PREF",Context.MODE_PRIVATE);
    }

    public static String getApiKey(Context context){
        return getSharedPreference(context).getString("Api",null);
    }

    public static void storeApiKey(Context context,String apikey){
        SharedPreferences.Editor editor = getSharedPreference(context).edit() ;
    editor.putString("Api",apikey);
    editor.commit();

    }
}
