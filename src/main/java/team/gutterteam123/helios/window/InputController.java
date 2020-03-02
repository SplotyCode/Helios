package team.gutterteam123.helios.window;

import lombok.Getter;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWScrollCallbackI;
import org.lwjgl.system.MemoryStack;
import team.gutterteam123.helios.Helios;

import java.nio.DoubleBuffer;

import static org.lwjgl.system.MemoryStack.stackPush;

@Getter
public class InputController implements GLFWScrollCallbackI {

    private double scrollX;
    private double scrollY;

    private float lastMouseX;
    private float lastMouseY;
    private float mouseX, mouseY;
    private float dragX, dragY;

    private boolean wasMousePressed;
    private boolean usedWheel;

    public InputController() {
        GLFW.glfwSetScrollCallback(GLFW.glfwGetCurrentContext(), this);
    }

    public void update() {
        lastMouseX = mouseX;
        lastMouseY = mouseY;

        long window = Helios.getInstance().getWindow().getWindow();
        try (MemoryStack stack = stackPush()) {
            DoubleBuffer pMouseX = stack.mallocDouble(1);
            DoubleBuffer pMouseY = stack.mallocDouble(1);
            GLFW.glfwGetCursorPos(window, pMouseX, pMouseY);
            mouseX = (float) pMouseX.get();
            mouseY = (float) pMouseY.get();
        }

        if (wasMousePressed) {
            dragX = mouseX - lastMouseX;
            dragY = mouseY -lastMouseY;
        } else {
            dragX = dragY = 0;
        }

        wasMousePressed = GLFW.glfwGetMouseButton(window, GLFW.GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_PRESS;

        if (!usedWheel) {
            scrollX = scrollY = 0;
        }
        usedWheel = false;
    }

    @Override
    public void invoke(long window, double xoffset, double yoffset) {
        usedWheel = true;
        scrollX = xoffset;
        scrollY = yoffset;
    }

}
