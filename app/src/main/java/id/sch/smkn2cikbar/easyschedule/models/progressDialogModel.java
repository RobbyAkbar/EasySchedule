package id.sch.smkn2cikbar.easyschedule.models;

import android.app.ProgressDialog;
import android.content.Context;

import id.sch.smkn2cikbar.easyschedule.R;

/**
 * Created by Robby Akbar on 14/12/16.
 */

public class progressDialogModel {

    static ProgressDialog progressDialog;

    public static void pdSignUP(Context context){
        progressDialog=new ProgressDialog(context, R.style.AppCompatAlertDialogStyle);
        progressDialog.setMessage("Mengolah proses signup....");
        progressDialog.setTitle("Silahkan Tunggu");
        progressDialog.show();
    }

    public static void pdLogin(Context context){
        progressDialog=new ProgressDialog(context, R.style.AppCompatAlertDialogStyle);
        progressDialog.setMessage("Mengolah proses login....");
        progressDialog.setTitle("Silahkan Tunggu");
        progressDialog.show();
    }

    public static void pdMenyiapkanDataJadwal(Context context){
        progressDialog=new ProgressDialog(context, R.style.AppCompatAlertDialogStyle);
        progressDialog.setMessage("Menyiapkan data jadwal...");
        progressDialog.setTitle("Silahkan Tunggu");
        progressDialog.show();
    }

    public static void pdPengumuman(Context context){
        progressDialog=new ProgressDialog(context, R.style.AppCompatAlertDialogStyle);
        progressDialog.setMessage("Menyiapkan data pengumuman...");
        progressDialog.setTitle("Silahkan Tunggu");
        progressDialog.show();
    }

    public static void pdMenyiapkanDataLogin(Context context){
        progressDialog=new ProgressDialog(context, R.style.AppCompatAlertDialogStyle);
        progressDialog.setMessage("Menyiapkan Data....");
        progressDialog.setTitle("Silahkan Tunggu");
        progressDialog.show();
    }

    public static void hideProgressDialog(){
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

}
