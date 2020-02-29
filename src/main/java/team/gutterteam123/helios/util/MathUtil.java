package team.gutterteam123.helios.util;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class MathUtil {

    public static void rotateMatrix(Matrix4f matrix, Vector3f rotation) {
        matrix.rotationX((float) Math.toRadians(rotation.x));
        matrix.rotationY((float) Math.toRadians(rotation.y));
        matrix.rotationZ((float) Math.toRadians(rotation.z));
    }

}
