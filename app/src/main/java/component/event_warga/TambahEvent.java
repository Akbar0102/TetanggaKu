package component.event_warga;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.tetanggaku.R;

import java.util.Calendar;

public class TambahEvent extends AppCompatActivity {
    EditText et_nama, et_alamat, et_deskripsi;
    Button btn_tambah;
    String nama_evt, alamat_evt, tgl_mulai, deskripsi;
    TextView tvTanggal;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_event);

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
                String date = month + "/" + day + "/" + year;
                tvTanggal.setText(date);
            }
        };
    }

    public void tambahEvent(View view){
        nama_evt = et_nama.getText().toString();
        alamat_evt = et_alamat.getText().toString();
        tgl_mulai = tvTanggal.getText().toString();
        deskripsi = et_deskripsi.getText().toString();
    }
}
