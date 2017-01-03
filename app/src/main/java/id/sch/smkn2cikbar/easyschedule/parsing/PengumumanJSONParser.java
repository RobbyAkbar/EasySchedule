package id.sch.smkn2cikbar.easyschedule.parsing;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import id.sch.smkn2cikbar.easyschedule.models.PengumumanModel;

/**
 * Created by Robby Akbar on 28/12/16.
 */

public class PengumumanJSONParser {
    static List<PengumumanModel> pengumumanList;

    public  static List<PengumumanModel> parseData(String context){
        JSONArray p_arry = null;
        PengumumanModel p = null;
        try {
            p_arry = new JSONArray(context);
            pengumumanList = new ArrayList<>();
            for (int i=0; i<p_arry.length(); i++){
                JSONObject object = p_arry.getJSONObject(i);
                p = new PengumumanModel();
                p.setId_(object.getString("id_"));
                p.setJudul(object.getString("judul"));
                p.setDetail(object.getString("detail"));
                p.setTgl(object.getString("tgl"));
                p.setImage(object.getString("img_p"));
                pengumumanList.add(p);
            }
            return pengumumanList;
        } catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }
}
