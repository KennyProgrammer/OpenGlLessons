#version 430 core

layout (location = 0) out vec4 out_Colour;

in vec3 position;
in vec4 colour;

uniform vec4 u_Colour;

void main()
{
	out_Colour = u_Colour;
}
