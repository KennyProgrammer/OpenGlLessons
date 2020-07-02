#version 430 core

layout (location = 0) out vec4 out_Colour;

in vec3 position;
in vec4 colour;
in vec2 textureCoord;

uniform vec4 u_Colour;
uniform sampler2D u_TextureSampler;

void main()
{
	//out_Colour = u_Colour;
	
	out_Colour = texture(u_TextureSampler, textureCoord);
}
