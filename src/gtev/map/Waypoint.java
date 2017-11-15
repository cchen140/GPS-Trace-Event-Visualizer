package gtev.map;

import org.json.JSONObject;

/**
 * Created by cy on 11/14/2017.
 */
public class Waypoint {
    long timestamp;
    double lat;
    double lng;

    public Waypoint(long timestamp, double lat, double lng) {
        this.timestamp = timestamp;
        this.lat = lat;
        this.lng = lng;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public JSONObject getJson() {
        JSONObject resultJson = new JSONObject();
        resultJson.put("timestamp", timestamp);
        resultJson.put("lat", lat);
        resultJson.put("lng", lng);
        return resultJson;
    }
}
