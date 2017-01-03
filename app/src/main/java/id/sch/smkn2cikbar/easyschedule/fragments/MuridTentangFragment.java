package id.sch.smkn2cikbar.easyschedule.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import id.sch.smkn2cikbar.easyschedule.MuridActivity;
import id.sch.smkn2cikbar.easyschedule.R;
import id.sch.smkn2cikbar.easyschedule.adapters.ClipboardUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class MuridTentangFragment extends Fragment {

    ArrayList<DetailBaris> list;
    DetailBaris detailBaris;
    AdapterSaya adapterSaya;
    ListView listView;
    ClipboardUtil clipboardUtil;

    private static final String[] text = new String[]{
            "Jadwal Pelajaran V4.0 (Check Update)",
            "Created by Robby Akbar",
            " PIN D5377D5 (Klik untuk salin)",
            " www.facebook.com/robby.akbar.75",
            " www.android-inyourhand.esy.es",
            "Copyright © 2016 ICT Art-Tractive",
            " www.facebook.com/groups/1609683786018600",
            " www.artractivesite.wordpress.com",
    };

    private MuridActivity muridActivity;
    private Toolbar toolbar;
    private CoordinatorLayout coordinatorLayout;

    public MuridTentangFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        muridActivity = (MuridActivity)activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tentang, container, false);

        toolbar = (Toolbar)view.findViewById(R.id.toolbar);
        setupToolbar();
        coordinatorLayout = (CoordinatorLayout)view.findViewById(R.id.coordinator_layout);

        list = new ArrayList<>();
        adapterSaya = new AdapterSaya(getActivity().getApplicationContext(), list);
        listView = (ListView)view.findViewById(R.id.list_view_about);
        listView.setAdapter(adapterSaya);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Drawable[]icon = new Drawable[8];
                icon[0] = getResources().getDrawable(R.mipmap.ic_launcher);
                icon[1] = null;
                icon[2] = getResources().getDrawable(R.drawable.bbm);
                icon[3] = getResources().getDrawable(R.drawable.fb);
                icon[4] = getResources().getDrawable(R.drawable.www);
                icon[5] = null;
                icon[6] = getResources().getDrawable(R.drawable.fb);
                icon[7] = getResources().getDrawable(R.drawable.www);

                list.clear();
                for (int i=0;i<text.length;i++){
                    detailBaris = new DetailBaris();
                    detailBaris.string = text[i];
                    detailBaris.drawable = icon[i];
                    list.add(detailBaris);
                }

                adapterSaya.notifyDataSetChanged();
                clipboardUtil = new ClipboardUtil();
                listView.setOnItemClickListener(detectClick());

            }
        }, 100);

        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        muridActivity.setupNavigationDrawer(toolbar);
    }

    private void setupToolbar(){
        toolbar.setTitle(R.string.tentang_fragment_title);
        toolbar.setSubtitle(R.string.tentang_fragment_subtitle);
        muridActivity.setSupportActionBar(toolbar);
    }

    private AdapterView.OnItemClickListener detectClick(){
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (text[i].contains("Jadwal")){
                    final String appPackageName = "id.sch.smkn2cikbar.easyschedule";
                    startActivity(Intent.createChooser(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)), "Buka dengan"));
                }
                if (text[i].contains("PIN")){
                    if (clipboardUtil.copyToClipboard(getActivity().getApplicationContext(), text[i].trim().split(" ")[1])){
                        anim();
                        final Snackbar snackbar = Snackbar.make(coordinatorLayout, "PIN disalin", Snackbar.LENGTH_SHORT);
                        snackbar.setAction("Oke", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                snackbar.dismiss();
                            }
                        });
                        snackbar.show();
                    }
                }
                if (text[i].contains(".com")){
                    startActivity(Intent.createChooser(new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + text[i].trim())), "Buka dengan"));
                }
                if (text[i].contains(".esy.es")){
                    startActivity(Intent.createChooser(new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + text[i].trim())), "Buka dengan"));
                }
            }
        };
    }

    private class DetailBaris{
        Drawable drawable;
        String string;
    }

    private class AdapterSaya extends BaseAdapter{
        List<DetailBaris>list = new ArrayList<>();
        LayoutInflater layoutInflater;
        AdapterSaya(Context context, ArrayList<DetailBaris>list){
            layoutInflater = LayoutInflater.from(context);
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (view==null){
                view = layoutInflater.inflate(R.layout.tentang_list, null);
                viewHolder = new ViewHolder();
                viewHolder.imageView = (ImageView)view.findViewById(R.id.about_row_ikon);
                viewHolder.textView = (TextView)view.findViewById(R.id.about_row_text);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder)view.getTag();
            }
            if (list.get(i)!=null){
                viewHolder.imageView.setImageDrawable(list.get(i).drawable);
                viewHolder.textView.setText(list.get(i).string);
            }
            return view;
        }

        class ViewHolder{
            ImageView imageView;
            TextView textView;
        }

    }

    public void anim(){
        LinearLayout ll = (LinearLayout) getActivity().findViewById(R.id.banner);
        Animation animation1 = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.anim);
        ll.startAnimation(animation1);
    }

}
