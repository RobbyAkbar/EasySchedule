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

import java.util.List;

import id.sch.smkn2cikbar.easyschedule.R;
import id.sch.smkn2cikbar.easyschedule.config.Config;
import id.sch.smkn2cikbar.easyschedule.models.PengumumanModel;

/**
 * Created by Robby Akbar on 28/12/16.
 */

public class PengumumanAdapter extends BaseAdapter {

    private Context context;
    private List<PengumumanModel> pengumumanList;
    private LayoutInflater inflater = null;
    private LruCache<Integer,Bitmap> imageCache;
    private RequestQueue queue;

    public PengumumanAdapter(Context context, List<PengumumanModel> list){
        this.context = context;
        this.pengumumanList = list;
        inflater = LayoutInflater.from(context);
        final int maxMemory = (int)(Runtime.getRuntime().maxMemory()/1024);
        final int cacheSize = maxMemory/8;
        imageCache = new LruCache<>(cacheSize);
        queue = Volley.newRequestQueue(context);
    }

    public class ViewHolder{
        TextView _judul, _detail, _tgl;
        ImageView _P_Image;
    }

    @Override
    public int getCount() {
        return pengumumanList.size();
    }

    @Override
    public Object getItem(int i) {
        return pengumumanList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final PengumumanModel pengumuman = pengumumanList.get(i);
        final PengumumanAdapter.ViewHolder holder;
        if (view==null){
            view = inflater.inflate(R.layout.pengumuman_list, null);
            holder = new PengumumanAdapter.ViewHolder();
            holder._judul = (TextView)view.findViewById(R.id.tv1);
            holder._detail = (TextView)view.findViewById(R.id.tv2);
            holder._tgl = (TextView)view.findViewById(R.id.tv3);
            view.setTag(holder);
        } else {
            holder = (PengumumanAdapter.ViewHolder)view.getTag();
        }

        holder._judul.setText(""+pengumuman.getJudul().toString());
        holder._detail.setText(""+pengumuman.getDetail().toString());
        holder._tgl.setText(""+pengumuman.getTgl().toString());
        Bitmap bitmap = imageCache.get(Integer.parseInt(pengumuman.getId_()));
        holder._P_Image = (ImageView)view.findViewById(R.id.img);

        if (bitmap != null){
            holder._P_Image.setImageBitmap(bitmap);
        } else {
            String imageURL = Config.IMAGE+pengumuman.getImage();
            ImageRequest request = new ImageRequest(imageURL,
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap response) {
                            holder._P_Image.setImageBitmap(response);
                            imageCache.put(Integer.parseInt(pengumuman.getId_()), response);
                        }
                    }, 90, 90, Bitmap.Config.ARGB_8888,
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("error",error.getMessage().toString());
                        }
                    });
            queue.add(request);
        }
        return view;
    }

}
