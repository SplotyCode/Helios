package team.gutterteam123.helios.render;

import lombok.Getter;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import team.gutterteam123.helios.Helios;
import team.gutterteam123.helios.util.MathUtil;

@Getter
public class Camera {

    public static final float FOV = 70;
    public static final float NEAR_PLANE = 0.1f;
    public static final float FAR_PLANE = 1000;

    private Vector3f position = new Vector3f();
    private Vector3f rotation = new Vector3f(); //pitch yaw roll

    private Matrix4f viewMatrix = new Matrix4f();

    private float distance = 2;
    private float angle;

    public void update() {
        distance -= Helios.getInstance().getInputController().getScrollY() * 0.5f;
        distance = Math.max(.1f, distance);
        angle += Helios.getInstance().getInputController().getDragX() * 0.5f;
        angle = MathUtil.normalize360(angle);
        rotation.x += Helios.getInstance().getInputController().getDragY();
        rotation.x = MathUtil.normalize360(rotation.x);

        Vector3f planePosition = Helios.getInstance().getPlane().getPosition();
        position.x = planePosition.x + (float) Math.cos(Math.toRadians(angle)) * distance;
        position.z = planePosition.z + (float) Math.sin(Math.toRadians(angle)) * distance;
        position.y = planePosition.y + (float) Math.sin(Math.toRadians(rotation.x)) * distance;

        rotation.y = MathUtil.normalize360(angle - 90);
    }

    public Matrix4f calculateViewMatrix() {
        viewMatrix.identity();
        //viewMatrix.lookAt(position, new Vector3f(0, 0, 0), new Vector3f(0, 1, 0));
        viewMatrix.rotate((float) Math.toRadians(rotation.x), new Vector3f(1, 0, 0));
        viewMatrix.rotate((float) Math.toRadians(rotation.y), new Vector3f(0, 1, 0));
        //viewMatrix.rotationY((float) Math.toRadians(rotation.y));
        //viewMatrix.rotationZ((float) Math.toRadians(rotation.z));
        viewMatrix.translate(-position.x, -position.y, -position.z);
        return viewMatrix;
    }



}
