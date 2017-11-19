package ch.hevs.denisdaniel.androidmusicapp;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import java.util.Locale;

/**
 * Created by Denis Woeffray on 30.10.2017.
 */

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    SharedPreferences sharedPreferences;
    public static String PREFS_LANGUAGE = "Language";


    @Override
    public void onAttach(Context context) {

        super.onAttach(LocaleHelper.onAttach(context, "en"));

    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        // Recover preferences
        addPreferencesFromResource(R.xml.preferences);
    }

     @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);

    }

    // Sources pour Language: https://stackoverflow.com/questions/31183732/changing-language-in-run-time-with-preferences-android
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {


        if (key.equals("Language")) {
            // Get default language
            String lang = sharedPreferences.getString(SettingsFragment.PREFS_LANGUAGE, "en");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                //setLocale(getContext(), new Locale(lang));
                setLocale(getContext(), lang);

            } else {
               //setLocale(getActivity().getBaseContext(), new Locale(lang));
                setLocale(getActivity(), lang);
            }
        }

    }


    public void setLocale(Context context, String lang) {


        // set the new locale language as default
        if(!lang.isEmpty() && context!=null) {
            Locale locale = new Locale(lang);
            locale.setDefault(locale);
            Configuration conf = new Configuration();
            conf.locale = locale;
            /*
            Locale.setDefault(locale);
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();

            Configuration conf = res.getConfiguration();
            conf.locale = locale;
            res.updateConfiguration(conf, dm);*/
            context.getResources().updateConfiguration(conf, getResources().getDisplayMetrics());

            // recover intent from MainActivity and restart activity
            Intent refresh = getActivity().getIntent();
            startActivity(refresh);
        }


    }



/*
    public void changeLang(String lang) {
        if (lang.equalsIgnoreCase(""))
            return;
        myLocale = new Locale(lang);
        saveLocale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());

    }

    public void saveLocale(String lang) {
        String langPref = "Language";
        SharedPreferences prefs = getSharedPreferences("CommonPrefs",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(langPref, lang);
        editor.commit();
    }

    public void LoadLanguage(){
        SharedPreferences shp = getSharedPreferences(
                "com.example.myapp.PREFERENCES", Context.MODE_PRIVATE);
        String language = shp.getString("USER_LANGUAGE","");
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }

    */


}
