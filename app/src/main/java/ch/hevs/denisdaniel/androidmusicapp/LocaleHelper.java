package ch.hevs.denisdaniel.androidmusicapp;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;

import java.util.Locale;

/**
 * Created by dnlro on 11/11/2017.
 */

public class LocaleHelper {

    private static final String SELECTED_LANGUAGE = "Locale.Helper.Selected.Language";


    public static Context onAttach(Context context){
        String lang = getPersistedData(context, Locale.getDefault().getLanguage());

        return setLocale(context, lang);

    }

    public static Context onAttach(Context context, String defaultLanguage){
        String lang = getPersistedData(context, defaultLanguage);

        return setLocale(context, lang);

    }
    public static Context setLocale(Context context, String lang){

        persist(context, lang);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            return updateRessources(context,lang);

        return updateRessourcesLegacy(context, lang);

    }

    @TargetApi(Build.VERSION_CODES.N)
    private static  Context updateRessources(Context context, String lang){
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);

        Configuration config = context.getResources().getConfiguration();
        config.setLocale(locale);
        config.setLayoutDirection(locale);

        return context.createConfigurationContext(config);

    }

    @SuppressWarnings("depreciation")
    private static  Context updateRessourcesLegacy(Context context, String lang){
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);

      Resources ressources = context.getResources();

      Configuration config = ressources.getConfiguration();
      config.locale = locale;
      if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
          config.setLayoutDirection(locale);

      ressources.updateConfiguration(config, ressources.getDisplayMetrics());

      return context;
    }



    private static void persist(Context context, String lang)
    {
        // get the language preference
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString(SELECTED_LANGUAGE,lang);
        editor.apply();

    }

    private static String getPersistedData(Context context, String language){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(SELECTED_LANGUAGE, language);
    }


/*
    public void loadLocale() {
        String langPref = "Language";
        SharedPreferences prefs = getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        String language = prefs.getString(langPref, "");
        changeLang(language);
    }

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
*/
}
