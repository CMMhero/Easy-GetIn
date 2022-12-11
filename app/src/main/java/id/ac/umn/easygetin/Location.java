package id.ac.umn.easygetin;

public class Location {
    double latitude, longitude;
    long jamPertama, jamBerikutnya;
    String location, name, photoUrl;

    public Location() { }

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

    public double getJamPertama() {
        return jamPertama;
    }

    public void setJamPertama(long jamPertama) {
        this.jamPertama = jamPertama;
    }

    public double getJamBerikutnya() {
        return jamBerikutnya;
    }

    public void setJamBerikutnya(long jamBerikutnya) {
        this.jamBerikutnya = jamBerikutnya;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
