package team.gutterteam123.helios.render;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.io.File;
import java.nio.charset.StandardCharsets;

public class ShaderUtil {

    @SneakyThrows
    public static int loadShader(String file, int type) {
        int shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, FileUtils.readFileToString(new File(file), StandardCharsets.UTF_8));
        GL20.glCompileShader(shaderID);
        if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            printShaderLog(file, "compile", shaderID);
        }
        return shaderID;
    }

    public static void printShaderLog(String shader, String status, int shaderID) {
        String messages =  GL20.glGetShaderInfoLog(shaderID, 1000);
        if (messages.length() == 100) {
            System.out.println("Shader log exceeds max log size! Print first 1000 chars");
        }
        System.out.println("Shader '" + shader + "' failed to " + status + ": " + messages);
    }

    public static void printProgramLog(Shader shader, String status, int shaderID) {
        String messages =  GL20.glGetProgramInfoLog(shaderID, 1000);
        if (messages.length() == 100) {
            System.out.println("Shader log exceeds max log size! Print first 1000 chars");
        }
        System.out.println("Shader '" + shader.getClass().getSimpleName() + "' failed to " + status + ": " + messages);
    }

}
