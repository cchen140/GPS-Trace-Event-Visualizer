package gtev;

import cy.utility.file.FileHandler;
import gtev.cli.RecordReceiverSerialConnection;
import gtev.cli.VirtualRecordFile;
import gtev.map.Marker;
import gtev.map.Waypoint;
import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by cy on 11/14/2017.
 */
public class TraceEventRecordParser extends FileHandler {
    String recordFolderPath;

    //BufferedReader recordFileReader;
    VirtualRecordFile recordFileReader;

    ArrayList<Marker> markers = new ArrayList();
    ArrayList<Waypoint> waypoints = new ArrayList<>();

    public TraceEventRecordParser() {
//    public TraceEventRecordParser(String recordFolderPath) {
//        this.recordFolderPath = recordFolderPath;
//
//        File recordFile = FileUtility.getTheNewestFile(this.recordFolderPath, "txt");
//        this.recordFileReader = openFile(recordFile.getPath());

        recordFileReader = RecordReceiverSerialConnection.recordFile;

        parseAllLines();
    }

    public void parseAllLines() {
        markers.clear();
        waypoints.clear();

        String thisLine;

        try {
            recordFileReader.resetReadPointer();
            while ((thisLine = recordFileReader.readLine()) != null) {
                if (thisLine.trim().equalsIgnoreCase("")) {
                    // This line is empty.
                    continue;
                } else if (thisLine.substring(0,1).equalsIgnoreCase("#")) {
                    // This line is commented.
                    continue;
                }

                String columnStrings[] = thisLine.split(";", 0);

                if (columnStrings.length == 1) {
                    continue;
                }

                int typeId = Integer.valueOf(columnStrings[1].trim());

                if (typeId == 1) {
                    // Markers
                    Marker newMarker = new Marker(
                            Long.valueOf(columnStrings[0].trim()),
                            Double.valueOf(columnStrings[4].trim()),
                            Double.valueOf(columnStrings[5].trim()),
                            Integer.valueOf(columnStrings[2].trim()),
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
            }
        }
        return resultJsonArray;
    }
}
