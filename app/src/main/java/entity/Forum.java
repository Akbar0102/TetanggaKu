package entity;

/**
 * Created by Akbar H on 06/01/2018.
 */

public class Forum {
    int id_status;
    String nama;
    String isi_status;
    String waktu;

    public Forum(int id_status, String nama, String isi_status, String waktu) {
        this.id_status = id_status;
        this.nama = nama;
        this.isi_status = isi_status;
        this.waktu = waktu;
    }

    public int getId_status() {
        return id_status;
    }

    public void setId_status(int id_status) {
        this.id_status = id_status;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getIsi_status() {
        return isi_status;
    }

    public void setIsi_status(String isi_status) {
        this.isi_status = isi_status;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }
}
