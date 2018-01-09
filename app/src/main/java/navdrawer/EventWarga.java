package navdrawer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import component.DetailTetangga;
import component.event_warga.TambahEvent;
import entity.Event;
import entity.URLs;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Akbar H on 03/12/2017.
 */

public class EventWarga extends Fragment {
    View myView;
    ArrayList<Event> allEvent = new ArrayList<Event>();
    CustomListAdapterEvent adapter;
    ListView list;
    static final int ACT2_REQUEST = 99;  // request code
    public final static String EXTRA_MESSAGE ="edu.upi.cs.yudiwbs.app1.MESSAGE";
    Handler handler = new Handler();
    ProgressDialog progress;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        myView = inflater.inflate(R.layout.layout_event_warga, container, false);
        adapter = new CustomListAdapterEvent(getActivity(), R.layout.custom_list_lihat_event, allEvent);

        initData();
        list = (ListView) myView.findViewById(R.id.lv_event_warga);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), DetailTetangga.class);
                i.putExtra(EXTRA_MESSAGE, allEvent.get(position).getNama());
                i.putExtra("alamat", allEvent.get(position).getEvent());
                i.putExtra("job", allEvent.get(position).getDeskripsi());
                startActivity(i);
            }
        });

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
                            adapter = new CustomListAdapterEvent(getActivity(), R.layout.custom_list_lihat_event, allEvent);
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
    public void onResume() {
        adapter.notifyDataSetChanged();
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.tambah_event) {
            adapter.clear();
            Intent i = new Intent(getActivity(), TambahEvent.class);
            //startActivity(i);
            startActivityForResult(i, ACT2_REQUEST);
        }else if(id == R.id.refresh_event){
            adapter.clear();
            progress=new ProgressDialog(getContext());
            progress.setMessage("Updating Data...");
            progress.setCancelable(false);
            progress.show();



            handler.postDelayed(new Runnable() {
                public void run() {
                    progress.dismiss();
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                initData();
                            }catch (Exception e){}
                        }

                    });
                }
            }, 5000);   //5 seconds
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // cek request code
        if (resultCode == RESULT_OK) {
            String pesan = data.getStringExtra(EXTRA_MESSAGE);
            //tampilkan toast
            //Toast toast = Toast.makeText(getContext(), "Pesan:"+pesan, Toast.LENGTH_LONG);
            //toast.show();
        }
        initData();
    }


}
