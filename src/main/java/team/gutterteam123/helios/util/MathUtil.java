package team.gutterteam123.helios.util;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class MathUtil {

    public static final Vector3f ORIGIN = new Vector3f();

    public static void rotateMatrix(Matrix4f matrix, Vector3f rotation) {
        matrix.rotationX((float) Math.toRadians(rotation.x));
        matrix.rotationY((float) Math.toRadians(rotation.y));
        matrix.rotationZ((float) Math.toRadians(rotation.z));
    }

    public static float normalize360(float degree) {
        degree = degree % 360;
        return degree < 0 ? degree + 360 : degree;
    }

}
