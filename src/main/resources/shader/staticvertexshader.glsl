#version 330 core

in vec3 position;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

void main() {
    //projectionMatrix * viewMatrix * transformationMatrix *
    gl_Position = projectionMatrix * viewMatrix * transformationMatrix * vec4(position, 1.0);
}