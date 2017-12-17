package component.event_warga;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.tetanggaku.R;

public class TambahEvent extends AppCompatActivity {
    EditText et_nama, et_alamat, et_tgl_mulai, et_tgl_selesai, et_deskripsi;
    Button btn_tambah;
    String nama_evt, alamat_evt, tgl_mulai, tgl_selesai, deskripsi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_event);

        et_nama = findViewById(R.id.et_nama_event);
        et_alamat = findViewById(R.id.et_alamat_event);
        et_tgl_mulai = findViewById(R.id.et_tanggal_mulai);
        et_tgl_selesai = findViewById(R.id.et_tanggal_selesai);
        et_deskripsi = findViewById(R.id.et_deskripsi);

        btn_tambah = findViewById(R.id.btn_tambah_event);
    }

    public void tambahEvent(View view){
        nama_evt = et_nama.getText().toString();
        alamat_evt = et_alamat.getText().toString();
        tgl_mulai = et_tgl_mulai.getText().toString();
        tgl_selesai = et_tgl_selesai.getText().toString();
        deskripsi = et_deskripsi.getText().toString();
    }
}
