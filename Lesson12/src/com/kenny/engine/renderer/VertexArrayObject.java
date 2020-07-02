package com.kenny.engine.renderer;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL20C.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20C.glVertexAttribPointer;
import static org.lwjgl.opengl.GL46C.*;

public class VertexArrayObject 
{
	private int id;
	private List<VertexBufferObject> vbos = new ArrayList<VertexBufferObject>();
	private List<IndexBufferObject> ibos = new ArrayList<IndexBufferObject>();
	
	public VertexArrayObject() 
	{
		this.create();
		this.bind();
	}
	
	private void create()
	{
		this.id = glCreateVertexArrays();
	}
	
	public void destroy()
	{
		for(VertexBufferObject vertexBuffer : vbos)
		{
			vertexBuffer.destroy();
		}
		
		for(IndexBufferObject indexBuffer : ibos)
		{
			indexBuffer.destroy();
		}
		
		vbos.clear();
		ibos.clear();
		
		glDeleteVertexArrays(this.id);
	}
	
	public void bind()
	{
		glBindVertexArray(this.id);
	}
	
	public void unBind()
	{
		glBindVertexArray(0);
	}
	
	private void addAttribute(int attributeId, VertexAttribute attribute,
			 int bufferStride)
	{
		glEnableVertexAttribArray(attributeId);
		glVertexAttribPointer
		(
			attributeId,
			attribute.getElementAttribSize(),
			attribute.convertedType,
			attribute.normalized ? true : false,
			bufferStride,
		    attribute.offset
		);
	}
	
	public void putBuffer(VertexBufferObject vertexBuffer)
	{
		int attributeId = 0;
		for(VertexAttribute attribute : vertexBuffer.getLayout().getAttributes())
		{
			addAttribute(attributeId, attribute, vertexBuffer.getLayout().getStride());
			attributeId++;
		}
		
		this.vbos.add(vertexBuffer);
	}
	
	public void putBuffer(IndexBufferObject indexBuffer)
	{
		this.ibos.add(indexBuffer);
	}

	public List<VertexBufferObject> getVbos() 
	{
		return vbos;
	}

	public List<IndexBufferObject> getIbos() 
	{
		return ibos;
	}
	
}
