package team.gutterteam123.helios;

import com.sun.org.apache.xalan.internal.Version;
import lombok.Getter;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import team.gutterteam123.helios.entity.Airplane;
import team.gutterteam123.helios.entity.IEntity;
import team.gutterteam123.helios.render.VAO;
import team.gutterteam123.helios.render.World;
import team.gutterteam123.helios.window.InputController;
import team.gutterteam123.helios.window.Window;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

@Getter
public class Helios {

    @Getter private static Helios instance;

    public static boolean isPressed(int key) {
        return GLFW.glfwGetKey(instance.getWindow().getWindow(), key) == GLFW.GLFW_PRESS;
    }

    Window window;
    World world;
    InputController inputController;

    private Airplane plane;

    public void run() throws IOException {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");
        window = new Window();
        window.init();
        inputController = new InputController();
        plane = new Airplane();
        loop();

        world.destroy();
        window.destroy();
        VAO.cleanAll();
    }

    private void loop() throws IOException {
        // Set the clear color
        //glClearColor(0.22f, 0.69f, 0.87f, 1.0f);

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.

        world = new World();
        //glEnable(GL_DEPTH_TEST);
        while (!glfwWindowShouldClose(window.getWindow())) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            inputController.update();
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