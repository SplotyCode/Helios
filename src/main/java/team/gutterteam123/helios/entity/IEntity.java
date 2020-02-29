package team.gutterteam123.helios.entity;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public interface IEntity {

    void update();
    void render();

    Vector3f getPosition();

    Matrix4f getTransformationMatrix();

}
