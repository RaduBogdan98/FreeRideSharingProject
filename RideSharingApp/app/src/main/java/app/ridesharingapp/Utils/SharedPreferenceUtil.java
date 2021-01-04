package app.ridesharingapp.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferenceUtil {
    public SharedPreferenceUtil(){}

    public static boolean saveEmail(String email, Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor preferencesEditor = preferences.edit();
        preferencesEditor.putString("email",email);
        preferencesEditor.apply();
        return true;
    }
    public static boolean savePassword(String password,Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor preferencesEditor = preferences.edit();
        preferencesEditor.putString("password",password);
        preferencesEditor.apply();
        return true;
    }

    public static String getEmail(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("email",null);
    }
    public static String getPassword(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("password",null);
    }
}
