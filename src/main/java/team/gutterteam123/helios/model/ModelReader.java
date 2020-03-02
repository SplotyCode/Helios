package team.gutterteam123.helios.model;

import org.joml.Vector3f;
import org.joml.Vector3i;

import java.io.*;

public class ModelReader {

    private BufferedReader reader;
    private String[] line;

    public ModelReader(File file) throws FileNotFoundException {
        reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
    }

    public String nextCommand() throws IOException {
        String fullLine = reader.readLine();
        if (fullLine == null) {
            return null;
        }
        line = fullLine.split(" ");
        return line[0];
    }

    public Vector3f readVector3f() {
        return new Vector3f(
                Float.valueOf(line[1]), /* x */
                Float.valueOf(line[2]), /* y */
                Float.valueOf(line[3])  /* z */
        );
    }

    public Vector3i readVector3i() {
        return new Vector3i(
                Integer.valueOf(line[1].split("/")[0]), /* x */
                Integer.valueOf(line[2].split("/")[0]), /* y */
                Integer.valueOf(line[3].split("/")[0])  /* z */
        );
    }

}
