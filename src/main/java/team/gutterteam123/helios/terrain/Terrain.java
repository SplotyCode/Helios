package team.gutterteam123.helios.terrain;

import lombok.SneakyThrows;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import team.gutterteam123.helios.Helios;
import team.gutterteam123.helios.render.VAO;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Random;

import static org.lwjgl.system.MemoryStack.stackPush;

public class Terrain {

    private static final int MAP_SIZE = 100;
    private static final int INDICES_SIZE = 6 * (MAP_SIZE - 1) * (MAP_SIZE);

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
        float[] vertices = new float[MAP_SIZE * MAP_SIZE * 3];
        int[] indices = new int[INDICES_SIZE];

        int vertexPointer = 0;
        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                vertices[vertexPointer * 3] = i;
                vertices[vertexPointer * 3 + 1] = (float) heightGenerator.noise(i / 20f, j / 20f) * 12;
                vertices[vertexPointer * 3 + 2] = j;
                vertexPointer++;
            }
        }
        int pointer = 0;
        for (int gz = 0; gz < MAP_SIZE - 1; gz++) {
            for (int gx = 0; gx < MAP_SIZE - 1; gx++) {
                int topLeft = (gz * MAP_SIZE) + gx;
                int topRight = topLeft + 1;
                int bottomLeft = ((gz + 1) * MAP_SIZE) + gx;
                int bottomRight = bottomLeft + 1;
                indices[pointer++] = topLeft;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = topRight;
                indices[pointer++] = topRight;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = bottomRight;
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
        GL11.glDrawElements(GL11.GL_TRIANGLES, INDICES_SIZE, GL11.GL_UNSIGNED_INT, 0);
        GL20.glDisableVertexAttribArray(0);
        vao.unbind();
        shader.stop();
    }

    public void cleanup() {
        shader.cleanUp();
    }
}
