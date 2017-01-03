package id.sch.smkn2cikbar.easyschedule.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import id.sch.smkn2cikbar.easyschedule.AddActivity;
import id.sch.smkn2cikbar.easyschedule.EditActivity;
import id.sch.smkn2cikbar.easyschedule.MuridActivity;
import id.sch.smkn2cikbar.easyschedule.R;
import id.sch.smkn2cikbar.easyschedule.adapters.CatatanAdapter;
import id.sch.smkn2cikbar.easyschedule.helper.DaftarCatatan;
import id.sch.smkn2cikbar.easyschedule.models.DaftarCatatanModel;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * A simple {@link Fragment} subclass.
 */
public class MuridCatatanFragment extends Fragment {

    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private RealmHelper realmHelper;
    private ArrayList<DaftarCatatanModel> daftarCatatanModels;
    private MuridActivity muridActivity;
    private Toolbar toolbar;

    public MuridCatatanFragment() {
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
        View view = inflater.inflate(R.layout.fragment_catatan, container, false);

        toolbar = (Toolbar)view.findViewById(R.id.toolbar);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        floatingActionButton = (FloatingActionButton)view.findViewById(R.id.fab);
        daftarCatatanModels = new ArrayList<>();
        realmHelper = new RealmHelper(getActivity());

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity().getApplicationContext(), AddActivity.class));
            }
        });

        setRecyclerView();
        setupToolbar();

        return view;
    }

    public class RealmHelper {

        private Realm realm;
        private RealmResults<DaftarCatatan> realmResults;
        public Context context;

        public RealmHelper (Context context){
            realm = Realm.getInstance(context);
            this.context = context;
        }

        public ArrayList<DaftarCatatanModel>findAllCatatan(){
            ArrayList<DaftarCatatanModel> daftarCatatanModels = new ArrayList<>();
            realmResults = realm.where(DaftarCatatan.class).findAll();
            realmResults.sort("id", Sort.DESCENDING);
            if (realmResults.size()>0){
                recyclerView.setBackgroundColor(getResources().getColor(android.R.color.white));
                showLog("Size : " + realmResults.size());
                for (int i=0; i<realmResults.size(); i++){
                    String pelajaran, tugas, deadline;
                    int id = realmResults.get(i).getId();
                    pelajaran = realmResults.get(i).getPelajaran();
                    tugas = realmResults.get(i).getTugas();
                    deadline = realmResults.get(i).getDeadline();
                    daftarCatatanModels.add(new DaftarCatatanModel(id, pelajaran, tugas, deadline));
                }
            } else {
                recyclerView.setBackgroundResource(R.drawable.empty_note);
                showLog("Size : 0");
                showToast("Tidak ada tugas!");
            }
            return daftarCatatanModels;
        }

        private void showLog(String s){
            Log.d("RealmHelper", s);
        }

        private void showToast(String s){
            Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
        }

    }

    private void setRecyclerView(){
        try {
            daftarCatatanModels = realmHelper.findAllCatatan();
        } catch (Exception e){
            e.printStackTrace();
        }
        CatatanAdapter catatanAdapter = new CatatanAdapter(daftarCatatanModels, new CatatanAdapter.OnItemClickListener() {
            @Override
            public void onClick(DaftarCatatanModel item) {
                Intent intent = new Intent(getActivity().getApplicationContext(), EditActivity.class);
                intent.putExtra("id", item.getId());
                intent.putExtra("pelajaran", item.getPelajaran());
                intent.putExtra("tugas", item.getTugas());
                intent.putExtra("deadline", item.getDeadline());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(catatanAdapter);
    }

    @Override
    public void onResume() {
        try {
            daftarCatatanModels = realmHelper.findAllCatatan();
        } catch (Exception e){
            e.printStackTrace();
        }
        CatatanAdapter catatanAdapter = new CatatanAdapter(daftarCatatanModels, new CatatanAdapter.OnItemClickListener() {
            @Override
            public void onClick(DaftarCatatanModel item) {
                Intent intent = new Intent(getActivity().getApplicationContext(), EditActivity.class);
                intent.putExtra("id", item.getId());
                intent.putExtra("pelajaran", item.getPelajaran());
                intent.putExtra("tugas", item.getTugas());
                intent.putExtra("deadline", item.getDeadline());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(catatanAdapter);
        super.onResume();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        muridActivity.setupNavigationDrawer(toolbar);
    }

    private void setupToolbar(){
        toolbar.setTitle(R.string.catatan_fragment_title);
        toolbar.setSubtitle(R.string.catatan_fragment_subtitle);
        muridActivity.setSupportActionBar(toolbar);
    }

}
