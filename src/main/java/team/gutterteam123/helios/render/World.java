package team.gutterteam123.helios.render;

import lombok.Getter;
import org.joml.Matrix4f;
import team.gutterteam123.helios.Helios;
import team.gutterteam123.helios.entity.IEntity;
import team.gutterteam123.helios.entity.SkyDome;
import team.gutterteam123.helios.terrain.Terrain;

import java.util.ArrayList;

@Getter
public class World {

    private Matrix4f projectionMatrix = new Matrix4f();
    private Camera camera = new Camera();
    private StaticShader staticShader = new StaticShader();

    private ArrayList<IEntity> entities = new ArrayList<>();

    private Terrain terrain = new Terrain();

    {
        entities.add(new SkyDome());
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
        terrain.render();
        staticShader.start();
        staticShader.storeViewMatrix(camera.calculateViewMatrix());

        boolean useStatic = true;

        for (IEntity entity : entities) {
            useStatic = render(entity, useStatic);
        }

        if (useStatic) {
            staticShader.stop();
        }
    }

    public void destroy() {
        entities.forEach(IEntity::cleanup);
        staticShader.cleanUp();
        terrain.cleanup();
    }

    private boolean render(IEntity entity, boolean useStatic) {
        if (entity.shader() == null) {
            if (!useStatic) {
                staticShader.start();
            }
            staticShader.storeTransformationMatrix(entity.getTransformationMatrix());
        } else {
            if (useStatic) {
                staticShader.stop();
            }
            entity.shader().start();
        }
        entity.render();
        if (entity.shader() != null) {
            entity.shader().stop();
            return false;
        }
        return true;
    }

    public Matrix4f getViewProjectionMatrix() {
        return camera.calculateViewMatrix().mul(projectionMatrix);
    }

}
