package entity;

/**
 * Created by Akbar H on 28/11/2017.
 */

public class sharedWarga {
    private String nama;
    private String jenis;
    private String alamat;
    private String status;

    public sharedWarga(String nama, String jenis, String alamat, String status) {
        this.nama = nama;
        this.jenis = jenis;
        this.alamat = alamat;
        this.status = status;
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
}
