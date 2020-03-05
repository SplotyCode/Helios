#version 330 core

out vec4 ourColor;

uniform vec4 staticColor = vec4(1, 0, 0, 1);

void main() {
    ourColor = staticColor;
}