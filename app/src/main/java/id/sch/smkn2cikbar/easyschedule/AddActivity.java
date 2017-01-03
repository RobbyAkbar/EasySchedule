package id.sch.smkn2cikbar.easyschedule;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import id.sch.smkn2cikbar.easyschedule.helper.RealmHelper;

/**
 * Created by Robby Akbar on 30/12/16.
 */

public class AddActivity extends AppCompatActivity {

    private EditText inputMapel, inputTugas, inputDeadline;
    private TextInputLayout inputLayoutMapel, inputLayoutTugas, inputLayoutDeadline;
    private TextView textViewCatatan;
    private Toolbar toolbar;
    private RealmHelper realmHelper;
    private InputMethodManager inputMethodManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catatan);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        inputMapel = (EditText)findViewById(R.id.input_mapel);
        inputMapel.requestFocus();
        inputTugas = (EditText)findViewById(R.id.input_tugas);
        inputDeadline = (EditText)findViewById(R.id.input_deadline);
        inputLayoutMapel = (TextInputLayout)findViewById(R.id.input_layout_mapel);
        inputLayoutTugas = (TextInputLayout)findViewById(R.id.input_layout_tugas);
        inputLayoutDeadline = (TextInputLayout)findViewById(R.id.input_layout_deadline);
        textViewCatatan = (TextView)findViewById(R.id.tvCatatan);
        textViewCatatan.setText(R.string.add_activity_title);
        realmHelper = new RealmHelper(AddActivity.this);
        inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        inputMapel.addTextChangedListener(new MyTextWatcher(inputMapel));
        inputTugas.addTextChangedListener(new MyTextWatcher(inputTugas));
        inputDeadline.addTextChangedListener(new MyTextWatcher(inputDeadline));

        setupToolbar();

    }

    private void setupToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle(R.string.add_activity_title);
        toolbar.setSubtitle(R.string.add_activity_subtitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_catatan, menu);
        MenuItem item = menu.findItem(R.id.hapus);
        item.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                onBackPressed();
                return true;
            case R.id.simpan:
                submitCatatan();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void submitCatatan(){
        if (!validateMapel()){
            return;
        }
        if (!validateTugas()){
            return;
        }
        if (!validateDeadline()){
            return;
        }
        String pelajaran, tugas, deadline;
        pelajaran = inputMapel.getText().toString();
        tugas = inputTugas.getText().toString();
        deadline = inputDeadline.getText().toString();
        realmHelper.addDaftarCatatan(pelajaran, tugas, deadline);
        inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        finish();
    }

    private boolean validateMapel(){
        if (inputMapel.getText().toString().trim().isEmpty()){
            inputLayoutMapel.setError("Harap masukan nama Mata Pelajaran!!");
            requestFocus(inputMapel);
            return false;
        } else {
            inputLayoutMapel.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateTugas(){
        if (inputTugas.getText().toString().trim().isEmpty()){
            inputLayoutTugas.setError("Harap masukan tugas!!");
            requestFocus(inputTugas);
            return false;
        } else {
            inputLayoutTugas.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateDeadline(){
        if (inputDeadline.getText().toString().trim().isEmpty()){
            inputLayoutDeadline.setError("Harap masukan deadline tugas!!");
            requestFocus(inputDeadline);
            return false;
        } else {
            inputLayoutDeadline.setErrorEnabled(false);
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
                case R.id.input_mapel:
                    validateMapel();
                    break;
                case R.id.input_tugas:
                    validateTugas();
                    break;
                case R.id.input_deadline:
                    validateDeadline();
                    break;
            }
        }
    }

}
