package com.kenny.engine.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.channels.FileChannel;

import org.joml.Matrix2f;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import static org.lwjgl.system.MemoryStack.*;

public class MemoryManagment 
{
	public static ByteBuffer resourceToByteBuffer(final String resource) throws IOException
	{
		File file = new File(resource);
		
		FileInputStream fileInputStream = new FileInputStream(file);
		FileChannel fileChannel = fileInputStream.getChannel();
		
		ByteBuffer buffer = BufferUtils.createByteBuffer((int) fileChannel.size() + 1);
		
		while (fileChannel.read(buffer) != -1) {
			;
		}
		
		fileInputStream.close();
		fileChannel.close();
		buffer.flip();
		
		return buffer;
	}
	
	public static IntBuffer putData(int[] data)
	{
		return (IntBuffer) stackGet().mallocInt(4 * data.length).put(data).flip();
	}
	
	public static IntBuffer putData(int size, int... data)
	{
		return (IntBuffer) stackGet().mallocInt(size).put(data).flip();
	}
	
	public static FloatBuffer putData(float[] data)
	{
		return (FloatBuffer) stackGet().mallocFloat(4 * data.length).put(data).flip();
	}
	
	public static FloatBuffer putData(int size, float... data)
	{
		return (FloatBuffer) stackGet().mallocFloat(size).put(data).flip();
	}
	
	public static DoubleBuffer putData(double[] data)
	{
		return (DoubleBuffer) stackGet().mallocDouble(8 * data.length).put(data).flip();
	}
	
	public static LongBuffer putData(long[] data)
	{
		return (LongBuffer) stackGet().mallocLong(8 * data.length).put(data).flip();
	}
}
