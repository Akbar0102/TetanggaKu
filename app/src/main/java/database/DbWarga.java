package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import entity.Warga;

/**
 * Created by ELPHAIM on 15/11/2017.
 */

public class DbWarga {

    private SQLiteDatabase db;
    private final OpenHelper dbHelper;

    public DbWarga(Context c) {

        dbHelper =  new OpenHelper(c);
    }

    public void open() {

        db = dbHelper.getWritableDatabase();
    }

    public void close() {

        db.close();
    }

    public long insertWarga(String nama, String nik,String alamat, String jenis_kelamin, String pekerjaan, String jenis_perangkat, String status, double latitude, double longitude, int id) {
        ContentValues newValues = new ContentValues();
        newValues.put("ID_WARGA", id);
        newValues.put("NIK",nik);
        newValues.put("NAMA", nama);
        newValues.put("JENIS_KELAMIN",jenis_kelamin);
        newValues.put("ALAMAT",alamat);
        newValues.put("PEKERJAAN",pekerjaan);
        newValues.put("JENIS_PERANGKAT",jenis_perangkat);
        newValues.put("STATUS",status);
        newValues.put("LATITUDE",latitude);
        newValues.put("LONGITUDE",longitude);
        return db.insert("TABLE_WARGA", null, newValues);
    }

    public boolean getWargaLogin(String email, String pass) {
        Cursor cur = null;
        boolean hasil=false;
        String selectQuery="SELECT * FROM TABLE_WARGA WHERE EMAIL='"+email+"' AND PASSWORD='"+pass+"'";

        cur = db.rawQuery(selectQuery,null);

        if (cur.getCount()>0) {  //ada data? ambil
            hasil=true;
        }
        cur.close();
        return hasil;
    }


    public void removeAll() {
        // db.delete(String tableName, String whereClause, String[] whereArgs);
        // If whereClause is null, it will delete all rows.
        SQLiteDatabase db = dbHelper.getWritableDatabase(); // helper is object extends SQLiteOpenHelper
        db.delete("TABLE_WARGA", null, null);
    }

    public ArrayList<Warga> getAllWarga() {
        Cursor cur = null;
        ArrayList<Warga> out = new ArrayList<>();
        cur = db.rawQuery("SELECT * FROM table_warga", null);
        if (cur.moveToFirst()) {
            do {
                Warga wrg = new Warga(cur.getString(2), cur.getString(5), cur.getString(4),"Ada di rumah", cur.getDouble(8), cur.getDouble(9), cur.getString(6), cur.getInt(0));

                out.add(wrg);
            } while (cur.moveToNext());
        }
        cur.close();
        return out;
    }

}



