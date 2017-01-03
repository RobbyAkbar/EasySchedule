package id.sch.smkn2cikbar.easyschedule.helper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import id.sch.smkn2cikbar.easyschedule.fragments.MuridCatatanFragment;
import id.sch.smkn2cikbar.easyschedule.models.DaftarCatatanModel;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Robby Akbar on 30/12/16.
 */

public class RealmHelper {

    private Realm realm;
    private RealmResults<DaftarCatatan>realmResults;
    public Context context;

    public RealmHelper (Context context){
        realm = Realm.getInstance(context);
        this.context = context;
    }

    public void addDaftarCatatan (String pelajaran, String tugas, String deadline){
        DaftarCatatan daftarCatatan = new DaftarCatatan();
        daftarCatatan.setId((int)(System.currentTimeMillis()));
        daftarCatatan.setPelajaran(pelajaran);
        daftarCatatan.setTugas(tugas);
        daftarCatatan.setDeadline(deadline);

        realm.beginTransaction();
        realm.copyToRealm(daftarCatatan);
        realm.commitTransaction();

        showLog("Added Task : " + pelajaran);
        showToast("Tugas " + pelajaran + " telah ditambahkan");
    }

    public void updateCatatan(int id, String pelajaran, String tugas, String deadline){
        realm.beginTransaction();
        DaftarCatatan daftarCatatan = realm.where(DaftarCatatan.class).equalTo("id", id).findFirst();
        daftarCatatan.setPelajaran(pelajaran);
        daftarCatatan.setTugas(tugas);
        daftarCatatan.setDeadline(deadline);
        realm.commitTransaction();
        showLog("Updated Task : " + pelajaran);
        showToast("Tugas " + pelajaran + " telah diperbarui");
    }

    public void deleteCatatan(int id){
        RealmResults<DaftarCatatan> daftarCataten = realm.where(DaftarCatatan.class).equalTo("id", id).findAll();
        realm.beginTransaction();
        daftarCataten.remove(0);
        daftarCataten.removeLast();
        daftarCataten.clear();
        realm.commitTransaction();
        showLog("Task Deleted");
        showToast("Tugas telah dihapus");
    }

    private void showLog(String s){
        Log.d("RealmHelper", s);
    }

    private void showToast(String s){
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

}
