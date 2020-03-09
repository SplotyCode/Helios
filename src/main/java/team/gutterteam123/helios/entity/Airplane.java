package team.gutterteam123.helios.entity;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import team.gutterteam123.helios.Helios;
import team.gutterteam123.helios.model.Model;

import java.io.File;

public class Airplane extends Entity {

    public Airplane() {
        model = new Model(new File("src/main/resources/models/Airbus A310.obj"),
                new File("src/main/resources/models/Airbus A310.mtl"));
        position = new Vector3f(20, 0, 20);
    }

    @Override
    public void update() {
        if (Helios.isPressed(GLFW.GLFW_KEY_UP)) {
            position.y += 0.1;
        }
        if (Helios.isPressed(GLFW.GLFW_KEY_DOWN)) {
            position.y -= 0.075;
        }
    }

    @Override
    public Matrix4f getTransformationMatrix() {
        Matrix4f transformationMatrix = super.getTransformationMatrix();
        //MathUtil.rotateMatrix(transformationMatrix, ); TODO rotation??
        return transformationMatrix;
    }
}
