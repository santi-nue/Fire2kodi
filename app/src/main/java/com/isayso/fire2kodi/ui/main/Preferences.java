package com.isayso.fire2kodi.ui.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import com.isayso.fire2kodi.GlobalApplication;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class Preferences {

   // @SuppressLint("StaticFieldLeak")
    public static Context context = GlobalApplication.getAppContext();


    public static String readE(String valueKey, String valueDefault) throws GeneralSecurityException, IOException {

        String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);

        SharedPreferences sharedPreferences = EncryptedSharedPreferences.create(
                "secret_shared_prefs",
                masterKeyAlias,
                context,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        );
        return sharedPreferences.getString(valueKey, valueDefault);

    }

    public static void saveE(String valueKey, String value) throws GeneralSecurityException, IOException {

        //https://stackoverflow.com/questions/62498977/how-to-create-masterkey-after-masterkeys-deprecated-in-android

        String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);

        SharedPreferences sharedPreferences = EncryptedSharedPreferences.create(
                "secret_shared_prefs",
                masterKeyAlias,
                context,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);

        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(valueKey, value);
        edit.apply();
    }


    public static String read(String valueKey, String valueDefault) {

        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        return prefs.getString(valueKey, valueDefault);
    }

    public static boolean readBool(String valueKey, boolean valueDefault) {

        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        return prefs.getBoolean(valueKey, valueDefault);
    }

    //save preferences string
    public static void save(String valueKey, String value) {
        SharedPreferences prefs = androidx.preference.PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString(valueKey, value);
        edit.apply();
    }

    //save preferences bool
    public static void saveBool(String valueKey, boolean value) {
        SharedPreferences prefs = androidx.preference.PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = prefs.edit();
        //edit.putString(valueKey, value);
        edit.putBoolean(valueKey, value);
        edit.apply();
    }

}
