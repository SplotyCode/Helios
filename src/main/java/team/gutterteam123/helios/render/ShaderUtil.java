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
            System.out.println("Shader failed: " + GL20.glGetShaderInfoLog(shaderID, 500));
        }
        return shaderID;
    }

}
