package navdrawer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import adapter.CustomListAdapterTetangga;
import adapter.VolleySingleton;
import component.DetailTetangga;
import database.DbWarga;
import entity.URLs;
import entity.Warga;

/**
 * Created by Akbar H on 09/11/2017.
 */

public class TetanggaSekitar extends Fragment {
    View myView;
    private Warga[] warga;
    ArrayList<Warga> allWarga = new ArrayList<Warga>();
    ListView list;
    CustomListAdapterTetangga adapter;
    public final static String EXTRA_MESSAGE ="edu.upi.cs.yudiwbs.app1.MESSAGE";
    DbWarga db;
    Handler handler = new Handler();
    ProgressDialog progress;
    int toastDurationInMilliSeconds = 5000;

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        myView = inflater.inflate(R.layout.layout_tetangga, container, false);
        db = new DbWarga(getActivity());
        db.open();

        list = (ListView) myView.findViewById(R.id.lv_tetangga);

        //initData();
        allWarga = db.getAllWarga();
        adapter = new CustomListAdapterTetangga(getActivity(),R.layout.custom_list_tetangga, allWarga);
        list.setAdapter(adapter);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), DetailTetangga.class);
                i.putExtra(EXTRA_MESSAGE, allWarga.get(position).getNama());
                i.putExtra("alamat", allWarga.get(position).getAlamat());
                i.putExtra("job", allWarga.get(position).getPekerjaan());
                startActivity(i);
                //Toast.makeText(getContext(),allWarga.get(position).getNama(),Toast.LENGTH_SHORT).show();
            }
        });
        return myView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_sync, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.mn_sync) {
            adapter.clear();

            /*final Toast toast = Toast.makeText(getContext(),"Updating Data..", Toast.LENGTH_LONG);

            // Set the countdown to display the toast
            final CountDownTimer toastCountDown;
            toastCountDown = new CountDownTimer(toastDurationInMilliSeconds, 1000) {
                public void onTick(long millisUntilFinished) {
                    toast.show();
                }
                public void onFinish() {
                    toast.cancel();
                }
            }.start();*/

            progress=new ProgressDialog(getContext());
            progress.setMessage("Updating Data...");
            progress.setCancelable(false);
            progress.show();


            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    try{
                        db.removeAll();
                        initData();
                    }catch (Exception e){}
                }

            });

            handler.postDelayed(new Runnable() {
                public void run() {
                    progress.dismiss();
                    allWarga = db.getAllWarga();
                    adapter = new CustomListAdapterTetangga(getActivity(),R.layout.custom_list_tetangga, allWarga);
                    list.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }, 5000);   //5 seconds

        }

        return super.onOptionsItemSelected(item);
    }

    private void initData() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_GETWARGA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray wargaArray = obj.getJSONArray("Warga");
                            for (int i = 0; i < wargaArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject wargaObject = wargaArray.getJSONObject(i);

                                //creating a hero object and giving them the values from json object
                                //Warga warga = new Warga(wargaObject.getString("nama"), wargaObject.getString("jenis_perangkat"), wargaObject.getString("alamat"), wargaObject.getString("status"), wargaObject.getDouble("latitude"), wargaObject.getDouble("longitude"), wargaObject.getString("pekerjaan"));
                                db.insertWarga(
                                        wargaObject.getString("nama"),
                                        wargaObject.getString("nik"),
                                        wargaObject.getString("alamat"),
                                        wargaObject.getString("jenis_kelamin"),
                                        wargaObject.getString("jenis_perangkat"),
                                        wargaObject.getString("pekerjaan"),
                                        wargaObject.getString("status"),
                                        wargaObject.getDouble("latitude"),
                                        wargaObject.getDouble("longitude"),
                                        wargaObject.getInt("id_warga"));
                                //allWarga.add(warga);
                            }
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

}
