package team.gutterteam123.helios.render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.ArrayList;

/*
 * A vao can store data directly in the GPU
 * The Vao stores VBOs (as attributes) witch then store the actual data
 */
public class VAO {

    private static ArrayList<VAO> vaos = new ArrayList<>();

    public static void cleanAll() {
        vaos.forEach(vao -> vao.cleanup(false));
        vaos.clear();
    }

    private int vaoID;
    private ArrayList<Integer> vbos = new ArrayList<>();

    public VAO() {
        vaoID = GL30.glGenVertexArrays();
        vaos.add(this);
    }

    /*
     * Creates a vbo and adds it to this vao
     * The VAO needs to be bound first
     */
    public void store(int attributeIndex, float[] data, int bundleSize, int usage) {
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, createVBO());

        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, data, usage);
        GL20.glVertexAttribPointer(attributeIndex, bundleSize, GL11.GL_FLOAT, false, 0, 0);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    public void store(int attributeIndex, float[] data, int bundleSize) {
        store(attributeIndex, data, bundleSize, GL15.GL_STATIC_DRAW);
    }

    public void storeIndices(int[] indices) {
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, createVBO());
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indices, GL15.GL_STATIC_DRAW);
    }

    protected int createVBO() {
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);
        return vboID;
    }

    public void bind() {
        GL30.glBindVertexArray(vaoID);
    }

    /*
     * Binding the default VAO will unbind the current one
     */
    public void unbind() {
        GL30.glBindVertexArray(0);
    }

    public void cleanup(boolean remove) {
        if (remove) {
            vaos.remove(this);
        }
        GL30.glDeleteVertexArrays(vaoID);
        GL15.glDeleteBuffers(vbos.stream().mapToInt(value -> value).toArray());
    }

}
