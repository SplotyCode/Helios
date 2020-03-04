package team.gutterteam123.helios.entity;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import team.gutterteam123.helios.Helios;
import team.gutterteam123.helios.model.Model;
import team.gutterteam123.helios.render.Camera;
import team.gutterteam123.helios.render.Shader;
import team.gutterteam123.helios.render.SkyDomeShader;
import team.gutterteam123.helios.render.World;
import team.gutterteam123.helios.util.MathUtil;

import java.io.File;

import static org.lwjgl.opengl.GL11.GL_CCW;
import static org.lwjgl.opengl.GL11.glFrontFace;
import static org.lwjgl.opengles.GLES20.GL_CW;

public class SkyDome implements IEntity {

    private static final Matrix4f IDENTITY_MATRIX = new Matrix4f();

    private SkyDomeShader shader = new SkyDomeShader();
    private Model model = new Model(new File("src/main/resources/models/skydome.obj"));

    private Matrix4f transformation = new Matrix4f();

    @Override public void update() {}

    @Override
    public void render() {
        World world = Helios.getInstance().getWorld();
        Vector3f cameraPosition = world.getCamera().getPosition();
        transformation
                .identity()
                .translate(cameraPosition.x, 0, cameraPosition.z)
                .scale(Camera.FAR_PLANE * 0.5f);
        shader.storeProjectionMatrix(world.getProjectionMatrix());
        shader.storeTransformationMatrix(transformation);
        shader.storeViewMatrix(world.getCamera().calculateViewMatrix());
        glFrontFace(GL_CCW);
        model.render();
        glFrontFace(GL_CW);
    }

    @Override
    public Shader shader() {
        return shader;
    }

    @Override
    public void cleanup() {
        shader.cleanUp();
    }

    @Override
    public Vector3f getPosition() {
        return MathUtil.ORIGIN;
    }

    @Override
    public Matrix4f getTransformationMatrix() {
        return IDENTITY_MATRIX;
    }
}
