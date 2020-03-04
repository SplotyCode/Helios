package team.gutterteam123.helios.entity;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import team.gutterteam123.helios.render.Shader;

public interface IEntity {

    void update();
    void render();

    default void cleanup() {}

    default Shader shader() {
        return null;
    }

    Vector3f getPosition();

    Matrix4f getTransformationMatrix();

}
