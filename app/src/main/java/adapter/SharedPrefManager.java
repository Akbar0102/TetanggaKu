package adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.android.tetanggaku.LoginActivity;

import entity.sharedWarga;

/**
 * Created by Akbar H on 27/11/2017.
 */

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "tetanggaku";
    private static final String KEY_NAMA = "keynama";
    private static final String KEY_STATUS = "keystatus";
    private static final String KEY_JENIS = "keyjenis";
    private static final String KEY_ALAMAT = "keyalamat";
    private static final String KEY_ID = "keyid";


    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public void userLogin(sharedWarga warga) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_NAMA, warga.getNama());
        editor.putString(KEY_JENIS, warga.getJenis());
        editor.putString(KEY_STATUS, warga.getStatus());
        editor.putString(KEY_ALAMAT, warga.getAlamat());
        editor.putInt(KEY_ID, warga.getId());
        editor.apply();
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_NAMA, null) != null;
    }

    public sharedWarga getwarga() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new sharedWarga(
                sharedPreferences.getString(KEY_NAMA, null),
                sharedPreferences.getString(KEY_JENIS, null),
                sharedPreferences.getString(KEY_ALAMAT, null),
                sharedPreferences.getString(KEY_STATUS, null),
                sharedPreferences.getInt(KEY_ID, 0)
        );
    }

    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, LoginActivity.class));
    }
}
