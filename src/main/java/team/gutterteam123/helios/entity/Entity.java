package team.gutterteam123.helios.entity;

import lombok.Getter;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import team.gutterteam123.helios.model.Model;

@Getter
public class Entity implements IEntity {

    protected Vector3f position;
    protected Vector3f rotation = new Vector3f();
    protected Model model;
    protected Matrix4f transformationMatrix = new Matrix4f();

    public void update() {

    }

    public void render() {
        //GL11.glPushMatrix();
        //GL11.glTranslatef(position.x, position.y, position.z);
        //glColor3f(1, 0, 0);
        //glScalef(8f, 8f, 8f);
        model.render();
        //GL11.glPopMatrix();
    }

    @Override
    public Matrix4f getTransformationMatrix() {
        transformationMatrix
                .identity()
                .translate(position);
                //.rotationXYZ((float) Math.toDegrees(rotation.x), (float) Math.toDegrees(rotation.y), (float) Math.toDegrees(rotation.z));
        return transformationMatrix;
    }
}
