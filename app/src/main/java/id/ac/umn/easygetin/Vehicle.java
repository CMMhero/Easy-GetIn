package id.ac.umn.easygetin;

public class Vehicle {
    String photo;
    String platNomor;
    String warna;
    String jenis;
    String nama;

    public Vehicle() {
    }

    public Vehicle(String nama, String platNomor, String warna, String jenis, String photo) {
        this.nama = nama;
        this.platNomor = platNomor;
        this.warna = warna;
        this.jenis = jenis;
        this.photo = photo;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPlatNomor() {
        return platNomor;
    }

    public void setPlatNomor(String platNomor) {
        this.platNomor = platNomor;
    }

    public String getWarna() {
        return warna;
    }

    public void setWarna(String warna) {
        this.warna = warna;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
