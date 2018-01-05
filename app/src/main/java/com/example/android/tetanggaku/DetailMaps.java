package com.example.android.tetanggaku;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DetailMaps extends AppCompatActivity {
    TextView tvNama, tvJenis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_maps);

        tvNama = (TextView)findViewById(R.id.tvNama);
        tvJenis = (TextView)findViewById(R.id.tv_jenis);

        Intent intent2 = getIntent();
        Bundle bundle = intent2.getExtras();

        //ambil datanya
        String snippet = bundle.getString("snippet");
        String nama = bundle.getString("nama");

        //pecah string
        String[] separated = snippet.split("\n");

        tvJenis.setText(separated[0]);
        tvNama.setText(nama);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        //ambil intent


    }
}
