package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ELPHAIM on 15/11/2017.
 */

public class OpenHelper extends SQLiteOpenHelper {

    //kalau ada upgrage, increment versi database
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "dbWarga.db";

    public static final String TABLE_WARGA =
            "CREATE TABLE `table_warga` ( `id_warga` INTEGER PRIMARY KEY AUTOINCREMENT, `nik` TEXT," +
            "`nama` TEXT, `jenis_kelamin` TEXT, `alamat` TEXT, `pekerjaan` TEXT, `jenis_perangkat` TEXT," +
            "`status` TEXT, `latitude` REAL, `longitude` REAL )";
    public static final String TABLE_USER =
            "CREATE TABLE \"table_user\" ( `id_user` INTEGER PRIMARY KEY AUTOINCREMENT," +
            "`email` TEXT, `password` TEXT, FOREIGN KEY(`id_user`) REFERENCES `table_warga`(`id_warga`) ON DELETE SET NULL )";
    public static final String TABLE_USAHA_WARGA =
            "CREATE TABLE `table_usaha_warga` ( `id` INTEGER PRIMARY KEY AUTOINCREMENT, `nama_usaha` TEXT,"+
            "`nama_pemilik` TEXT, `alamat` TEXT, `latitude` REAL, `longitude` REAL )";
    public static final String TABLE_POI =
            "CREATE TABLE `table_poi` ( `id` INTEGER PRIMARY KEY AUTOINCREMENT, `nama_tempat` TEXT, `alamat` TEXT, `tipe_tempat` TEXT, `latitude` REAL, `longitude` REAL )";
    public static final String TABLE_FORUM =
            "CREATE TABLE \"table_forum\" ( `id_status` INTEGER PRIMARY KEY AUTOINCREMENT, `id_warga` INTEGER," +
            "`isi_status` TEXT, `waktu` TEXT, `gambar` BLOB, `lokasi` TEXT, FOREIGN KEY(`id_warga`) REFERENCES `table_warga`(`id_warga`) ON DELETE SET NULL )";
    public static final String TABLE_EVENT =
            "CREATE TABLE \"table_event\" ( `id_event` INTEGER PRIMARY KEY AUTOINCREMENT, `nama_event` TEXT," +
            "`alamat` TEXT, `tanggal_mulai` INTEGER, `tanggal_selesai` INTEGER, `deskripsi` TEXT, `id_warga` INTEGER," +
            "`latitude` REAL, `longitude` REAL, FOREIGN KEY(`id_warga`) REFERENCES `table_warga`(`id_warga`) ON DELETE SET NULL )";
    public static final String TABLE_KOMENTAR = "CREATE TABLE `table_komentar` ( `id_komen` INTEGER PRIMARY KEY AUTOINCREMENT,"+
            "`id_status` INTEGER, `komentar` TEXT, `id_warga` INTEGER, FOREIGN KEY(`id_status`) REFERENCES `table_forum`(`id_status`) ON DELETE SET NULL ON UPDATE CASCADE, FOREIGN KEY(`id_warga`) REFERENCES `table_warga`(`id_warga`) ON DELETE SET NULL ON UPDATE CASCADE )";

    public OpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create database
        db.execSQL(TABLE_WARGA);
        db.execSQL(TABLE_USER);
        db.execSQL(TABLE_USAHA_WARGA);
        db.execSQL(TABLE_POI);
        db.execSQL(TABLE_FORUM);
        db.execSQL(TABLE_EVENT);
        db.execSQL(TABLE_KOMENTAR);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //jika app diupgrade (diinstall yang baru) maka database akan dicreate ulang (data hilang)
        //jika tidak tidak ingin hilang, bisa diproses disini
        db.execSQL("DROP TABLE IF EXISTS TABLE_WARGA");
        db.execSQL("DROP TABLE IF EXISTS TABLE_USER");
        db.execSQL("DROP TABLE IF EXISTS TABLE_USAHA_WARGA");
        db.execSQL("DROP TABLE IF EXISTS TABLE_POI");
        db.execSQL("DROP TABLE IF EXISTS TABLE_FORUM");
        db.execSQL("DROP TABLE IF EXISTS TABLE_EVENT");
        db.execSQL("DROP TABLE IF EXISTS TABLE_KOMENTAR");
        onCreate(db);
    }
}
