package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.tetanggaku.R;

import java.util.ArrayList;

import entity.Warga;

/**
 * Created by Akbar H on 09/11/2017.
 */

public class CustomListAdapter extends ArrayAdapter<Warga> {
    private Context context;
    private ArrayList<Warga> warga;

    public CustomListAdapter(Context context, int resource, ArrayList<Warga> warga){
        super(context, resource, warga);
        this.warga = warga;
        this.context = context;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){
        //mengkonversi (array ke view), mylist agar bisa dipakai di listview menggunakan inflater
        LayoutInflater inflater = LayoutInflater.from(this.context);
        //memakai view, pilih layoutnya yang mau dikonversi
        View rowView = inflater.inflate(R.layout.my_list, null, true);

        TextView txtNama = (TextView) rowView.findViewById(R.id.txtNama);
        TextView txtJenis = (TextView) rowView.findViewById(R.id.txtJenis);
        TextView txtAlamat = (TextView) rowView.findViewById(R.id.txtAlamat);
        TextView txtStatus = (TextView) rowView.findViewById(R.id.txtStatus);

        //set isi textview sama imageview, yang ada di mylist
        txtNama.setText(warga.get(position).getNama());
        txtJenis.setText(warga.get(position).getJenis());
        txtAlamat.setText(warga.get(position).getAlamat());
        txtStatus.setText(warga.get(position).getStatus());

        return rowView;
    }
}
