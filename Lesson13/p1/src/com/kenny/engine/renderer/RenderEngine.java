package com.kenny.engine.renderer;

import static org.lwjgl.opengl.GL46C.*;

import com.kenny.engine.object.GameObject;

public class RenderEngine 
{
	public static void init()
	{
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}
	
	public static void begin(Shader shader)
	{
		glClearColor(0, 1, 1, 1);
		glClear(GL_COLOR_BUFFER_BIT);
		
		shader.bind();
	}
	
	public static void end(Shader shader)
	{
		shader.unBind();
	}
	
	public static void renderGameObject(GameObject gameObject, Shader shader)
	{
		if(gameObject.getVertexArray() != null)
		{
			if(gameObject.getTexture() != null)
				gameObject.getTexture().bind();
			
			gameObject.getVertexArray().bind();
			shader.bind();
			
			int indexCount = gameObject.getVertexArray().getIbos().get(0).getCount();
			glDrawElements(GL_TRIANGLES, indexCount, GL_UNSIGNED_INT, 0);
			gameObject.getVertexArray().unBind();
			
			if(gameObject.getTexture() != null)
				gameObject.getTexture().unBind();
		}
	}
	
	public static void destory()
	{
		glDisable(GL_BLEND);
	}
}
