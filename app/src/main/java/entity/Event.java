package entity;

/**
 * Created by Akbar H on 04/12/2017.
 */

public class Event {
    private String nama;
    private String deskripsi;
    private String event;
    private String waktu;
    private String lokasi;

    public Event(String nama, String deskripsi, String event, String waktu, String lokasi) {
        this.nama = nama;
        this.deskripsi = deskripsi;
        this.event = event;
        this.waktu = waktu;
        this.lokasi = lokasi;
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

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }
}
