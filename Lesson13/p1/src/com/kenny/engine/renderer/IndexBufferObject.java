package com.kenny.engine.renderer;

import static org.lwjgl.opengl.GL46C.*;
import com.kenny.engine.util.MemoryManagment;

public class IndexBufferObject 
{
	private int id;
	private int[] data;
	private int usage;
	
	public IndexBufferObject(int... data) 
	{
		this.data = data;
		this.usage = GL_STATIC_DRAW;
		this.create();
		this.bind();
		this.putData();
	}
	
	private void create()
	{
		this.id = glCreateBuffers();
	}
	
	public void destroy()
	{
		glDeleteBuffers(this.id);
	}
	
	private void putData()
	{
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, MemoryManagment.putData(this.data), this.usage);
	}
	
	public void bind()
	{
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this.id);
	}
	
	public void unBind()
	{
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
	}

	public int[] getData() 
	{
		return data;
	}
	
	public int getCount()
	{
		return data.length;
	}

	public int getUsage() 
	{
		return usage;
	}
}
