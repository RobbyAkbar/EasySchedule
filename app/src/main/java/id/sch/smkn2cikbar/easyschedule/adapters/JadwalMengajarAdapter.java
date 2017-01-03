package id.sch.smkn2cikbar.easyschedule.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import id.sch.smkn2cikbar.easyschedule.R;
import id.sch.smkn2cikbar.easyschedule.config.Config;
import id.sch.smkn2cikbar.easyschedule.models.JadwalMengajarModel;

/**
 * Created by robby on 03/01/17.
 */

public class JadwalMengajarAdapter extends BaseAdapter {

    private Context context;
    private List<JadwalMengajarModel> jadwalList;
    private LayoutInflater inflater = null;
    private LruCache<Integer, Bitmap> imageCache;
    private RequestQueue requestQueue;

    public JadwalMengajarAdapter(Context context, List<JadwalMengajarModel> list){
        this.context = context;
        this.jadwalList = list;
        inflater = LayoutInflater.from(context);
        final int maxMemory = (int)(Runtime.getRuntime().maxMemory()/1024);
        final int cacheSize = maxMemory/8;
        imageCache = new LruCache<>(cacheSize);
        requestQueue = Volley.newRequestQueue(context);
    }

    public class ViewHolder{
        TextView _namaGuru, _kelas, _sub_kelas, _mapel, _hari, _jam_mulai, _jam_selesai;
        ImageView _jadwal_Image;
    }

    public JadwalMengajarAdapter sortData(){
        Collections.sort(jadwalList, new Comparator<JadwalMengajarModel>() {
            @Override
            public int compare(JadwalMengajarModel jadwalMengajarModel, JadwalMengajarModel t1) {
                return jadwalMengajarModel.getJam_mulai().compareTo(t1.getJam_mulai());
            }
        });
        return this;
    }

    @Override
    public int getCount() {
        return jadwalList.size();
    }

    @Override
    public Object getItem(int i) {
        return jadwalList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final JadwalMengajarModel jadwal = jadwalList.get(i);
        final ViewHolder holder;
        if (view==null){
            view = inflater.inflate(R.layout.jadwal_mengajar_list, null);
            holder = new ViewHolder();
            holder._namaGuru = (TextView)view.findViewById(R.id.nama_guru);
            holder._kelas = (TextView)view.findViewById(R.id.kelas);
            holder._sub_kelas = (TextView)view.findViewById(R.id.sub_kelas);
            holder._mapel = (TextView)view.findViewById(R.id.mapel);
            holder._hari = (TextView)view.findViewById(R.id.hari);
            holder._jam_mulai = (TextView)view.findViewById(R.id.jam_mulai);
            holder._jam_selesai= (TextView)view.findViewById(R.id.jam_selesai);
            view.setTag(holder);
        } else {
            holder = (ViewHolder)view.getTag();
        }

        holder._namaGuru.setText(""+jadwal.getNama_guru().toString());
        holder._kelas.setText(""+jadwal.getKelas().toString());
        holder._sub_kelas.setText(""+jadwal.getSub_kelas().toString());
        holder._mapel.setText(""+jadwal.getMapel().toString());
        holder._hari.setText(""+jadwal.getHari().toString());
        holder._jam_mulai.setText("Jam Mulai "+jadwal.getJam_mulai().toString());
        holder._jam_selesai.setText("Jam Selesai "+jadwal.getJam_selesai().toString());
        Bitmap bitmap = imageCache.get(Integer.parseInt(jadwal.getId_jadwal()));
        holder._jadwal_Image = (ImageView)view.findViewById(R.id.img);

        if (bitmap != null){
            holder._jadwal_Image.setImageBitmap(bitmap);
        } else {
            String imageURL = Config.IMAGE+jadwal.getImage();
            ImageRequest request = new ImageRequest(imageURL,
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap response) {
                            holder._jadwal_Image.setImageBitmap(response);
                            imageCache.put(Integer.parseInt(jadwal.getId_jadwal()), response);
                        }
                    }, 90, 90, Bitmap.Config.ARGB_8888,
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Error", error.getMessage().toString());
                        }
                    });
            requestQueue.add(request);
        }
        return view;
    }
}
