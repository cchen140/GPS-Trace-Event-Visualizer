package gtev.servlet;

import gtev.TraceEventRecordParser;
import gtev.cli.RecordReceiverSerialConnection;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by cy on 11/14/2017.
 */
public class ServletGetNewData extends HttpServlet {
    final String RECORD_FOLDER_PATH = "/resources/records";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        long requiredTimestamp = Long.valueOf(request.getParameter("timestamp"));
        String absoluteRecordFolderPath = getServletContext().getRealPath(RECORD_FOLDER_PATH);

        TraceEventRecordParser traceEventRecordParser = new TraceEventRecordParser();
//        TraceEventRecordParser traceEventRecordParser = new TraceEventRecordParser(absoluteRecordFolderPath);

        JSONArray jsonArrayMarkers = traceEventRecordParser.getJSONArrayMarkersAfterTimestamp(requiredTimestamp);
        JSONArray jsonArrayPaths = traceEventRecordParser.getSJONArrayWaypointsAfterTimestamp(requiredTimestamp);

//        JSONArray jsonArrayMarkers = new JSONArray();
//        JSONArray jsonArrayPaths = new JSONArray();
//
//        /* Markers */
//        for (int i=0; i<5; i++) {
//            JSONObject newJsonObjectMarker = new JSONObject();//"{\"name\": \"John\", \"aa\":\"Kally\"}");
//            //newJsonObjectMarker.put("id", i);
//            newJsonObjectMarker.put("lat", -12.044012922866312+i);
//            newJsonObjectMarker.put("lng", -77.02470665341184+i);
//            jsonArrayMarkers.put(newJsonObjectMarker);
//        }
//
//        /* Paths */
//        for (int i=0; i<5; i++) {
//            JSONObject newJsonObjectPath = new JSONObject();//"{\"name\": \"John\", \"aa\":\"Kally\"}");
//            newJsonObjectPath.put("lat", -12.044012922866312-i);
//            newJsonObjectPath.put("lng", -77.02470665341184-i);
//            jsonArrayPaths.put(newJsonObjectPath);
//        }

        /* Combine them all */
        JSONObject jsonObjectResult = new JSONObject();
        jsonObjectResult.put("markers", jsonArrayMarkers);
        jsonObjectResult.put("paths", jsonArrayPaths);

        //System.out.println(obj.getString("name")); //John

        response.setContentType("application/json");
// Get the printwriter object from response to write the required json object to the output stream
        PrintWriter out = response.getWriter();
// Assuming your json object is **jsonObject**, perform the following, it will return your json object
        out.print(jsonObjectResult.toString());
        out.flush();
    }
}
