package navdrawer;

import android.app.ProgressDialog;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.android.tetanggaku.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import adapter.CustomListAdapterForum;
import adapter.SharedPrefManager;
import adapter.VolleySingleton;
import entity.Forum;
import entity.URLs;
import entity.sharedWarga;

/**
 * Created by Akbar H on 07/11/2017.
 */

public class ForumWarga extends Fragment {
    View myView;
    ArrayList<Forum> allForum = new ArrayList<Forum>();
    CustomListAdapterForum adapter;
    ListView list;
    EditText et_feed;
    Handler handler = new Handler();
    ProgressDialog progress;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        myView = inflater.inflate(R.layout.layout_forum_warga, container, false);
        adapter = new CustomListAdapterForum(getActivity(), R.layout.custom_list_feed, allForum);

        initData();
        list = (ListView) myView.findViewById(R.id.lv_feed);

        Button btn_kirim_feed = (Button) myView.findViewById(R.id.btn_kirim_feed);
        btn_kirim_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ambil id yg login
                sharedWarga user = SharedPrefManager.getInstance(getContext()).getwarga();
                final String id_warga = Integer.toString(user.getId());

                //ambil waktu sekarang
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                final String waktuSekarang = df.format(c.getTime());

                //ambil isi forum
                et_feed = (EditText) myView.findViewById(R.id.et_feed);
                final String forum = et_feed.getText().toString();

                /*POSTING KE SERVER*/
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_ADDFORUM,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    //konversi response ke jsonObject
                                    JSONObject obj = new JSONObject(response);

                                    //jika error bernilai false
                                    if (!obj.getBoolean("error")) {
                                        Toast.makeText(getContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                        et_feed.setText("");
                                    } else {
                                        Toast.makeText(getContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
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
                        }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("id_warga", id_warga);
                        params.put("forum", forum);
                        params.put("waktu", waktuSekarang);
                        return params;
                    }
                };

                VolleySingleton.getmInstance(getContext()).addToRequestQueue(stringRequest);
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
        if(id == R.id.mn_sync){
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

    public void initData(){
        ArrayList<Forum> tmp = new ArrayList<Forum>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_GETFORUM,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray forumArray = obj.getJSONArray("Forum");
                            for (int i = 0; i < forumArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject forumObject = forumArray.getJSONObject(i);
                                //creating a event object
                                Forum forum = new Forum(forumObject.getInt("id_status"), forumObject.getString("nama"), forumObject.getString("isi_status"), forumObject.getString("waktu"));
                                //Log.d("nama event", ""+event.getEvent());
                                allForum.add(forum);
                            }
                            adapter = new CustomListAdapterForum(getActivity(), R.layout.custom_list_feed, allForum);
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

}
