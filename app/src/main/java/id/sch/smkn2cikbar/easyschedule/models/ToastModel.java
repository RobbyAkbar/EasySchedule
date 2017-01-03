package id.sch.smkn2cikbar.easyschedule.models;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Robby Akbar on 27/12/16.
 */

public class ToastModel {
    public static void ToashBerhasilSignUP(Context context){
        Toast.makeText(context.getApplicationContext(),"Akun sudah dibuat, silahkan login...",Toast.LENGTH_SHORT).show();
    }
    public static void ToashGagalSignUP(Context context){
        Toast.makeText(context.getApplicationContext(),"Gagal membuat akun, silahkan coba lagi...",Toast.LENGTH_SHORT).show();
    }
    public static void ToashGagalLoginR(Context context){
        Toast.makeText(context.getApplicationContext(),"Email Atau Password Salah",Toast.LENGTH_SHORT).show();
    }

}
