package id.ac.umn.easygetin;

public class Location {
    double latitude, longitude, jamPertama, jamBerikutnya;
    String location, mapsUrl, name, photoUrl;

    public Location() { }
//    public Location(String name, String location, String mapsUrl, String photoUrl, double latitude, double longitude, double price) {
//        this.name = name;
//        this.location = location;
//        this.mapsUrl = mapsUrl;
//        this.photoUrl = photoUrl;
//        this.latitude = latitude;
//        this.longitude = longitude;
//        this.price = price;
//    }
    public Location(String name, String location) {
        this.name = name;
        this.location = location;
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

    public double getJamPertama() {
        return jamPertama;
    }

    public void setJamPertama(double jamPertama) {
        this.jamPertama = jamPertama;
    }

    public double getJamBerikutnya() {
        return jamBerikutnya;
    }

    public void setJamBerikutnya(double jamBerikutnya) {
        this.jamBerikutnya = jamBerikutnya;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMapsUrl() {
        return mapsUrl;
    }

    public void setMapsUrl(String mapsUrl) {
        this.mapsUrl = mapsUrl;
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
