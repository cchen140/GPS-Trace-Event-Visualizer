package gtev;

import cy.utility.file.FileHandler;
import cy.utility.file.FileUtility;
import gtev.map.Marker;
import gtev.map.Waypoint;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by cy on 11/14/2017.
 */
public class TraceEventRecordParser extends FileHandler {
    String recordFolderPath;

    BufferedReader recordFileReader;

    ArrayList<Marker> markers = new ArrayList();
    ArrayList<Waypoint> waypoints = new ArrayList<>();

    public TraceEventRecordParser(String recordFolderPath) {
        this.recordFolderPath = recordFolderPath;

        System.out.println(recordFolderPath);

        File recordFile = FileUtility.getTheNewestFile(this.recordFolderPath, "txt");
        this.recordFileReader = openFile(recordFile.getPath());

        //System.out.println(recordFile.getPath());

        parseAllLines();
    }

    public void parseAllLines() {
        markers.clear();
        waypoints.clear();

        String thisLine;

        try {
            while ((thisLine = recordFileReader.readLine()) != null) {
                String columnStrings[] = thisLine.split(";", 0);

                int typeId = Integer.valueOf(columnStrings[1].trim());

                if (typeId == 1) {
                    // Markers
                    Marker newMarker = new Marker(
                            Long.valueOf(columnStrings[0].trim()),
                            Double.valueOf(columnStrings[4].trim()),
                            Double.valueOf(columnStrings[5].trim()),
                            columnStrings[6].trim()
                    );
                    markers.add(newMarker);
                } else if (typeId == 2) {
                    // Waypoint
                    Waypoint newWaypoint = new Waypoint(
                            Long.valueOf(columnStrings[0].trim()),
                            Double.valueOf(columnStrings[4].trim()),
                            Double.valueOf(columnStrings[5].trim())
                    );
                    waypoints.add(newWaypoint);
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    public JSONArray getJSONArrayMarkersAfterTimestamp(long timestamp) {
        JSONArray resultJsonArray = new JSONArray();
        for (Marker thisMarker :
                markers) {
            if (thisMarker.getTimestamp() >= timestamp) {
                resultJsonArray.put(thisMarker.getJson());
            } else {
                break;
            }
        }
        return resultJsonArray;
    }

    public JSONArray getSJONArrayWaypointsAfterTimestamp(long timestamp) {
        JSONArray resultJsonArray = new JSONArray();
        for (Waypoint thisWaypoint :
                waypoints) {
            if (thisWaypoint.getTimestamp() >= timestamp) {
                resultJsonArray.put(thisWaypoint.getJson());
            } else {
                break;
            }
        }
        return resultJsonArray;
    }
}
