package id.sch.smkn2cikbar.easyschedule.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import id.sch.smkn2cikbar.easyschedule.JadwalPelajaranActivity;
import id.sch.smkn2cikbar.easyschedule.MuridActivity;
import id.sch.smkn2cikbar.easyschedule.R;
import id.sch.smkn2cikbar.easyschedule.adapters.CustomItemClickListener;
import id.sch.smkn2cikbar.easyschedule.adapters.RecyclerAdapter;
import id.sch.smkn2cikbar.easyschedule.config.Config;
import id.sch.smkn2cikbar.easyschedule.models.CardItemModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class JadwalPelajaran extends Fragment {

    private List<CardItemModel> cardItems = new ArrayList<>(6);
    private Toolbar toolbar;
    private MuridActivity muridActivity;
    private android.support.v7.widget.RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;

    public JadwalPelajaran() {
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
        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);

        toolbar = (Toolbar)view.findViewById(R.id.toolbar);
        recyclerView = (android.support.v7.widget.RecyclerView)view.findViewById(R.id.recycler_view);

        setupRecyclerView();
        setupToolbar();

        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        muridActivity.setupNavigationDrawer(toolbar);
    }

    private void setupToolbar(){
        toolbar.setTitle(R.string.jadwalpelajaran_fragment_title);
        toolbar.setSubtitle(R.string.jadwalpelajaran_fragment_subtitle);
        muridActivity.setSupportActionBar(toolbar);
    }

    private void setupRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(muridActivity));
        recyclerView.setHasFixedSize(true);
        initializeCardItemList();
        recyclerAdapter = new RecyclerAdapter(cardItems, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                final Intent intent;
                switch (position){
                    case 0:
                        intent = new Intent(getActivity(), JadwalPelajaranActivity.class);
                        intent.putExtra(Config.TAG_HARI, "Senin");
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(getActivity(), JadwalPelajaranActivity.class);
                        intent.putExtra(Config.TAG_HARI, "Selasa");
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(getActivity(), JadwalPelajaranActivity.class);
                        intent.putExtra(Config.TAG_HARI, "Rabu");
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(getActivity(), JadwalPelajaranActivity.class);
                        intent.putExtra(Config.TAG_HARI, "Kamis");
                        startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(getActivity(), JadwalPelajaranActivity.class);
                        intent.putExtra(Config.TAG_HARI, "Jumat");
                        startActivity(intent);
                        break;
                    case 5:
                        intent = new Intent(getActivity(), JadwalPelajaranActivity.class);
                        intent.putExtra(Config.TAG_HARI, "Sabtu");
                        startActivity(intent);
                        break;
                }
            }
        });
        recyclerView.setAdapter(recyclerAdapter);
    }

    private void initializeCardItemList(){
        CardItemModel cardItemModel;
        String[] cardTitles = getResources().getStringArray(R.array.title_jadwal_pelajaran);
        String[] cardContents = getResources().getStringArray(R.array.subtitle_jadwal_pelajaran);
        final int length = cardTitles.length;
        for (int i=0;i<length;i++){
            cardItemModel = new CardItemModel(cardTitles[i],cardContents[i]);
            cardItems.add(cardItemModel);
        }
    }
}
