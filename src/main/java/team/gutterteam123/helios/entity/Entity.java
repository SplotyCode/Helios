package team.gutterteam123.helios.entity;

import lombok.Getter;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import team.gutterteam123.helios.model.Model;

import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glScalef;

@Getter
public class Entity implements IEntity {

    protected Vector3f position;
    protected Model model;
    protected Matrix4f transformationMatrix = new Matrix4f();

    public void update() {

    }

    public void render() {
        GL11.glPushMatrix();
        //GL11.glTranslatef(position.x, position.y, position.z);
        glColor3f(1, 0, 0);
        glScalef(8f, 8f, 8f);
        model.render();
        GL11.glPopMatrix();
    }

    @Override
    public Matrix4f getTransformationMatrix() {
        transformationMatrix.zero();
        transformationMatrix.translate(position);
        return transformationMatrix;
    }
}
