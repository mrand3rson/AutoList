package com.example.autolist.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.autolist.R;
import com.example.autolist.pattern.models.Automobile;
import com.example.autolist.pattern.models.CarModel;
import com.example.autolist.pattern.models.DaoMaster;
import com.example.autolist.pattern.models.DaoSession;
import com.example.autolist.pattern.models.Manufacturer;
import com.google.gson.Gson;

import org.greenrobot.greendao.database.Database;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Andrei on 06.08.2018.
 */

public class InitEngine {
    private final static String LOG_TAG = "Log_init";
    private final static String KEY_FIRST_LAUNCH = "first_launch";
    private final static String DB_NAME = "autos-db";


    public static void init(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(
                context.getPackageName(),
                Context.MODE_PRIVATE
        );
        if (!prefs.getBoolean(KEY_FIRST_LAUNCH, false)) {
            initDatabase(context);

            prefs.edit()
                    .putBoolean(KEY_FIRST_LAUNCH, true)
                    .apply();
            Log.d(LOG_TAG, "completed");
        }
    }

    private static void initDatabase(Context context) {
        Gson gson = new Gson();
        try {
            String jsonStringManufacturers = getJsonString(context, R.raw.init_manufacturer);
            String jsonStringCarmodels = getJsonString(context, R.raw.init_carmodel);
            String jsonStringAutomobiles = getJsonString(context, R.raw.init_automobile);
            Manufacturer[] manufacturers = gson.fromJson(
                    jsonStringManufacturers,
                    Manufacturer[].class);
            CarModel[] carModels = gson.fromJson(
                    jsonStringCarmodels,
                    CarModel[].class);
            Automobile[] automobiles = gson.fromJson(
                    jsonStringAutomobiles,
                    Automobile[].class
            );

            DaoSession daoSession = initDaoSession(context);
            daoSession.getManufacturerDao().insertInTx(manufacturers);
            daoSession.getCarModelDao().insertInTx(carModels);
            daoSession.getAutomobileDao().insertInTx(automobiles);
        } catch (IOException e) {
            Log.d(LOG_TAG, "error");
            e.printStackTrace();
        }
    }

    public static DaoSession initDaoSession(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, DB_NAME);
        Database db = helper.getWritableDb();
        return new DaoMaster(db).newSession();
    }

    private static String getJsonString(Context context, int raw) throws IOException {
        InputStream is = context.getResources().openRawResource(raw);
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();

        return new String(buffer, "UTF-8");
    }
}
