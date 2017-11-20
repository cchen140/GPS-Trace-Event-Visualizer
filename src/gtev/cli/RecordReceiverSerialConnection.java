package gtev.cli;


import cy.utility.connect.SerialConnection;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import java.util.ArrayList;

/**
 * Created by cy on 11/14/2017.
 */
public class RecordReceiverSerialConnection  extends Thread implements HttpSessionBindingListener {
    final static String RECORD_FOLDER_PATH = "\\resources\\records";//"\\web\\resources\\records";
    String absoluteRootPath = "C:\\Users\\cy\\Documents\\myProgram\\Java\\GPS-Trace-Event-Visualizer\\web";

//    public RecordReceiverSerialConnection(String absoluteRootPath) {
//        this.absoluteRootPath = absoluteRootPath;
//    }

    public static VirtualRecordFile recordFile = new VirtualRecordFile();

    public void run() {
        //String absolutePathRecordFolder = Sys.getProgramRootPath() + RECORD_FOLDER_PATH;
        String absolutePathRecordFolder = absoluteRootPath + RECORD_FOLDER_PATH;
        System.out.println(absolutePathRecordFolder);

        //FileHandler recordFile = new FileHandler();
        //BufferedWriter recordFile = recordFile.openToWriteFile(absolutePathRecordFolder + File.separator + Sys.currentTimeString() + ".txt");
        //BufferedWriter recordFile = recordFile.openToWriteFile(absolutePathRecordFolder + File.separator + "1.txt");

        //File recordFile = cy.utility.file.FileUtility.getTheNewestFile(absolutePathRecordFolder, "txt");
        //System.out.println(recordFile.getAbsolutePath());

        SerialConnection serialConnection = null;
        try {
            ArrayList<String> serialPortList = SerialConnection.getSerialPorts();
            System.out.println("Available ports: " + serialPortList.toString());

            serialConnection = new SerialConnection("COM6");
            System.out.println("Successfully open port \"" + serialConnection.getPortName() + "\"");

            System.out.println("Begin to listen incoming messages...");

            // Write an empty line to flush file.
            recordFile.append(System.lineSeparator());
            recordFile.flush();

            while (true) {
                if (serialConnection.isStringReady()) {
                    String incomingString = serialConnection.readNextString();
                    recordFile.append(incomingString + System.lineSeparator());
                    recordFile.flush();

                    System.out.println(incomingString);
                }

                if (isInterrupted()) return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            serialConnection.close();

        }

    }

    public void valueBound(HttpSessionBindingEvent event) {
        start(); // Will instantly be started when doing session.setAttribute("task", new Task());
    }

    public void valueUnbound(HttpSessionBindingEvent event) {
        interrupt(); // Will signal interrupt when session expires.
    }


//    public static void main(String[] args) {
//        String absolutePathRecordFolder = Sys.getProgramRootPath() + RECORD_FOLDER_PATH;
//        //System.out.println(absolutePathRecordFolder);
//
//        FileHandler recordFile = new FileHandler();
//        BufferedWriter recordFile = recordFile.openToWriteFile(absolutePathRecordFolder + File.separator + Sys.currentTimeString() + ".txt");
//
//        //File recordFile = cy.utility.file.FileUtility.getTheNewestFile(absolutePathRecordFolder, "txt");
//        //System.out.println(recordFile.getAbsolutePath());
//
//        SerialConnection serialConnection = null;
//        try {
//            ArrayList<String> serialPortList = SerialConnection.getSerialPorts();
//            System.out.println("Available ports: " + serialPortList.toString());
//
//            serialConnection = new SerialConnection("COM6");
//            System.out.println("Successfully open port \"" + serialConnection.getPortName() + "\"");
//
//            System.out.println("Begin to listen incoming messages...");
//
//            // Write an empty line to flush file.
//            recordFile.append(System.lineSeparator());
//            recordFile.flush();
//
//            while (true) {
//                if (serialConnection.isStringReady()) {
//                    String incomingString = serialConnection.readNextString();
//                    recordFile.append(incomingString + System.lineSeparator());
//                    recordFile.flush();
//
//                    System.out.println(incomingString);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            serialConnection.close();
//
//        }
//
//    }
}
