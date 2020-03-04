#version 330 core

layout (location = 0) in vec3 position;

out vec4 worldPosition;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

void main() {
    //projectionMatrix * viewMatrix * transformationMatrix * position
    worldPosition = transformationMatrix * vec4(position, 1.0);
    gl_Position = projectionMatrix * viewMatrix * transformationMatrix * vec4(position, 1.0);
}