package team.gutterteam123.helios.model;

import com.sun.org.apache.xalan.internal.Version;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;
import team.gutterteam123.helios.entity.Airplane;
import team.gutterteam123.helios.window.Window;

import java.io.File;
import java.io.IOException;
import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class HelloWorld {

    private static HelloWorld instance;

    public static HelloWorld getInstance() {
        return instance;
    }

    // The window handle
    private long windowId;
    Window window;

    public Window getWindow() {
        return window;
    }

    public void run() throws IOException {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");
        window = new Window();
        window.init();
        loop();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(windowId);
        glfwDestroyWindow(windowId);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }



    private void loop() throws IOException {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        // Set the clear color
        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.

        Airplane airplane = new Airplane();
        while ( !glfwWindowShouldClose(window.getWindow()) ) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            airplane.update();
            airplane.render();

            glfwSwapBuffers(window.getWindow()); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }

    public static void main(String[] args) throws IOException {
        instance = new HelloWorld();
        instance.run();
    }

}