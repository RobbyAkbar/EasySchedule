package id.sch.smkn2cikbar.easyschedule;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
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

import id.sch.smkn2cikbar.easyschedule.adapters.JadwalMengajarAdapter;
import id.sch.smkn2cikbar.easyschedule.config.Config;
import id.sch.smkn2cikbar.easyschedule.models.JadwalMengajarModel;
import id.sch.smkn2cikbar.easyschedule.models.progressDialogModel;
import id.sch.smkn2cikbar.easyschedule.parsing.JadwalMengajarJSONParser;

/**
 * Created by Robby Akbar on 14/12/16.
 */

public class JadwalMengajarActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private List<JadwalMengajarModel> jadwalList;
    private ListView listView;
    private CoordinatorLayout coordinatorLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);

        final Intent intent = getIntent();

        listView = (ListView)findViewById(R.id.myList);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        linearLayout = (LinearLayout)findViewById(R.id.linearLayout);
        coordinatorLayout = (CoordinatorLayout)findViewById(R.id.coordinator_layout);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.CYAN);
        SharedPreferences sharedPreferences = JadwalMengajarActivity.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String nip = sharedPreferences.getString(Config.USERNAME_SHARED_PREF_GURU, "");
        final String hari = intent.getStringExtra(Config.TAG_HARI);

        toolbar.setTitle("Jadwal Mengajar");
        toolbar.setSubtitle("Hari " + hari);

        setupToolbar();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestData(Config.JADWAL_GURU+nip+"&hari="+hari);
                progressDialogModel.pdMenyiapkanDataJadwal(JadwalMengajarActivity.this);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                JadwalMengajarModel jadwal = jadwalList.get(i);
                final Snackbar snackbar = Snackbar.make(coordinatorLayout, jadwal.getMapel(), Snackbar.LENGTH_SHORT);
                snackbar.setAction("Oke", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        snackbar.dismiss();
                    }
                });
                snackbar.show();
            }
        });

        requestData(Config.JADWAL_GURU+nip+"&hari="+hari);
        progressDialogModel.pdMenyiapkanDataJadwal(JadwalMengajarActivity.this);

    }

    public void requestData(String uri){

        final StringRequest stringRequest = new StringRequest(uri,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        jadwalList = JadwalMengajarJSONParser.parseData(response);
                        JadwalMengajarAdapter adapter = new JadwalMengajarAdapter(JadwalMengajarActivity.this, jadwalList);
                        listView.setAdapter(adapter.sortData());
                        progressDialogModel.hideProgressDialog();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(JadwalMengajarActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        linearLayout.setBackgroundResource(R.drawable.offline);
                        progressDialogModel.hideProgressDialog();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }

    private void setupToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
