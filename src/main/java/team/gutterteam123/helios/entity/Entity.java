package team.gutterteam123.helios.entity;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import team.gutterteam123.helios.model.Model;

import static org.lwjgl.opengl.GL11.glColor3f;

public class Entity {

    protected Vector3f position;
    protected Model model;

    public void update() {

    }

    public void render() {
        GL11.glPushMatrix();
        GL11.glTranslatef(position.x, position.y, position.z);
        glColor3f(0, 0, 0);
        model.render();
        GL11.glPopMatrix();
    }

}
