#version 330 core

layout(location = 0) out vec4 outColor;

in vec4 worldPosition;

const vec3 skyColor = vec3(0.22, 0.69, 0.87);

void main() {
    float y = worldPosition.y - 140;
    float red = -0.0022 * y + skyColor.x;
    float green = -0.0025 * y + skyColor.y;
    float blue = -0.0019 * y + skyColor.z;

    outColor = vec4(red, green, blue, 1);

}