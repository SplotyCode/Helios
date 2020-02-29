package team.gutterteam123.helios.model;

import lombok.SneakyThrows;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import team.gutterteam123.helios.render.VAO;

import java.io.File;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;

public class Model {

    /* All the used points of the object */
    private ArrayList<Vector3f> vertices = new ArrayList<>();

    /* The triangles - not coordinates but indexes of the vertices array */
    private ArrayList<Vector3i> faces = new ArrayList<>();

    private VAO vao = new VAO();

    @SneakyThrows
    public Model(File file) {
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
        vao.bind();
        /*vao.store(0, new float[]{
                -0.5f, -0.5f, 0.0f, // left
                0.5f, -0.5f, 0.0f, // right
                0.0f,  0.5f, 0.0f  // top
        }, 3);*/
        vao.store(0, getRawData(), 3);
        vao.unbind();
    }

    public float[] getRawData() {
        float[] data = new float[faces.size() * 3 * 3];
        for (int i = 0; i < faces.size(); i++) {
            Vector3i face = faces.get(i);
            int start = i * 3 * 3;
            saveVertex(data, getVertex(face.x), start);
            saveVertex(data, getVertex(face.y), start + 3);
            saveVertex(data, getVertex(face.z), start + 6);
        }
        return data;
    }

    private void saveVertex(float[] data, Vector3f vertex, int start) {
        data[start] = vertex.x;
        data[start + 1] = vertex.y;
        data[start + 2] = vertex.z;
    }

    /* Starting at 1 */
    public Vector3f getVertex(int index) {
        return vertices.get(index - 1);
    }

    public void render() {
        GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
        //GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);

        vao.bind();
        GL20.glEnableVertexAttribArray(0);
        glDrawArrays(GL_TRIANGLES, 0, 3 * faces.size());
        GL20.glDisableVertexAttribArray(0);
        vao.unbind();
    }

}
