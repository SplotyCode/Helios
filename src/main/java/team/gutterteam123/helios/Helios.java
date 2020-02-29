package team.gutterteam123.helios;

import com.sun.org.apache.xalan.internal.Version;
import lombok.Getter;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import team.gutterteam123.helios.entity.Airplane;
import team.gutterteam123.helios.entity.IEntity;
import team.gutterteam123.helios.render.VAO;
import team.gutterteam123.helios.render.World;
import team.gutterteam123.helios.window.Window;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Helios {

    @Getter private static Helios instance;

    public static boolean isPressed(int key) {
        return GLFW.glfwGetKey(instance.getWindow().getWindow(), GLFW.GLFW_KEY_UP) == GLFW.GLFW_PRESS;
    }

    @Getter Window window;
    @Getter World world;

    public void run() throws IOException {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");
        window = new Window();
        window.init();
        loop();

        window.destroy();
        VAO.cleanAll();
        System.out.println("xxx");
        Matrix4f matrix = new Matrix4f().identity().rotationX((float) Math.toRadians(20));
        System.out.println(matrix.m12() + " " + matrix.m21());
    }

    private void loop() throws IOException {
        // Set the clear color
        glClearColor(0.0f, 1.0f, 0.0f, 0.0f);

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.

        world = new World();
        while (!glfwWindowShouldClose(window.getWindow())) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            world.update();
            world.render();

            glfwSwapBuffers(window.getWindow()); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }

    public static void main(String[] args) throws IOException {
        instance = new Helios();
        instance.run();
    }

}