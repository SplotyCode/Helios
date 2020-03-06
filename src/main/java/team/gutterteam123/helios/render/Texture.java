package team.gutterteam123.helios.render;

import lombok.Getter;
import lombok.SneakyThrows;
import org.lwjgl.opengl.GL11;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.EXTBGRA.GL_BGR_EXT;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;

@Getter
public class Texture {

    private int width, height;
    private int textureID;

    @SneakyThrows
    public Texture(File file) {
        ByteBuffer buffer;
        int channels;

        try (MemoryStack stack = stackPush()) {
            IntBuffer widthP = stack.mallocInt(1);
            IntBuffer heightP = stack.mallocInt(1);
            IntBuffer channelsP = stack.mallocInt(1);

            buffer = STBImage.stbi_load(file.getAbsolutePath(), widthP, heightP, channelsP, 0);
            if (buffer == null) {
                throw new Exception("Failed to load file " + file + ": " + STBImage.stbi_failure_reason());
            }

            width = widthP.get();
            height = heightP.get();
            channels = channelsP.get();
        }
        textureID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureID);

        /* What should happen if we want to get pixels outside of the image? */
        setParameter(GL_TEXTURE_WRAP_S, GL_REPEAT);
        setParameter(GL_TEXTURE_WRAP_T, GL_REPEAT);

        /* used if your texture is scaled to a different image size */
        setParameter(GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        setParameter(GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        if (channels == 3) {
            if ((width & 3) != 0) {
                glPixelStorei(GL_UNPACK_ALIGNMENT, 2 - (width & 1));
            }
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, buffer);
        } else {
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
        }
        STBImage.stbi_image_free(buffer);
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, textureID);
    }

    public void setParameter(int keyID, int value) {
        glTexParameteri(GL_TEXTURE_2D, keyID, value);
    }

    public void delete() {
        glDeleteTextures(textureID);
    }

}
