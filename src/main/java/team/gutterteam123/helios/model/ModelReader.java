package team.gutterteam123.helios.model;

import lombok.Getter;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector3i;

import java.io.*;

@Getter
public class ModelReader {

    private BufferedReader reader;
    private String[] currentLine;

    public ModelReader(File file) throws FileNotFoundException {
        reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
    }

    public String nextCommand() throws IOException {
        String fullLine = reader.readLine();
        if (fullLine == null) {
            return null;
        }
        currentLine = fullLine.split(" ");
        return currentLine[0];
    }

    public Vector2f readVector2f() {
        return new Vector2f(
                Float.valueOf(currentLine[1]), /* x */
                Float.valueOf(currentLine[2])  /* y */
        );
    }

    public Vector3f readVector3f() {
        return new Vector3f(
                Float.valueOf(currentLine[1]), /* x */
                Float.valueOf(currentLine[2]), /* y */
                Float.valueOf(currentLine[3])  /* z */
        );
    }

    public Vector3i readVector3i() {
        return new Vector3i(
                Integer.valueOf(currentLine[1].split("/")[0]), /* x */
                Integer.valueOf(currentLine[2].split("/")[0]), /* y */
                Integer.valueOf(currentLine[3].split("/")[0])  /* z */
        );
    }

    /*
     * Possible formats
     * v = vertex, t = texture, n = normal
     *
     * f v v v
     * f v/t v/t v/t
     * f v//n v//n v//n
     * f v/t/n v/t/n v/t/n
     */
    public void readVertex(Face face, int index) {
        String[] vertexParts = currentLine[index + 1].split("/");
        int partSize = vertexParts.length;

        face.getPosition().setComponent(index, Integer.parseInt(vertexParts[0]));

        String texture;
        if (partSize > 1 && !(texture = vertexParts[1]).isEmpty()) {
            face.getTexture().setComponent(index, Integer.parseInt(texture));
        }
        //TODO normals???
    }

}
