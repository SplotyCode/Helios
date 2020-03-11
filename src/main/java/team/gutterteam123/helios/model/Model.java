package team.gutterteam123.helios.model;

import lombok.SneakyThrows;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import team.gutterteam123.helios.render.VAO;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Model {

    /* All the used points of the object */
    private ArrayList<Vector3f> vertices = new ArrayList<>();

    private ArrayList<Vector2f> textureVertices = new ArrayList<>();

    /* The triangles */
    private ArrayList<Face> faces = new ArrayList<>();

    private HashMap<String, Material> materials = new HashMap<>();

    private VAO vao = new VAO();

    @SneakyThrows
    public Model(File file) {
        this(file, null);
    }

    @SneakyThrows
    public Model(File file, File mtlFile) {
        if (mtlFile != null) {
            ModelReader mtlReader = new ModelReader(mtlFile);
            String mtlCommand;
            Material material = null;
            while ((mtlCommand = mtlReader.nextCommand()) != null) {
                if (mtlCommand.equals("newmtl")) {
                    material = new Material(mtlReader.getArgument(1));
                } else if (material != null) {
                    material.readCommand(mtlReader, mtlCommand);
                } else {
                    System.out.println("Unexpected command: " + mtlCommand + " expected newmtl");
                }
            }
        }

        ModelReader reader = new ModelReader(file);
        String command;
        HashSet<String> unknownCommands = new HashSet<>();
        while ((command = reader.nextCommand()) != null) {
            switch (command) {
                case "v":
                    vertices.add(reader.readVector3f());
                    break;
                case "vt":
                    textureVertices.add(reader.readVector2f());
                    break;
                case "f":
                    try {
                        Face face = new Face();
                        reader.readVertex(face, 0);
                        reader.readVertex(face, 1);
                        reader.readVertex(face, 2);
                        faces.add(face);
                    } catch (IndexOutOfBoundsException ex) {
                        System.out.println("Face with illegal vertex count: " + String.join(" ", reader.getCurrentLine()));
                        /* TODO faces with not 3 vertices! Are this lines??? */
                    }
                    break;
                default:
                    unknownCommands.add(command);
                    break;
            }
        }
        if (!unknownCommands.isEmpty()) {
            System.out.println("Unknown commands: " + String.join(", " + unknownCommands));
        }

        vao.bind();
        int[] indices = new int [numVertices()];
        float[] textureArray = new float[numVertices() * 2];
        for (int i = 0; i < faces.size(); i++) {
            Face face = faces.get(i);
            face.prepare(i * 3, indices, textureArray, textureVertices);
        }

        float[] rawPositions = new float[numVertices() * 3];
        for (int i = 0; i < vertices.size(); i++) {
            Vector3f face = vertices.get(i);
            saveVertex(rawPositions, face, i * 3);
        }

        vao.storeIndices(indices);
        vao.store(0, rawPositions, 3);
        //vao.store(1, textureArray, 2);
        vao.unbind();
    }

    public int numTriangles() {
        return faces.size();
    }

    public int numVertices() {
        return faces.size() * 3;
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
        GL11.glDrawElements(GL11.GL_TRIANGLES, numVertices(), GL11.GL_UNSIGNED_INT, 0);
        GL20.glDisableVertexAttribArray(0);
        vao.unbind();
    }

}
