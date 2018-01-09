package navdrawer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.android.tetanggaku.R;

import component.lokasi_penting.LokasiAdministrasi;
import component.lokasi_penting.LokasiEntertainment;
import component.lokasi_penting.LokasiTetangga;
import component.lokasi_penting.LokasiUsaha;

/**
 * Created by Akbar H on 29/11/2017.
 */

public class LokasiPenting extends Fragment{
    View myView;
    Button btn_lokasi_tetangga,btn_wilayah, btn_grosir, btn_enter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //setHasOptionsMenu(true);
        myView = inflater.inflate(R.layout.layout_lokasi_penting, container, false);

        btn_lokasi_tetangga = myView.findViewById(R.id.btn_lokasi_tetangga);
        btn_lokasi_tetangga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), LokasiTetangga.class);
                startActivity(i);
            }
        });

        btn_wilayah = myView.findViewById(R.id.btn_wilayah);
        btn_wilayah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), LokasiAdministrasi.class);
                startActivity(i);
            }
        });

        btn_grosir = myView.findViewById(R.id.btn_grosir);
        btn_grosir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), LokasiUsaha.class);
                startActivity(i);
            }
        });

        btn_enter = myView.findViewById(R.id.btn_entertainment);
        btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), LokasiEntertainment.class);
                startActivity(i);
            }
        });
        return myView;
    }


    /*@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_lokasi_penting, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.mn_lok_tet) {
            Intent i = new Intent(getActivity(), LokasiTetangga.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }*/
}
