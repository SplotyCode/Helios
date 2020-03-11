package team.gutterteam123.helios.entity;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import team.gutterteam123.helios.Helios;
import team.gutterteam123.helios.model.Model;
import team.gutterteam123.helios.util.MathUtil;

import java.io.File;

public class Airplane extends Entity {
    private Vector3f rotationVector;

    public Airplane() {
        model = new Model(new File("src/main/resources/models/Airbus A310.obj"),
                new File("src/main/resources/models/Airbus A310.mtl"));
        position = new Vector3f(20, 0, 20);
        rotationVector = new Vector3f(0,0,0);
    }

    @Override
    public void update() {
        if (Helios.isPressed(GLFW.GLFW_KEY_UP)) {
            position.y += 0.1;
            if((rotationVector.x>-30))
            rotationVector.x -=1;

        }
        if (Helios.isPressed(GLFW.GLFW_KEY_DOWN)) {
            position.y -= 0.075;
            if(!(rotationVector.x>30))
            rotationVector.x += 1;

        }
        if(Helios.isPressed(GLFW.GLFW_KEY_LEFT)){

            MathUtil.rotateMatrix(getTransformationMatrix(),rotationVector);
        }
    }

    @Override
    public Matrix4f getTransformationMatrix() {
        Matrix4f transformationMatrix = super.getTransformationMatrix();
        transformationMatrix.rotate((float) Math.toRadians(rotationVector.x), new Vector3f(1, 0, 0));

        return transformationMatrix;
    }
}
