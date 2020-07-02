#version 430 core

layout (location = 0) in vec3 attrib_Position;
layout (location = 1) in vec4 attrib_Colour;
layout (location = 2) in vec2 attrib_TextureCoord;

out vec3 position;
out vec4 colour;
out vec2 textureCoord;

void main()
{
	position       = attrib_Position;
	colour         = attrib_Colour;
	textureCoord   = attrib_TextureCoord;
	
	gl_Position    = vec4(attrib_Position, 1.0f);
}