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
        //position.x+= 0.001/Math.sin(rotationVector.y/360);
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
            if(rotationVector.z>-30)
                rotationVector.z-=1;
            rotationVector.y-=1;
        }
        if(Helios.isPressed(GLFW.GLFW_KEY_RIGHT)){
            if(!(rotationVector.z>30))
                rotationVector.z+=1;
            rotationVector.y+=1;
        }
    }

    @Override
    public Matrix4f getTransformationMatrix() {
        Matrix4f transformationMatrix = super.getTransformationMatrix();
        transformationMatrix.rotate((float) Math.toRadians(rotationVector.x), new Vector3f(1, 0, 0));
        transformationMatrix.rotate((float) Math.toRadians(rotationVector.y), new Vector3f(0,1,0));
        transformationMatrix.rotate((float) Math.toRadians(rotationVector.z), new Vector3f(0,0,1));
        return transformationMatrix;
    }
}
