package team.gutterteam123.helios.model;

import org.joml.Vector3f;
import org.joml.Vector3i;
import org.lwjgl.opengl.GL11;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Model {

    /* All the used points of the object */
    private ArrayList<Vector3f> vertices = new ArrayList<>();

    /* The triangles - not coordinates but indexes of the vertices array */
    private ArrayList<Vector3i> faces = new ArrayList<>();

    public Model(File file) throws IOException {
        ModelReader reader = new ModelReader(file);
        String command;
        while ((command = reader.nextCommand()) != null) {
            if (command.startsWith("#")) continue;
            switch (command) {
                case "v":
                    vertices.add(reader.readVector3f());
                    break;
                case "f":
                    try {
                        faces.add(reader.readVector3i());
                    } catch (IndexOutOfBoundsException ex) {
                        /* TODO faces with not 3 vertices! Are this lines??? */
                    }
                    break;
                default:
                    System.out.println("Unknown command: " + command);
            }
        }
    }

    /* Starting at 1 */
    public Vector3f getVertex(int index) {
        return vertices.get(index - 1);
    }

    public void render() {
        GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
        //GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);

        GL11.glBegin(GL11.GL_TRIANGLES);
        for (Vector3i face : faces) {
            Vector3f one = getVertex(face.x);
            GL11.glVertex3f(one.x, one.y, one.z);

            Vector3f two = getVertex(face.y);
            GL11.glVertex3f(two.x, two.y, two.z);

            Vector3f three = getVertex(face.z);
            GL11.glVertex3f(three.x, three.y, three.z);
        }
        GL11.glEnd();
    }

}
