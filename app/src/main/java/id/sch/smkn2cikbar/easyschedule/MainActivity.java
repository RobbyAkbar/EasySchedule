package id.sch.smkn2cikbar.easyschedule;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import id.sch.smkn2cikbar.easyschedule.config.Config;

/**
 * Created by Robby Akbar on 28/12/16.
 */

public class MainActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = MainActivity.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                Boolean state = sharedPreferences.getBoolean(Config.LOGGEDIN_SHARED_PREF, false);
                if (state){
                    cekData();
                } else {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);

    }

    private void cekData(){
        SharedPreferences sharedPreferences = MainActivity.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String nis = sharedPreferences.getString(Config.USERNAME_SHARED_PREF_SISWA, "");
        String nip = sharedPreferences.getString(Config.USERNAME_SHARED_PREF_GURU, "");
        if (!nis.equalsIgnoreCase("")){
            Intent intent = new Intent(MainActivity.this, MuridActivity.class);
            startActivity(intent);
            finish();
        } else if (!nip.equalsIgnoreCase("")){
            Intent intent = new Intent(MainActivity.this, GuruActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
