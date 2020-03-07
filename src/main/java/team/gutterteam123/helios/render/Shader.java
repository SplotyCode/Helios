package team.gutterteam123.helios.render;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

/*
 * This class is inspired from thin matrix
 */
public abstract class Shader {

    private int programID;
    private int vertexShaderID;
    private int fragmentShaderID;

    public Shader(String vertexFile, String fragmentFile) {
        vertexShaderID = ShaderUtil.loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
        fragmentShaderID = ShaderUtil.loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);
        programID = GL20.glCreateProgram();
        GL20.glAttachShader(programID, vertexShaderID);
        GL20.glAttachShader(programID, fragmentShaderID);
        bindAttributes();
        GL20.glLinkProgram(programID);
        if (GL20.glGetProgrami(programID, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) {
            ShaderUtil.printProgramLog(this, "link", programID);
        }
        GL20.glValidateProgram(programID);
        if (GL20.glGetProgrami(programID, GL20.GL_VALIDATE_STATUS) == GL11.GL_FALSE) {
            ShaderUtil.printProgramLog(this, "validate", programID);
        }
        getAllUniformLocations();
    }

    protected abstract void getAllUniformLocations();

    protected final int getUniformLocation(String uniformName) {
        int uniformLocation = GL20.glGetUniformLocation(programID, uniformName);
        if (uniformLocation == -1) {
            throw new IllegalArgumentException("Could not find uniform name: " + uniformName);
        }
        return uniformLocation;
    }

    public void start() {
        GL20.glUseProgram(programID);
    }

    public void stop() {
        GL20.glUseProgram(0);
    }

    public void cleanUp() {
        GL20.glDetachShader(programID, vertexShaderID);
        GL20.glDetachShader(programID, fragmentShaderID);
        GL20.glDeleteShader(vertexShaderID);
        GL20.glDeleteShader(fragmentShaderID);
        GL20.glDeleteProgram(programID);
    }

    protected abstract void bindAttributes();

    protected void bindAttribute(int attribute, String variableName) {
        GL20.glBindAttribLocation(programID, attribute, variableName);
    }

    protected void loadFloat(int location, float value) {
        GL20.glUniform1f(location, value);
    }

    protected void loadInt(int location, int value) {
        GL20.glUniform1i(location, value);
    }

    protected void loadVector(int location, Vector3f vector) {
        GL20.glUniform3f(location, vector.x, vector.y, vector.z);
    }

    protected void loadVector(int location, Vector4f vector) {
        GL20.glUniform4f(location, vector.x, vector.y, vector.z, vector.w);
    }

    protected void load2DVector(int location, Vector2f vector) {
        GL20.glUniform2f(location, vector.x, vector.y);
    }

    protected void loadBoolean(int location, boolean value) {
        GL20.glUniform1f(location, value ? 1 : 0);
    }

    private float[] matrixBuffer = new float[4 * 4];

    public void loadMatrix(int location, Matrix4f matrix) {
        GL20.glUniformMatrix4fv(location, false, matrix.get(matrixBuffer));
    }

}
