package component.lokasi_penting;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.android.tetanggaku.DetailMaps;
import com.example.android.tetanggaku.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import adapter.CustomInfoWindowAdapter;
import database.DbWarga;
import entity.Warga;

/**
 * Created by Akbar H on 29/11/2017.
 */

public class LokasiTetangga extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private static final String TAG = MapFragment.class.getSimpleName();
    private static final int MY_PERMISSIONS_REQUEST = 99;//int bebas, maks 1 byte
    public final static String EXTRA_MESSAGE ="edu.upi.cs.yudiwbs.app1.MESSAGE";
    GoogleApiClient mGoogleApiClient ;
    Location mLastLocation;
    LocationRequest mLocationRequest;
    private GoogleMap mGoogleMap;
    private MapView mMapView;
    private Marker mPosSekarang;
    private List<Marker> marker_rumah = new ArrayList<Marker>();
    private Circle mCircle;
    private LatLng latLng;
    private LatLng rumahWarga;
    Warga yourMarkerTag;
    DbWarga db;
    private ArrayList<Warga> allWarga = new ArrayList<Warga>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sekitarku);

        db = new DbWarga(getApplicationContext());
        db.open();

        mMapView = (MapView)findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();

        buildGoogleApiClient();
        createLocationRequest();
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        //10 detik sekali minta lokasi (10000ms = 10 detik)
        mLocationRequest.setInterval(10000);
        //tapi tidak boleh lebih cepat dari 5 detik
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    public void ambilLokasi() {
    /* mulai Android 6 (API 23), pemberian persmission
       dilakukan secara dinamik (tdk diawal)
       untuk jenis2 persmisson tertentu, termasuk lokasi
    */

        // cek apakah sudah diijinkan oleh user, jika belum tampilkan dialog
        // pastikan permission yg diminta cocok dgn manifest
        if (ActivityCompat.checkSelfPermission (this,android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED ) {
            //belum ada ijin, tampilkan dialog
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST);
            return;
        }

        /*Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location == null)
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        else
            handleNewLocation(location);*/

        //ambil lokasi terakhir
        //setiap ada update maka onLocationChanged akan dipanggil
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                ambilLokasi();
            } else {
                //permssion tidak diberikan, tampilkan pesan
                AlertDialog ad = new AlertDialog.Builder(this).create();
                ad.setMessage("Tidak mendapat ijin, tidak dapat mengambil lokasi");
                ad.show();
            }
            return;
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        ambilLokasi();
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
        mMapView.onResume();
        setUpMap();
    }

    @Override
    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void handleNewLocation(Location location) {
        latLng = new LatLng(location.getLatitude(), location.getLongitude());
        int strokeColor = 0xffff0000; //red outline
        int shadeColor = 0x44ff0000; //opaque red fill

        if(mPosSekarang == null ){
            mPosSekarang = mGoogleMap.addMarker(new MarkerOptions().position(latLng)
                    .title("Lokasi saat ini")
                    .snippet("disini")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            mCircle = mGoogleMap.addCircle(new CircleOptions().center(latLng)
                    .radius(50).fillColor(shadeColor).strokeColor(strokeColor).strokeWidth(8));
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
        }
        else {
            mPosSekarang.setPosition(latLng);
            mCircle.setCenter(latLng);
        }

        // mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
        Log.d("latitude:", String.valueOf(location.getLatitude()));
    }

    @Override
    public void onLocationChanged(Location location) {
        handleNewLocation(location);
        //cek_radius(location, mCircle, marker_rumah);
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    public void setUpMap() {
        if (mGoogleMap == null)
            mMapView.getMapAsync(new OnMapReadyCallback() {

                @Override
                public void onMapReady(GoogleMap googleMap) {
                    mGoogleMap = googleMap;
                    allWarga = db.getAllWarga();
                    for(int i=0; i<allWarga.size(); i++){
                        rumahWarga = new LatLng(allWarga.get(i).getLatitude(), allWarga.get(i).getLongitude());
                        String snippet = "Alamat: "+allWarga.get(i).getAlamat()+"\n"+
                                "Jenis: "+allWarga.get(i).getJenis()+"\n"+
                                "Pekerjaan: "+allWarga.get(i).getPekerjaan()+"\n";
                        Marker marker = mGoogleMap.addMarker(new MarkerOptions().position(rumahWarga)
                                .title(allWarga.get(i).getNama())
                                .snippet(snippet)
                                .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.lokasi_tetangga)));
                        marker.setTag(allWarga.get(i).getJenis());
                        marker_rumah.add(marker);
                    }
                    mGoogleMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(getApplicationContext()));
                    mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(rumahWarga, 17));

                    mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick(Marker marker) {
                            Intent intent = new Intent(LokasiTetangga.this, DetailMaps.class);
                            intent.putExtra("snippet", marker.getSnippet());
                            intent.putExtra("nama", marker.getTitle());

                            // Starting the  Activity
                            startActivity(intent);
                        }
                    });

                    try {
                        // Customise the styling of the base map using a JSON object defined
                        // in a raw resource file.
                        boolean success = googleMap.setMapStyle(
                                MapStyleOptions.loadRawResourceStyle(
                                        getApplicationContext(), R.raw.style_json));

                        if (!success) {
                            Log.e(TAG, "Style parsing failed.");
                        }
                    } catch (Resources.NotFoundException e) {
                        Log.e(TAG, "Can't find style. Error: ", e);
                    }

                    /*StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_GETWARGA,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject obj = new JSONObject(response);
                                        JSONArray wargaArray = obj.getJSONArray("Warga");
                                        for (int i = 0; i < wargaArray.length(); i++) {
                                            //getting the json object of the particular index inside the array
                                            JSONObject wargaObject = wargaArray.getJSONObject(i);

                                            //marker rumah warga
                                            rumahWarga = new LatLng(wargaObject.getDouble("latitude"), wargaObject.getDouble("longitude"));
                                            Marker marker = mGoogleMap.addMarker(new MarkerOptions().position(rumahWarga)
                                                    .title(wargaObject.getString("nama"))
                                                    .snippet(wargaObject.getString("alamat"))
                                                    .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.lokasi_tetangga)));
                                            marker_rumah.add(marker);
                                        }
                                        //Log.d("jumlah rumah warga", ""+marker_rumah.size());
                                        mGoogleMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(getApplicationContext()));
                                        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(rumahWarga, 17));

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                    VolleySingleton.getmInstance(getApplicationContext()).addToRequestQueue(stringRequest);*/


                }
            });
    }

    public void cek_radius(Location location, Circle mCircle, List<Marker> marker_rumah){
        latLng = new LatLng(location.getLatitude(), location.getLongitude());
        float[] distance = new float[2];

        for(int i=0; i<marker_rumah.size(); i++){
            Location.distanceBetween(marker_rumah.get(i).getPosition().latitude, marker_rumah.get(i).getPosition().longitude, mCircle.getCenter().latitude, mCircle.getCenter().longitude, distance);
            if (distance[0] <= mCircle.getRadius()) {
                Log.d("radius",""+mCircle.getRadius());
                marker_rumah.get(i).setVisible(true);
            }
            else {
                marker_rumah.get(i).setVisible(false);
            }
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}
