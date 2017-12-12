package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.tetanggaku.R;

import java.util.ArrayList;

import entity.Event;

/**
 * Created by Akbar H on 04/12/2017.
 */

public class CustomListAdapterEvent extends ArrayAdapter<Event> {
    private Context context;
    private ArrayList<Event> event;

    public CustomListAdapterEvent(Context context, int resource, ArrayList<Event> event){
        super(context, resource, event);
        this.event = event;
        this.context = context;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){
        //mengkonversi (array ke view), mylist agar bisa dipakai di listview menggunakan inflater
        LayoutInflater inflater = LayoutInflater.from(this.context);
        //memakai view, pilih layoutnya yang mau dikonversi
        View rowView = inflater.inflate(R.layout.custom_list_lihat_event, null, true);

        TextView txtEvent = (TextView) rowView.findViewById(R.id.tvEvent);
        TextView txtDeskripsi = (TextView) rowView.findViewById(R.id.tvDeskripsi);

        Event evt = event.get(position);

        //set isi textview sama imageview, yang ada di mylist
        txtEvent.setText(event.get(position).getNama());
        txtDeskripsi.setText(event.get(position).getDeskripsi());

        return rowView;
    }
}
