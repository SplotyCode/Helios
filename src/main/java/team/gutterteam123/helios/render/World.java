package team.gutterteam123.helios.render;

import org.joml.Matrix4f;
import team.gutterteam123.helios.Helios;
import team.gutterteam123.helios.entity.Airplane;
import team.gutterteam123.helios.entity.IEntity;

import java.util.ArrayList;

public class World {

    private Matrix4f projectionMatrix = new Matrix4f();
    private Camera camera = new Camera();
    private StaticShader staticShader = new StaticShader();

    private ArrayList<IEntity> entities = new ArrayList<>();

    {
        entities.add(Helios.getInstance().getPlane());
    }

    public World() {
        reCalcProjectionMatrix();
    }

    public void reCalcProjectionMatrix() {
        float aspectRatio = Helios.getInstance().getWindow().getAspectRatio();
        projectionMatrix
                .identity()
                .perspective((float) Math.toRadians(Camera.FOV), aspectRatio, Camera.NEAR_PLANE, Camera.FAR_PLANE);

        staticShader.start();
        staticShader.storeProjectionMatrix(projectionMatrix);
        //System.out.println("projection: \n" + projectionMatrix.toString(DecimalFormat.getNumberInstance()));
        staticShader.stop();
    }

    public void update() {
        camera.update();
        for (IEntity entity : entities) {
            entity.update();
        }
    }

    public void render() {
        staticShader.start();
        staticShader.storeViewMatrix(camera.calculateViewMatrix());

        for (IEntity entity : entities) {
            render(entity);
        }

        staticShader.stop();
    }

    public void destroy() {
        staticShader.cleanUp();
    }

    private void render(IEntity entity) {
        staticShader.storeTransformationMatrix(entity.getTransformationMatrix());
        entity.render();
    }

}
