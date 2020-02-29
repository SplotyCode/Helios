package team.gutterteam123.helios.render;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import team.gutterteam123.helios.Helios;
import team.gutterteam123.helios.util.MathUtil;

import java.text.NumberFormat;

public class Camera {

    public static final float FOV = 70;
    public static final float NEAR_PLANE = 0.1f;
    public static final float FAR_PLANE = 1000;

    private Vector3f position = new Vector3f(0, 0, -1);
    private Vector3f rotation = new Vector3f(20, 0, 0); //pitch yaw roll

    private Matrix4f viewMatrix = new Matrix4f();

    public void update() {
        float verticalDistance = (float) (50 * Math.sin(Math.toRadians(rotation.x)));
        float horizontalDistance = (float) (50 * Math.cos(Math.toRadians(rotation.x)));
        float offsetX = (float) (horizontalDistance * Math.sin(Math.toRadians(0)));
        float offsetZ = (float) (horizontalDistance * Math.cos(Math.toRadians(0)));

        position.x = offsetX;
        position.z = offsetZ;
        position.y = verticalDistance;
    }

    public Matrix4f calculateViewMatrix() {
        viewMatrix.identity();
        viewMatrix.lookAt(position, new Vector3f(0, 0, 0), new Vector3f(0, 1, 0));
        //MathUtil.rotateMatrix(viewMatrix, rotation);
        //viewMatrix.translate(-position.x, -position.y, -position.z);
        //System.out.println(viewMatrix.toString(NumberFormat.getNumberInstance()));
        return viewMatrix;
    }



}
