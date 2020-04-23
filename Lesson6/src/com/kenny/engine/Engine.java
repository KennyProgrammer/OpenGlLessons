package com.kenny.engine;

import static org.lwjgl.opengl.GL46C.*;
import static com.kenny.engine.util.MemoryManagment.*;

import com.kenny.engine.input.Keyboard;
import com.kenny.engine.input.Mouse;

public class Engine 
{
	public static final int WIDHT = 640;
	public static final int HEIGHT = 360;
	public static final String TITLE = "Engine 0.0.1 pre-alpha";
	private EngineWindow engineWindow;
	
	public void run() 
	{
		this.init();
	}
	
	public void init() 
	{
		this.engineWindow = new EngineWindow(WIDHT, HEIGHT, TITLE);
		this.engineWindow.create();
		this.update();
	}
	
	public void update() 
	{
		float[] v_quad = {
				0.5f, 0.5f, 0,  
				-0.5f, 0.5f, 0,   
				-0.5f, -0.5f, 0,   
				0.5f, -0.5f, 0 };
		
		int [] i_quad = {0, 1, 2, 0, 2, 3};
		
		int vertexArray = glCreateVertexArrays();
		glBindVertexArray(vertexArray);

		int vertexBuffer = glCreateBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer);
		glBufferData(GL_ARRAY_BUFFER, putData(v_quad), GL_STATIC_DRAW);
		glEnableVertexAttribArray(0);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		
		int indexBuffer = glCreateBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBuffer);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, putData(i_quad), GL_STATIC_DRAW);
		
		glBindVertexArray(vertexArray);
	
		while(!this.engineWindow.isCloseRequest())
		{
			Keyboard.handleKeyboardInput();
			Mouse.handleMouseInput();
			
			glClearColor(0, 1, 1, 1);
			glClear(GL_COLOR_BUFFER_BIT);
			
			glBindVertexArray(vertexArray);
			glDrawElements(GL_TRIANGLES, i_quad.length, GL_UNSIGNED_INT, 0);
			glBindVertexArray(vertexArray);

			this.engineWindow.update();
		}
		
		this.engineWindow.destroy();
	}

	public EngineWindow getEngineWindow() 
	{
		return this.engineWindow;
	}
}
