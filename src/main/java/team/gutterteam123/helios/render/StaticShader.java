package team.gutterteam123.helios.render;

import org.joml.Matrix4f;

public class StaticShader extends Shader {

    private int locationTransformationMatrix;
    private int locationProjectionMatrix;
    private int locationViewMatrix;

    public StaticShader() {
        super("src/main/resources/shader/staticvertexshader.glsl", "src/main/resources/shader/staticfragmentshader.glsl");
    }

    @Override
    protected void getAllUniformLocations() {
        locationTransformationMatrix = getUniformLocation("transformationMatrix");
        locationProjectionMatrix = getUniformLocation("projectionMatrix");
        locationViewMatrix = getUniformLocation("viewMatrix");
    }

    public void storeTransformationMatrix(Matrix4f transformationMatrix) {
        loadMatrix(locationTransformationMatrix, transformationMatrix);
    }

    public void storeProjectionMatrix(Matrix4f projectionMatrix) {
        loadMatrix(locationProjectionMatrix, projectionMatrix);
    }

    public void storeViewMatrix(Matrix4f viewMatrix) {
        loadMatrix(locationViewMatrix, viewMatrix);
    }

    @Override
    protected void bindAttributes() {

    }
}
