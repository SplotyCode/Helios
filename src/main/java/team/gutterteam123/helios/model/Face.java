package team.gutterteam123.helios.model;

import lombok.Getter;
import lombok.ToString;
import org.joml.Vector2f;
import org.joml.Vector3i;

import java.util.ArrayList;

@Getter
@ToString
public class Face {

    /* Not coordinates but indexes of the vertex/texture array */
    private Vector3i position = new Vector3i();
    private Vector3i texture = new Vector3i();

    public void prepare(int index, int[] indices, float[] texture, ArrayList<Vector2f> textureVertices) {
        fill(index++, 0, indices, texture, textureVertices);
        fill(index++, 1, indices, texture, textureVertices);
        fill(index, 2, indices, texture, textureVertices);
    }

    private void fill(int index, int vertex, int[] indices, float[] texture, ArrayList<Vector2f> textureVertices) {
        int vertexID = position.get(vertex);
        indices[index] = vertexID - 1;

        int textureID = this.texture.get(vertex);
        if (textureID != 0) {
            Vector2f texturePos = textureVertices.get(textureID - 1);
            texture[vertexID * 2] = texturePos.x;
            texture[vertexID * 2 + 1] = texturePos.y;
        }
    }

}
