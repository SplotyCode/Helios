package team.gutterteam123.helios.terrain;

import org.joml.Matrix4f;
import org.joml.Vector4f;
import team.gutterteam123.helios.render.Shader;

public class TerrainShader extends Shader {

    private int locationProjectionMatrix;
    private int locationViewMatrix;
    private int locationStaticColor;

    public TerrainShader() {
        super("src/main/resources/terrain/terrainvertexshader.glsl", "src/main/resources/shader/staticfragmentshader.glsl");
    }

    @Override
    protected void getAllUniformLocations() {
        locationStaticColor = getUniformLocation("staticColor");
        locationProjectionMatrix = getUniformLocation("projectionMatrix");
        locationViewMatrix = getUniformLocation("viewMatrix");
        locationStaticColor = getUniformLocation("staticColor");
    }

    public void storeStaticColor(Vector4f color) {
        loadVector(locationStaticColor, color);
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
