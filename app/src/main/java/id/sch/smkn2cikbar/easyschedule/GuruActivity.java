package id.sch.smkn2cikbar.easyschedule;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import id.sch.smkn2cikbar.easyschedule.config.Config;
import id.sch.smkn2cikbar.easyschedule.fragments.GuruFacebookFragment;
import id.sch.smkn2cikbar.easyschedule.fragments.GuruPengumumanFragment;
import id.sch.smkn2cikbar.easyschedule.fragments.GuruTentangFragment;
import id.sch.smkn2cikbar.easyschedule.fragments.GuruTwitterFragment;
import id.sch.smkn2cikbar.easyschedule.fragments.JadwalMengajar;
import id.sch.smkn2cikbar.easyschedule.models.progressDialogModel;

/**
 * Created by Robby Akbar on 09/12/16.
 */

public class GuruActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toast toast;
    private long lastBackPressTime = 0;
    private TextView user, name;
    public String nip_;

    private final static String SELECTED_TAG = "selected_index";
    private static int selectedIndex;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guru);

        navigationView = (NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        if (savedInstanceState!=null){
            navigationView.getMenu().findItem(savedInstanceState.getInt(SELECTED_TAG)).setChecked(true);
            return;
        }

        selectedIndex = R.id.nav_jadwalMengajar;

        View headerView = navigationView.getHeaderView(0);
        user = (TextView)headerView.findViewById(R.id.tvUserName);
        name = (TextView)headerView.findViewById(R.id.tvName);

        SharedPreferences sharedPreferences = GuruActivity.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String text = sharedPreferences.getString(Config.USERNAME_SHARED_PREF_GURU, "");
        user.setText(text);
        nip_ = user.getText().toString();

        ambilData();

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new JadwalMengajar(), JadwalMengajar.class.getSimpleName()).commit();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_TAG, selectedIndex);
    }

    private void ambilData(){
        progressDialogModel.pdMenyiapkanDataLogin(GuruActivity.this);
        String url = Config.AMBIL_DATA_GURU + nip_.trim();
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                showJSON(response);
                progressDialogModel.hideProgressDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(GuruActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                progressDialogModel.hideProgressDialog();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response){
        String nama = "";
        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("profil");
            JSONObject collegeData = result.getJSONObject(0);
            nama = collegeData.getString("nama_guru");
        } catch (JSONException e){
            e.printStackTrace();
        }
        name.setText(nama);
    }

    public void setupNavigationDrawer(Toolbar toolbar){
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_guru, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_jadwalMengajar:
                if(!item.isChecked()){
                    selectedIndex = R.id.nav_jadwalMengajar;
                    item.setChecked(true);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new JadwalMengajar(), JadwalMengajar.class.getSimpleName()).commit();
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_pengumuman:
                if(!item.isChecked()){
                    selectedIndex = R.id.nav_pengumuman;
                    item.setChecked(true);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new GuruPengumumanFragment(), GuruPengumumanFragment.class.getSimpleName()).commit();
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.facebook:
                if(!item.isChecked()){
                    selectedIndex = R.id.facebook;
                    item.setChecked(true);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new GuruFacebookFragment(), GuruFacebookFragment.class.getSimpleName()).commit();
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.twitter:
                if(!item.isChecked()){
                    selectedIndex = R.id.twitter;
                    item.setChecked(true);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new GuruTwitterFragment(), GuruTwitterFragment.class.getSimpleName()).commit();
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.tentang:
                if(!item.isChecked()){
                    selectedIndex = R.id.tentang;
                    item.setChecked(true);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new GuruTentangFragment(), GuruTentangFragment.class.getSimpleName()).commit();
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.logout:
                if(!item.isChecked()){
                    logout();
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.keluar:
                if(!item.isChecked()){
                    keluar();
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            default:
                Toast.makeText(this, "Mohon Maaf!! Menu Belum Tersedia", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private void logout(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
        builder.setMessage("Apakah anda yakin ingin Logout akun anda?")
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences sharedPreferences = GuruActivity.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, false);
                        editor.remove(Config.USERNAME_SHARED_PREF_GURU);
                        editor.apply();
                        editor.commit();
                        Intent intent = new Intent(GuruActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .show();
    }

    private void keluar(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
        builder.setMessage("Apakah anda yakin keluar dari aplikasi ini?")
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        GuruActivity.this.finish();
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .show();
    }

    @Override
    public void onBackPressed() {
        if (this.lastBackPressTime < System.currentTimeMillis() - 4000) {
            toast = Toast.makeText(this, "Tekan sekali lagi untuk keluar",
                    Toast.LENGTH_LONG);
            toast.show();
            this.lastBackPressTime = System.currentTimeMillis();
        } else {
            if (toast != null) {
                toast.cancel();
            }
            super.onBackPressed();
        }
    }

}
