package component;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.android.tetanggaku.R;

import navdrawer.TetanggaSekitar;

public class DetailTetangga extends AppCompatActivity {
    TextView txt_detail_nama,txt_detail_alamat,txt_detail_job;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tetangga);
        txt_detail_nama = (TextView) findViewById(R.id.txt_detail_nama);
        txt_detail_alamat = (TextView) findViewById(R.id.txt_detail_alamat);
        txt_detail_job = (TextView) findViewById(R.id.txt_detail_job);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        //ambil intent
        Intent intent2 = getIntent();
        Bundle bundle = intent2.getExtras();

        //ambil datanya
        String nama = intent2.getStringExtra(TetanggaSekitar.EXTRA_MESSAGE);
        String alamat = bundle.getString("alamat");
        String job = bundle.getString("job");
        //tampilkan pesan sebagai toast
        //Toast t = Toast.makeText(getApplicationContext(), "Pesan:"+pesan,Toast.LENGTH_LONG);
        //t.show();
        txt_detail_nama.setText(nama);
        txt_detail_alamat.setText(alamat);
        txt_detail_job.setText(job);
    }
}
