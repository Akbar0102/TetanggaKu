package entity;

/**
 * Created by Akbar H on 04/12/2017.
 */

public class Event {
    private String nama;
    private String deskripsi;

    public Event(String nama, String deskripsi) {
        this.nama = nama;
        this.deskripsi = deskripsi;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String jenis) {
        this.deskripsi = jenis;
    }
}
