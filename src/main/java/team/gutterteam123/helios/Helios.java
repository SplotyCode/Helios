package team.gutterteam123.helios;

import com.sun.org.apache.xalan.internal.Version;
import lombok.Getter;
import team.gutterteam123.helios.entity.Airplane;
import team.gutterteam123.helios.entity.IEntity;
import team.gutterteam123.helios.window.Window;

import java.io.IOException;
import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Helios {

    @Getter private static Helios instance;

    @Getter Window window;
    @Getter ArrayList<IEntity> entities = new ArrayList<>();

    public void run() throws IOException {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");
        window = new Window();
        window.init();
        loop();

        window.destroy();
    }

    private void loop() throws IOException {
        // Set the clear color
        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.

        entities.add(new Airplane());
        while (!glfwWindowShouldClose(window.getWindow())) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            for (IEntity entity : entities) {
                entity.update();
                entity.render();
            }

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