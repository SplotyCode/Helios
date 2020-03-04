package team.gutterteam123.helios.entity;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import static org.lwjgl.opengl.GL11.*;

public class Floor implements IEntity {
    @Override
    public void update() {

    }

    @Override
    public void render() {
        glBegin(GL_LINES);
        glColor3f(0.2f, 0.2f, 0.2f);
        for (int i = -20; i <= 20; i++) {
            glVertex3f(-20.0f, 0.0f, i);
            glVertex3f(20.0f, 0.0f, i);
            glVertex3f(i, 0.0f, -20.0f);
            glVertex3f(i, 0.0f, 20.0f);
        }
        glEnd();
    }

    @Override
    public Vector3f getPosition() {
        return new Vector3f();
    }

    @Override
    public Matrix4f getTransformationMatrix() {
        return new Matrix4f();
    }
}
