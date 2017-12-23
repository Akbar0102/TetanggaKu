package component.event_warga;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.android.tetanggaku.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import adapter.SharedPrefManager;
import adapter.VolleySingleton;
import entity.URLs;
import entity.sharedWarga;
import navdrawer.EventWarga;

public class TambahEvent extends AppCompatActivity {
    EditText et_nama, et_alamat, et_deskripsi;
    Button btn_tambah;
    String nama_evt, alamat_evt, tgl_mulai, deskripsi, id_warga;
    TextView tvTanggal;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    sharedWarga user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_event);

        user = SharedPrefManager.getInstance(this).getwarga();

        et_nama = findViewById(R.id.et_nama_event);
        et_alamat = findViewById(R.id.et_alamat_event);
        tvTanggal = findViewById(R.id.tvDate);
        et_deskripsi = findViewById(R.id.et_deskripsi);

        btn_tambah = findViewById(R.id.btn_tambah_event);

        tvTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        TambahEvent.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = year + "-" + month + "-" + day;
                tvTanggal.setText(date);
            }
        };
    }

    public void tambahEvent(View view){
        id_warga = String.valueOf(user.getId());
        nama_evt = et_nama.getText().toString();
        alamat_evt = et_alamat.getText().toString();
        tgl_mulai = tvTanggal.getText().toString();
        deskripsi = et_deskripsi.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_ADDEVENT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //konversi response ke jsonObject
                            JSONObject obj = new JSONObject(response);

                            //jika tanpa error di response
                            if (!obj.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                et_nama.setText("");
                                et_alamat.setText("");
                                tvTanggal.setText("");
                                et_deskripsi.setText("");
                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_warga", id_warga);
                params.put("nama_event", nama_evt);
                params.put("deskripsi", deskripsi);
                params.put("tanggal", tgl_mulai);
                params.put("alamat", alamat_evt);
                return params;
            }
        };

        VolleySingleton.getmInstance(this).addToRequestQueue(stringRequest);

        Intent intent2 = getIntent();
        intent2.putExtra(EventWarga.EXTRA_MESSAGE, "Ini pesan dari acvtivity tambahEvent (DUA)");
        //bungkus string balasan
        setResult(RESULT_OK,intent2);
        //close acvitituy dua
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
