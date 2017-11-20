package gtev.servlet;

import gtev.cli.RecordReceiverSerialConnection;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by cy on 11/18/2017.
 */
public class ServletResetTraceEventRecords extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        RecordReceiverSerialConnection.recordFile.resetAll();

        System.out.println("Record file is reset.");

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JSONObject resultJson = new JSONObject();
        resultJson.put("status", "OK");
        out.print(resultJson.toString());
        out.flush();
    }
}
