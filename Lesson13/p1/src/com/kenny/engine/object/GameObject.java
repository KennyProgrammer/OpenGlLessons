package com.kenny.engine.object;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import com.kenny.engine.renderer.Texture;
import com.kenny.engine.renderer.VertexArrayObject;

public class GameObject 
{
	private VertexArrayObject vertexArray;
	private Texture texture;
	
	protected Vector3f position;
	protected Vector3f rotation;
	protected Vector3f scale;
	
	public GameObject(Vector3f position, Vector3f rotation, Vector3f scale) 
	{
		this.position = new Vector3f(position);
		this.rotation = new Vector3f(rotation);
		this.scale = new Vector3f(scale);
	}
	
	public GameObject setModel(VertexArrayObject vertexArray)
	{
		this.vertexArray = vertexArray;
		return this;
	}
	
	public GameObject setTexture(Texture texture)
	{
		this.texture = texture;
		return this;
	}

	public VertexArrayObject getVertexArray()
	{
		return vertexArray;
	}

	public Texture getTexture() 
	{
		return texture;
	}

	public Vector3f getPosition() 
	{
		return position;
	}
	
	public Vector3f getRotation() 
	{
		return rotation;
	}
	
	public Vector3f getScale() 
	{
		return scale;
	}
}
