package team.gutterteam123.helios.render;

import org.joml.Matrix4f;
import team.gutterteam123.helios.entity.Airplane;
import team.gutterteam123.helios.entity.Floor;
import team.gutterteam123.helios.entity.IEntity;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

public class World {

    private Matrix4f projectionMatrix = new Matrix4f();
    private Camera camera = new Camera();

    private ArrayList<IEntity> entities = new ArrayList<>();

    {
        entities.add(new Airplane());
    }

    public World() {
        reCalcProjectionMatrix();
    }

    public void reCalcProjectionMatrix() {
        float aspectRatio = 300.0f / 300.0f;
        projectionMatrix
                .identity()
                .perspective((float) Math.toRadians(Camera.FOV), aspectRatio, Camera.NEAR_PLANE, Camera.FAR_PLANE);

        /*staticShader.start();
        staticShader.storeProjectionMatrix(projectionMatrix);
        System.out.println("projection: \n" + projectionMatrix.toString(DecimalFormat.getNumberInstance()));
        staticShader.stop();*/
    }

    public void update() {
        camera.update();
        //cube.setUp();
        for (IEntity entity : entities) {
            entity.update();
        }
    }

    public void render() {
        //staticShader.start();
        //staticShader.storeViewMatrix(camera.calculateViewMatrix());
        glMatrixMode(GL_PROJECTION);
        //glLoadIdentity();
        glLoadMatrixf(projectionMatrix.get(new float[16]));
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        glLoadMatrixf(camera.calculateViewMatrix().get(new float[16]));

        //new Floor().render();
        for (IEntity entity : entities) {
            render(entity);
        }
        //staticShader.stop();
    }

    private void render(IEntity entity) {
        //staticShader.storeTransformationMatrix(entity.getTransformationMatrix());
        entity.render();
    }

}
