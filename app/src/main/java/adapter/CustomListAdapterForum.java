package adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import entity.Forum;
import entity.URLs;

/**
 * Created by Akbar H on 06/01/2018.
 */

public class CustomListAdapterForum extends ArrayAdapter<Forum> {
    private Context context;
    private ArrayList<Forum> forum;

    public CustomListAdapterForum(Context context, int resource, ArrayList<Forum> forum){
        super(context, resource, forum);
        this.forum = forum;
        this.context = context;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent){
        //mengkonversi (array ke view), mylist agar bisa dipakai di listview menggunakan inflater
        LayoutInflater inflater = LayoutInflater.from(this.context);
        //memakai view, pilih layoutnya yang mau dikonversi
        View rowView = inflater.inflate(R.layout.custom_list_feed, null, true);

        TextView txtNama = (TextView) rowView.findViewById(R.id.tv_feed_nama);
        TextView txtWaktu = (TextView) rowView.findViewById(R.id.tv_feed_waktu);
        TextView txtStatus = (TextView) rowView.findViewById(R.id.tv_feed_status);

        //set isi textview
        txtNama.setText(forum.get(position).getNama());
        txtWaktu.setText(forum.get(position).getWaktu());
        txtStatus.setText(forum.get(position).getIsi_status());

        Button btn_komen = (Button) rowView.findViewById(R.id.btn_komen);
        btn_komen.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                final String id_status = String.valueOf(forum.get(position).getId_status());
                Log.d("id_status", ""+id_status);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_GETSELECTEDFORUM,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject obj = new JSONObject(response);
                                    JSONArray komenArray = obj.getJSONArray("Komen");
                                    for (int i = 0; i < komenArray.length(); i++) {
                                        //getting the json object of the particular index inside the array
                                        JSONObject komenObject = komenArray.getJSONObject(i);
                                        Log.d("komen", ""+komenObject.getString("komentar"));
                                        Log.d("nama", ""+komenObject.getString("nama"));
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
                        params.put("id_status", id_status);
                        return params;
                    }
                };

                VolleySingleton.getmInstance(getContext()).addToRequestQueue(stringRequest);
            }
        });

        return rowView;
    }

    public ArrayList<Forum> getList(){
        return forum;
    }
}
