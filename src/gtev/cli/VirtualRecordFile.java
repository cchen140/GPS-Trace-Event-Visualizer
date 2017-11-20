package gtev.cli;

import java.util.ArrayList;

/**
 * Created by cy on 11/15/2017.
 */
public class VirtualRecordFile {
    ArrayList<String> lines = new ArrayList<>();

    int readLinePointer = 0;

    public void append(String line) {
        lines.add(line);
    }

    public void resetReadPointer() {
        readLinePointer = 0;
    }

    public String readLine() {
        if (readLinePointer >= lines.size()) {
            return null;
        }

        String result = lines.get(readLinePointer);
        readLinePointer++;
        return result;
    }

    public void flush() {
        ;
    }

    public void resetAll() {
        lines.clear();
        resetReadPointer();
    }
}
