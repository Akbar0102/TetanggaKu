package entity;

/**
 * Created by Akbar H on 11/11/2017.
 */

public class Warga {
    private String nama;
    private String jenis;
    private String alamat, pekerjaan;
    private String status;
    private double latitude, longitude;
    private int id;


    public Warga(String nama, String jenis, String alamat, String status, double latitude, double longitude, String pekerjaan, int id) {
        this.nama = nama;
        this.jenis = jenis;
        this.alamat = alamat;
        this.status = status;
        this.latitude = latitude;
        this.longitude = longitude;
        this.pekerjaan = pekerjaan;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getPekerjaan() {
        return pekerjaan;
    }

    public void setPekerjaan(String pekerjaan) {
        this.pekerjaan = pekerjaan;
    }
}
