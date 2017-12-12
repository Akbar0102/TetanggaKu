package navdrawer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.tetanggaku.R;

import component.lokasi_penting.LokasiTetangga;

/**
 * Created by Akbar H on 29/11/2017.
 */

public class LokasiPenting extends Fragment{
    View myView;
    //Button btnPendidikan;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        myView = inflater.inflate(R.layout.layout_lokasi_penting, container, false);
        //btnPendidikan = myView.findViewById(R.id.btn_pendidikan);
        //btnPendidikan.setOnClickListener(this);
        return myView;
    }

    @Override
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
    }
}
