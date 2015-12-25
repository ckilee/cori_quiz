package frolic.br.coriquiz.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Random;

/**
 * Created by ckilee on 26/11/15.
 */
public class Utils {
    public static int randInt(int min, int max){
        Random rand = new Random();
        int randomNum = rand.nextInt((max-min)+1)+min;
        return randomNum;
    }

    public static void addToSharedPreferences(Context context, int newDbVersion){
        SharedPreferences sharedPreferences = context.getSharedPreferences(ExtraNames.MY_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(ExtraNames.DB_VERSION_PREFS,newDbVersion);
        editor.commit();
    }


}
