package navdrawer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.android.tetanggaku.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import adapter.CustomListAdapterEvent;
import adapter.VolleySingleton;
import component.event_warga.TambahEvent;
import entity.Event;
import entity.URLs;

/**
 * Created by Akbar H on 03/12/2017.
 */

public class EventWarga extends Fragment {
    View myView;
    ArrayList<Event> allEvent = new ArrayList<Event>();
    ListView list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        myView = inflater.inflate(R.layout.layout_event_warga, container, false);

        initData();
        list = (ListView) myView.findViewById(R.id.lv_event_warga);

        return myView;
    }

    public void initData(){
        ArrayList<Event> tmp = new ArrayList<Event>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_GETEVENT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray eventArray = obj.getJSONArray("Event");
                            for (int i = 0; i < eventArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject eventObject = eventArray.getJSONObject(i);
                                //creating a event object
                                Event event = new Event(eventObject.getString("nama"), eventObject.getString("deskripsi"), eventObject.getString("nama_event"), eventObject.getString("tanggal_mulai"), eventObject.getString("alamat"));
                                Log.d("nama event", ""+event.getEvent());
                                allEvent.add(event);
                            }
                            CustomListAdapterEvent adapter = new CustomListAdapterEvent(getActivity(), R.layout.custom_list_lihat_event, allEvent);
                            list.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        VolleySingleton.getmInstance(getContext()).addToRequestQueue(stringRequest);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_event_warga, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.tambah_event) {
            Intent i = new Intent(getActivity(), TambahEvent.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

}
