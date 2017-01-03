package id.sch.smkn2cikbar.easyschedule.parsing;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import id.sch.smkn2cikbar.easyschedule.models.JadwalMengajarModel;

/**
 * Created by robby on 03/01/17.
 */

public class JadwalMengajarJSONParser {

    static List<JadwalMengajarModel> jadwalList;

    public static List<JadwalMengajarModel> parseData(String content){

        JSONArray jadwal_arry = null;
        JadwalMengajarModel jadwal = null;

        try {
            jadwal_arry = new JSONArray(content);
            jadwalList = new ArrayList<>();

            for (int i=0;i<jadwal_arry.length();i++){
                JSONObject object = jadwal_arry.getJSONObject(i);
                jadwal = new JadwalMengajarModel();
                jadwal.setId_jadwal(object.getString("id_jadwal"));
                jadwal.setNama_guru(object.getString("nama_guru"));
                jadwal.setKelas(object.getString("kelas"));
                jadwal.setSub_kelas(object.getString("sub_kelas"));
                jadwal.setMapel(object.getString("mapel"));
                jadwal.setHari(object.getString("hari"));
                jadwal.setJam_mulai(object.getString("jam_mulai"));
                jadwal.setJam_selesai(object.getString("jam_selesai"));
                jadwal.setImage(object.getString("img_jadwal"));
                jadwalList.add(jadwal);
            }
            return jadwalList;
        } catch (JSONException e){
            e.printStackTrace();
            return null;
        }

    }

}
