package team.gutterteam123.helios.entity;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import team.gutterteam123.helios.model.HelloWorld;
import team.gutterteam123.helios.model.Model;

import java.io.File;
import java.io.IOException;

public class Airplane extends Entity {

    public Airplane() throws IOException {
        model = new Model(new File("src/main/resources/models/plane.obj"));
        position = new Vector3f(0, 0, 0);
    }

    @Override
    public void update() {
        if (GLFW.glfwGetKey(HelloWorld.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_UP) == GLFW.GLFW_PRESS) {
            position.y += 0.075;
        }
        if (GLFW.glfwGetKey(HelloWorld.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_DOWN) == GLFW.GLFW_PRESS) {
            position.y -= 0.075;
        }
    }
}
