package team.gutterteam123.helios.model;

import org.joml.Vector3f;

public class Material {

    private static final Vector3f DEFAULT_AMBIENT = new Vector3f(0.2f);
    private static final Vector3f DEFAULT_DIFFUSE = new Vector3f(0.8f);
    private static final Vector3f DEFAULT_SPECULAR = new Vector3f(1);

    private String name;
    private Vector3f ambient = DEFAULT_AMBIENT, diffuse = DEFAULT_DIFFUSE, specular = DEFAULT_SPECULAR;
    private float shininess;

    /* 0 = disable lighting, 1 = ambient & diffuse only (specular color set to black), 2 = full lighting */
    private int illuminationModel;

    public Material(String name) {
        this.name = name;
    }

    protected void readCommand(ModelReader reader, String command) {
        switch (command) {
            case "Ns":
                shininess = reader.readFloat();
                break;
            case "Ka":
                ambient = reader.readVector3f();
                break;
            case "Kd":
                diffuse = reader.readVector3f();
            case "Ks":
                specular = reader.readVector3f();
                break;
            case "illum":
                illuminationModel = reader.readInt();
                break;
            //TODO map_Ka, Tr, d, Ke, Ni
        }
    }
}
