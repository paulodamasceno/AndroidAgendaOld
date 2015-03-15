package com.example.paulo.agenda;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.example.paulo.agenda.model.User;

/**
 * Created by Paulo on 12/03/2015.
 */
public class Helper {

    public static void saveUserPreference(Context context,String username, String password){
        SharedPreferences sharedPreferences = context.getSharedPreferences("login",Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("username",username);
        editor.putString("password",password);

        editor.commit();
    }


    public static User getUserPreference(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("login",Context.MODE_PRIVATE);

        String username = sharedPreferences.getString("username", null);
        String password = sharedPreferences.getString("password", null);

        if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
            return null;
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        return user;
    }


}
