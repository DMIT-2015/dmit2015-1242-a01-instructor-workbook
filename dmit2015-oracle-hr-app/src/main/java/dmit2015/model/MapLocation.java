package dmit2015.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class MapLocation {

    private String id;
    private String storeLocation;
    private double latitude;
    private double longitude;

    public MapLocation(String storeLocation, double latitude, double longitude) {
        id = UUID.randomUUID().toString();
        this.storeLocation = storeLocation;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
