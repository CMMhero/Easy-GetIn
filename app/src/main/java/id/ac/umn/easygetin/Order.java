package id.ac.umn.easygetin;

import com.google.firebase.Timestamp;

public class Order {
    Boolean finished;
    Timestamp start, end;
    String locationName, nomorParkir;

    public Order(Boolean finished, Timestamp start, String locationName, String nomorParkir) {
        this.finished = finished;
        this.start = start;
        this.locationName = locationName;
        this.nomorParkir = nomorParkir;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getNomorParkir() {
        return nomorParkir;
    }

    public void setNomorParkir(String nomorParkir) {
        this.nomorParkir = nomorParkir;
    }
}
