package id.sch.smkn2cikbar.easyschedule;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

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
 * Created by robby on 03/01/17.
 */

public class SignupActivity extends AppCompatActivity {

    private TextView login;
    private EditText inputnis, inputnama_siswa, inputpassword;
    private Spinner spKelas;
    private ButtonRectangle btnSignup;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        inputnis = (EditText)findViewById(R.id.input_nis);
        inputnama_siswa = (EditText)findViewById(R.id.input_nama);
        inputpassword = (EditText)findViewById(R.id.input_password);
        spKelas = (Spinner)findViewById(R.id.spKelas);
        btnSignup = (ButtonRectangle)findViewById(R.id.btnSignup);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });
        login = (TextView)findViewById(R.id.login);
        login.setText(Html.fromHtml("&nbsp;"+"<u><b>disini</b></u>"));
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        inputnis.addTextChangedListener(new MyTextWatcher(inputnis));
        inputnama_siswa.addTextChangedListener(new MyTextWatcher(inputnama_siswa));
        inputpassword.addTextChangedListener(new MyTextWatcher(inputpassword));
        kelas();

    }

    private void kelas(){
        List<String> list = new ArrayList<>();
        String[] kelas = getResources().getStringArray(R.array.kelas);
        final int length = kelas.length;
        for (int i=0;i<length;i++){
            list.add(kelas[i]);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spKelas.setAdapter(adapter);
    }

    private void signup(){
        final String nis, nama_siswa, password, spKodekelas, kode_kelas;

        if (!validateNIS()){
            return;
        }
        if (!validateNama()){
            return;
        }
        if (!validatePassword()){
            return;
        }

        progressDialogModel.pdSignUP(SignupActivity.this);

        nis = inputnis.getText().toString();
        nama_siswa = inputnama_siswa.getText().toString();
        password = inputpassword.getText().toString();
        spKodekelas = spKelas.getSelectedItem().toString();
        kode_kelas = spKodekelas.replace(" ","");

        StringRequest request = new StringRequest(Request.Method.POST, Config.SIGN_UP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response.toString());
                ToastModel.ToashBerhasilSignUP(SignupActivity.this);
                progressDialogModel.hideProgressDialog();
                inputnis.setText("");
                inputnama_siswa.setText("");
                inputpassword.setText("");
                inputnis.setError(null);
                inputnama_siswa.setError(null);
                inputpassword.setError(null);
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ToastModel.ToashGagalSignUP(SignupActivity.this);
                progressDialogModel.hideProgressDialog();
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> parameters = new HashMap<>();
                parameters.put("nis", nis);
                parameters.put("nama_siswa", nama_siswa);
                parameters.put("kode_kelas", kode_kelas);
                parameters.put("password", password);
                return parameters;
            }
        };
        requestQueue.add(request);
    }

    private boolean validateNIS(){
        if (inputnis.getText().toString().trim().isEmpty() || inputnis.getText().toString().trim().length()<9){
            inputnis.setError("NIS setidaknya 9 karakter!!");
            requestFocus(inputnis);
            return false;
        } else {
            inputnis.setError(null);
        }
        return true;
    }

    private boolean validateNama(){
        if (inputnama_siswa.getText().toString().trim().isEmpty()){
            inputnama_siswa.setError("Harap masukan Nama Anda!!");
            requestFocus(inputnama_siswa);
            return false;
        } else {
            inputnama_siswa.setError(null);
        }
        return true;
    }

    private boolean validatePassword(){
        if (inputpassword.getText().toString().trim().isEmpty() || inputpassword.getText().toString().trim().length()<6){
            inputpassword.setError("Password setidaknya 6 karakter atau lebih!!");
            requestFocus(inputpassword);
            return false;
        } else {
            inputpassword.setError(null);
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_nis:
                    validateNIS();
                    break;
                case R.id.input_nama:
                    validateNama();
                    break;
                case R.id.input_password:
                    validatePassword();
                    break;
            }
        }
    }

}
