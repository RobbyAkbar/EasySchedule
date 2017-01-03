package id.sch.smkn2cikbar.easyschedule;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gc.materialdesign.views.ButtonRectangle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.sch.smkn2cikbar.easyschedule.config.Config;
import id.sch.smkn2cikbar.easyschedule.models.ToastModel;
import id.sch.smkn2cikbar.easyschedule.models.progressDialogModel;

/**
 * Created by Robby Akbar on 03/12/16.
 */

public class LoginActivity extends AppCompatActivity {

    Spinner sphakakses;
    ButtonRectangle btnlogin;
    EditText editUsername, editPassword;
    TextView signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sphakakses = (Spinner)findViewById(R.id.spHakAkses);
        btnlogin = (ButtonRectangle) findViewById(R.id.btnLogin);
        editUsername = (EditText)findViewById(R.id.editUserName);
        editPassword = (EditText)findViewById(R.id.editUserPassword);
        signup = (TextView)findViewById(R.id.signup);
        signup.setText(Html.fromHtml("&nbsp;"+"<u><b>disini</b></u>"));
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });
        hakakses();
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String hak = sphakakses.getSelectedItem().toString();
                if (hak.equals("Murid")){
                    loginMurid();
                } else if (hak.equals("Guru")){
                    loginGuru();
                }
            }
        });

    }

    private void hakakses(){
        List<String> list = new ArrayList<>();
        list.add("Murid");
        list.add("Guru");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sphakakses.setAdapter(adapter);
    }

    private void loginMurid() {
        progressDialogModel.pdLogin(LoginActivity.this);
        final String nis = editUsername.getText().toString().trim();
        final String password = editPassword.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.LOGIN_URL_SISWA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains(Config.LOGIN_SUCCESS)) {
                            SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
                            editor.putString(Config.USERNAME_SHARED_PREF_SISWA, nis);
                            editor.apply();
                            editor.commit();
                            Intent intent = new Intent(LoginActivity.this, MuridActivity.class);
                            progressDialogModel.hideProgressDialog();
                            intent.putExtra(Config.NIS, editUsername.getText().toString());
                            startActivity(intent);
                            finish();
                            Toast.makeText(LoginActivity.this, "Anda Login Sebagai Murid", Toast.LENGTH_SHORT).show();
                        } else {
                            ToastModel.ToashGagalLoginR(LoginActivity.this);
                            progressDialogModel.hideProgressDialog();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(Config.NIS, nis);
                params.put(Config.KEY_PASS, password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void loginGuru(){
        progressDialogModel.pdLogin(LoginActivity.this);
        final String nip = editUsername.getText().toString().trim();
        final String password = editPassword.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.LOGIN_URL_GURU,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains(Config.LOGIN_SUCCESS)) {
                            SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
                            editor.putString(Config.USERNAME_SHARED_PREF_GURU, nip);
                            editor.apply();
                            editor.commit();
                            Intent intent = new Intent(LoginActivity.this, GuruActivity.class);
                            progressDialogModel.hideProgressDialog();
                            intent.putExtra(Config.NIP, editUsername.getText().toString());
                            startActivity(intent);
                            finish();
                            Toast.makeText(LoginActivity.this, "Anda Login Sebagai Guru", Toast.LENGTH_SHORT).show();
                        } else {
                            ToastModel.ToashGagalLoginR(LoginActivity.this);
                            progressDialogModel.hideProgressDialog();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(Config.NIP, nip);
                params.put(Config.KEY_PASS, password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
