package com.kenny.engine;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;
import org.joml.Vector4f;

import com.kenny.engine.input.Keyboard;
import com.kenny.engine.input.Mouse;
import com.kenny.engine.object.GameObject;
import com.kenny.engine.renderer.BufferLayout;
import com.kenny.engine.renderer.IndexBufferObject;
import com.kenny.engine.renderer.RenderEngine;
import com.kenny.engine.renderer.Shader;
import com.kenny.engine.renderer.Texture;
import com.kenny.engine.renderer.VertexArrayObject;
import com.kenny.engine.renderer.VertexAttribute;
import com.kenny.engine.renderer.VertexAttribute.ShaderDataType;
import com.kenny.engine.renderer.VertexBufferObject;

public class Engine 
{
	public static Engine instance;
	public static final int WIDHT = 640;
	public static final int HEIGHT = 360;
	public static final String TITLE = "Engine 0.0.1 pre-alpha";
	private EngineWindow engineWindow;
	public Shader shader;
	public Texture texture;
	
	public Engine() 
	{
		instance = this;
	}
	
	public void run() 
	{
		this.init();
	}
	
	public void init() 
	{
		this.engineWindow = new EngineWindow(WIDHT, HEIGHT, TITLE);
		this.engineWindow.create();

		RenderEngine.init();

		this.shader = new Shader("/shaders/Rectangle.vert", "/shaders/Rectangle.frag");
		this.texture = new Texture("res/textures/logo_256x256.png");
		
		this.shader.bind();
		this.shader.setUnifromInt("u_TextureSampler", 0);
		
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
		
		GameObject gameObject = new GameObject(new Vector3f(0,0,0), new Vector3f(0,0,0), new Vector3f(1,1,1));
		gameObject.setModel(vertexArray);
		gameObject.setTexture(texture);
		
		while(!this.engineWindow.isCloseRequested())
		{
			Keyboard.handleKeyboardInput();
			Mouse.handleMouseInput();
			
			RenderEngine.begin(shader);
			{
				RenderEngine.renderGameObject(gm, shader);
			}
			RenderEngine.end(shader);
			
			this.engineWindow.swapBuffers();
		}

		RenderEngine.destory();
		this.engineWindow.destroy();
	}
	
	public static Engine get()
	{
		return instance;
	}
	
	public static void main(String[] args) 
	{
		new Engine().run();
	}

	public EngineWindow getEngineWindow() 
	{
		return this.engineWindow;
	}
}
