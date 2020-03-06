package team.gutterteam123.helios.terrain;

import lombok.SneakyThrows;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import team.gutterteam123.helios.Helios;
import team.gutterteam123.helios.render.Texture;
import team.gutterteam123.helios.render.VAO;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.system.MemoryStack.stackPush;

public class Terrain {

    private static final int MAP_SIZE = 100;

    private TerrainShader shader = new TerrainShader();

    private VAO vao = new VAO();

    private File heightmap = new File("src/main/resources/terrain/heightmap.bmp");
    private int height;

    public Terrain() {
        createMesh();

        shader.start();
        shader.storeStaticColor(new Vector4f(0, 1, 0, 1));
        shader.stop();
    }

    @SneakyThrows
    private void createMesh() {
        float[] vertices = new float[MAP_SIZE * MAP_SIZE * 3];
        int[] indices = new int[6 * (MAP_SIZE - 1) * (MAP_SIZE)];

        ByteBuffer buffer;
        try (MemoryStack stack = stackPush()) {
            IntBuffer widthP = stack.mallocInt(1);
            IntBuffer heightP = stack.mallocInt(1);
            IntBuffer channelsP = stack.mallocInt(1);

            buffer = STBImage.stbi_load(heightmap.getAbsolutePath(), widthP, heightP, channelsP, 1);
            System.out.println(channelsP.get());
            if (buffer == null) {
                throw new Exception("Failed to load file " + heightmap.getAbsolutePath() + ": " + STBImage.stbi_failure_reason());
            }

            height = heightP.get();
            assert height == widthP.get();
            float usedHeight = height / 8f;

            int vertexPointer = 0;
            for (int i = 0; i < MAP_SIZE; i++) {
                for (int j = 0; j < MAP_SIZE; j++) {
                    vertices[vertexPointer * 3] = i;
                    vertices[vertexPointer * 3 + 1] = getHeight(i / (float) MAP_SIZE * usedHeight, j / (float) MAP_SIZE * usedHeight, buffer);
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
    }

    private float getHeight(float x, float z, ByteBuffer buffer) {
        /*byte r = buffer.get(x * 4 + 0 + z * 4 * width);
        byte g = buffer.get(x * 4 + 1 + z * 4 * width);
        byte b = buffer.get(x * 4 + 2 + z * 4 * width);
        byte a = buffer.get(x * 4 + 3 + z * 4 * width);
        int argb = ((0xFF & a) << 24) | ((0xFF & r) << 16)
                | ((0xFF & g) << 8) | (0xFF & b);
        return argb / (255 * 255 * 255f * 255) * 16;*/
        System.out.println(buffer.remaining());
        return buffer.get((int) (height * x + z)) / 255f * 35f;
    }

    public void render() {
        shader.start();
        shader.storeProjectionMatrix(Helios.getInstance().getWorld().getProjectionMatrix());
        shader.storeViewMatrix(Helios.getInstance().getWorld().getCamera().calculateViewMatrix());
        vao.bind();
        GL20.glEnableVertexAttribArray(0);
        GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
        GL11.glDrawElements(GL11.GL_TRIANGLES, height * height, GL11.GL_UNSIGNED_INT, 0);
        GL20.glDisableVertexAttribArray(0);
        vao.unbind();
        shader.stop();
    }

    public void cleanup() {
        shader.cleanUp();
    }
}
