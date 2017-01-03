package id.sch.smkn2cikbar.easyschedule.fragments;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;

import id.sch.smkn2cikbar.easyschedule.GuruActivity;
import id.sch.smkn2cikbar.easyschedule.R;
import id.sch.smkn2cikbar.easyschedule.adapters.PengumumanAdapter;
import id.sch.smkn2cikbar.easyschedule.config.Config;
import id.sch.smkn2cikbar.easyschedule.models.PengumumanModel;
import id.sch.smkn2cikbar.easyschedule.models.progressDialogModel;
import id.sch.smkn2cikbar.easyschedule.parsing.PengumumanJSONParser;

/**
 * A simple {@link Fragment} subclass.
 */
public class GuruPengumumanFragment extends Fragment {

    private Toolbar toolbar;
    private GuruActivity guruActivity;
    private List<PengumumanModel> pengumumanList;
    private ListView listView;
    private CoordinatorLayout coordinatorLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout linearLayout;

    public GuruPengumumanFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        guruActivity = (GuruActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.list_view, container, false);

        toolbar = (Toolbar)view.findViewById(R.id.toolbar);
        linearLayout = (LinearLayout)view.findViewById(R.id.linearLayout);
        coordinatorLayout = (CoordinatorLayout)view.findViewById(R.id.coordinator_layout);
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.CYAN);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requetData(Config.PENGUMUMAN);
                progressDialogModel.pdPengumuman(getActivity());
            }
        });

        setupToolbar();

        listView = (ListView)view.findViewById(R.id.myList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PengumumanModel pengumuman = pengumumanList.get(i);
                final Snackbar snackBar = Snackbar.make(coordinatorLayout, pengumuman.getJudul(), Snackbar.LENGTH_LONG);
                snackBar.setAction("Oke", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackBar.dismiss();
                    }
                });
                snackBar.show();
            }
        });
        requetData(Config.PENGUMUMAN);
        progressDialogModel.pdPengumuman(getActivity());

        return view;
    }

    public void requetData(String uri){
        StringRequest request = new StringRequest(uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pengumumanList = PengumumanJSONParser.parseData(response);
                        PengumumanAdapter adapter = new PengumumanAdapter(getActivity(), pengumumanList);
                        listView.setAdapter(adapter);
                        progressDialogModel.hideProgressDialog();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        linearLayout.setBackgroundResource(R.drawable.offline);
                        progressDialogModel.hideProgressDialog();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(request);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        guruActivity.setupNavigationDrawer(toolbar);
    }

    private void setupToolbar(){
        toolbar.setTitle(R.string.pengumuman_fragment_title);
        toolbar.setSubtitle(R.string.pengumuman_fragment_subtitle);
        guruActivity.setSupportActionBar(toolbar);
    }

}
