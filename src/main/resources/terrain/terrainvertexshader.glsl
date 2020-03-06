#version 330 core

uniform sampler2D heightmap;

in vec3 position;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

void main() {
    vec2 pos = vec2(position.x, position.z);
    float y = texture(heightmap, pos).r;
    gl_Position = projectionMatrix * viewMatrix * vec4(position.x, position.y, position.z, 1.0f);
}