package team.gutterteam123.helios.render;

import lombok.Getter;
import org.joml.Matrix4f;

@Getter
public class SkyDomeShader extends Shader {

    private int locationTransformationMatrix;
    private int locationProjectionMatrix;
    private int locationViewMatrix;

    public SkyDomeShader() {
        super("src/main/resources/shader/skyvertexshader.glsl", "src/main/resources/shader/skyfragmentshader.glsl");
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
