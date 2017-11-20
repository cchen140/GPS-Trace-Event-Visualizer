package gtev.map;

import org.json.JSONObject;

/**
 * Created by cy on 11/14/2017.
 */
public class Marker {
    long timestamp;
    double lat;
    double lng;
    String label;
    int mode;

    public Marker(long timestamp, double lat, double lng, int mode, String label) {
        this.timestamp = timestamp;
        this.lat = lat;
        this.lng = lng;
        this.mode = mode;
        this.label = label;
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

    public String getLabel() {
        return label;
    }

    public JSONObject getJson() {
        JSONObject resultJson = new JSONObject();
        resultJson.put("timestamp", timestamp);
        resultJson.put("lat", lat);
        resultJson.put("lng", lng);
        resultJson.put("mode", mode);
        resultJson.put("label", label);
        return resultJson;
    }
}
