package com.kenny.engine;

import static org.lwjgl.opengl.GL46C.*;

import org.joml.Vector4f;

import com.kenny.engine.input.Keyboard;
import com.kenny.engine.input.Mouse;
import com.kenny.engine.renderer.BufferLayout;
import com.kenny.engine.renderer.IndexBufferObject;
import com.kenny.engine.renderer.Shader;
import com.kenny.engine.renderer.Texture;
import com.kenny.engine.renderer.VertexArrayObject;
import com.kenny.engine.renderer.VertexAttribute;
import com.kenny.engine.renderer.VertexAttribute.ShaderDataType;
import com.kenny.engine.renderer.VertexBufferObject;

public class Engine 
{
	public static final int WIDHT = 640;
	public static final int HEIGHT = 360;
	public static final String TITLE = "Engine 0.0.1 pre-alpha";
	private EngineWindow engineWindow;
	public Shader shader;
	public Texture texture;
	
	public void run() 
	{
		this.init();
	}
	
	public void init() 
	{
		this.engineWindow = new EngineWindow(WIDHT, HEIGHT, TITLE);
		this.engineWindow.create();
		this.shader = new Shader("/shaders/Rectangle.vert", "/shaders/Rectangle.frag");
		this.texture = new Texture("res/textures/icon_breath.png");
		
		this.shader.bind();
		this.shader.setUnifromInt("u_TextureSampler", 0);
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		this.update();
	}
	
	public void update() 
	{
		float[] vertices =
			{
//                  positions          colours               uv
				0.30f,  0.5f, 0,  1.0f, 1.0f, 0.0f, 1.0f,  1.0f, 0.0f,
			   -0.30f,  0.5f, 0,  1.0f, 0.0f, 1.0f, 1.0f,  0.0f, 0.0f,
			   -0.30f, -0.5f, 0,  1.0f, 1.0f, 0.0f, 1.0f,  0.0f, 1.0f,
			    0.30f, -0.5f, 0,  0.0f, 1.0f, 0.0f, 1.0f,  1.0f, 1.0f
			};
		
		int [] indices = {0, 1, 2, 0, 2, 3};
		
		VertexArrayObject vertexArray = new VertexArrayObject();
		VertexBufferObject vertexBuffer = new VertexBufferObject(vertices);
		vertexBuffer.setLayout(new BufferLayout
		(
				new VertexAttribute("attrib_Position", ShaderDataType.t_float3),
				new VertexAttribute("attrib_Colour", ShaderDataType.t_float4),
				new VertexAttribute("attrib_TextureCoord", ShaderDataType.t_float2)
		));
		vertexArray.putBuffer(vertexBuffer);
		IndexBufferObject indexBuffer = new IndexBufferObject(indices);
		vertexArray.putBuffer(indexBuffer);
		
		Vector4f colour = new Vector4f(0, 0, 0, 1);
		
		while(!this.engineWindow.isCloseRequest())
		{
			Keyboard.handleKeyboardInput();
			Mouse.handleMouseInput();
			
			glClearColor(0, 1, 1, 1);
			glClear(GL_COLOR_BUFFER_BIT);
			
			float speed = 0.01f;
			
			colour.x += speed;
				
			if(colour.x >= 1)
				colour.y += speed;
				
			if(colour.y >= 1)
				colour.z += speed;
				
			if(colour.z >= 1)
			{
				colour.set(0.0f);
			}
		
			vertexArray.bind();
			this.texture.bind();
			this.shader.bind();
			this.shader.setUnifromVec4("u_Colour", colour);
			glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);
			this.shader.unBind();
			this.texture.unBind();
			vertexArray.unBind();
			
			this.engineWindow.update();
		}
		
		this.engineWindow.destroy();
	}
	

	public EngineWindow getEngineWindow() 
	{
		return this.engineWindow;
	}
}
