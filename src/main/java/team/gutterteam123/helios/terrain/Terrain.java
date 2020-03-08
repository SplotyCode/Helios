package team.gutterteam123.helios.terrain;

import lombok.SneakyThrows;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import team.gutterteam123.helios.Helios;
import team.gutterteam123.helios.render.VAO;

import java.util.Random;

public class Terrain {

    private static final int MAP_SIZE = 3;
    private static final int INDICES_SIZE = (MAP_SIZE * 2 + 2) * MAP_SIZE + ((MAP_SIZE - 1) * 2);
    private static final int VERTICES_SIZE = 3 * (MAP_SIZE + 1) * (MAP_SIZE + 1);

    private TerrainShader shader = new TerrainShader();

    private VAO vao = new VAO();

    private HeightGenerator heightGenerator = new HeightGenerator(new Random());

    public Terrain() {
        createMesh();

        shader.start();
        shader.storeStaticColor(new Vector4f(0, 1, 0, 1));
        shader.stop();
    }

    @SneakyThrows
    private void createMesh() {
        float[] vertices = new float[VERTICES_SIZE];
        int[] indices = new int[INDICES_SIZE];

        int vertexPointer = 0;
        for (int z = 0; z < MAP_SIZE + 1; z++) {
            for (int x = 0; x < MAP_SIZE + 1; x++) {
                System.out.println(vertexPointer + " " + x + " " + z);
                vertices[vertexPointer * 3] = x;
                vertices[vertexPointer * 3 + 1] = 0;//(float) heightGenerator.noise(z / 20f, x / 20f) * 12;
                vertices[vertexPointer * 3 + 2] = z;
                vertexPointer++;
            }
        }
        System.out.println("a " + (vertexPointer - 1) + " " + VERTICES_SIZE / 3);
        int pointer = 0;
        for (int z = 0; z < MAP_SIZE; z++) {
            for (int x = 0; x < MAP_SIZE + 1; x++) {
                int topLeft = (z * MAP_SIZE + 1) + x;
                indices[pointer++] = topLeft;
                indices[pointer++] = topLeft + MAP_SIZE + 1;
            }
            if(z < MAP_SIZE - 1) {
                indices[pointer++] = (z + 1) * (MAP_SIZE + 1) + (MAP_SIZE);
                indices[pointer++] = (z + 1) * (MAP_SIZE + 1);
            }
        }
        vao.bind();
        vao.storeIndices(indices);
        vao.store(0, vertices, 3);
        vao.unbind();
    }

    public void render() {
        shader.start();
        shader.storeProjectionMatrix(Helios.getInstance().getWorld().getProjectionMatrix());
        shader.storeViewMatrix(Helios.getInstance().getWorld().getCamera().calculateViewMatrix());
        vao.bind();
        GL20.glEnableVertexAttribArray(0);
        GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
        GL11.glDrawElements(GL11.GL_TRIANGLE_STRIP, INDICES_SIZE, GL11.GL_UNSIGNED_INT, 0);
        GL20.glDisableVertexAttribArray(0);
        vao.unbind();
        shader.stop();
    }

    public void cleanup() {
        shader.cleanUp();
    }
}
