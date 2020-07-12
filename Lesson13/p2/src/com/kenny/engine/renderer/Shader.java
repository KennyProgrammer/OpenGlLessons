package com.kenny.engine.renderer;

import static org.lwjgl.opengl.GL46C.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

public class Shader 
{
	public int programId;
	
	public Shader(String vsSrc, String fsSrc) 
	{
		Map<Integer, String> shaderSources = new HashMap<Integer, String>(2);
		shaderSources.put(1, this.readFile(vsSrc));
		shaderSources.put(2, this.readFile(fsSrc));
		this.compile(shaderSources);
	}
	
	public void compile(Map<Integer, String> shaderSources)
	{
		int program = glCreateProgram();
		
		List<Integer> shaderIds = new ArrayList<Integer>();
		int shaderIdIdxs = 1;
		
		for(int i = 0; i < shaderSources.size(); i++)
		{
			int type = i == 0 ? GL_VERTEX_SHADER : i == 1 ? GL_FRAGMENT_SHADER : - 1;
			String source = shaderSources.get(shaderIdIdxs);
			
			int shader = glCreateShader(type);
			glShaderSource(shader, source);
			glCompileShader(shader);
			
			int isCompiled = 0;
			isCompiled = glGetShaderi(shader, GL_COMPILE_STATUS);
			if(isCompiled == GL_FALSE)
			{
				int maxLength = 0;
				maxLength = glGetShaderi(shader, GL_INFO_LOG_LENGTH);
				
				String infoLog = "";
				infoLog = glGetShaderInfoLog(shader, maxLength);
				glDeleteShader(shader);
				
				String st = type == 0 ? "Vertex Shader" : "Fragment Shader";
				System.out.println("Cannot compile " + st + ": " + infoLog);
				System.exit(-1);
			}
			
			glAttachShader(program, shader);
			shaderIdIdxs++;
		}
		
		glLinkProgram(program);
		
		int isLinked = 0;
		isLinked = glGetProgrami(program, GL_LINK_STATUS);
		if(isLinked == GL_FALSE)
		{
			int maxLength = 0;
			maxLength = glGetProgrami(program, GL_INFO_LOG_LENGTH);
			
			String infoLog = "";
			infoLog = glGetProgramInfoLog(program, maxLength);
			
			for(int shaderId : shaderIds)
			{
				glDetachShader(program, shaderId);
			}
			
			for(int shaderId : shaderIds)
			{
				glDeleteShader(shaderId);
			}
		
			System.out.println("Cannot link shader program! ");
			System.out.println(infoLog);
			System.exit(-1);

		}
		
		for(int shaderId : shaderIds)
		{
			glDetachShader(program, shaderId);
		}
		
		this.programId = program;
	}
	
	
	public String readFile(String file)
	{
		boolean appendSlashes = false;
		boolean returnInOneLine = false;
		StringBuilder shaderSource = new StringBuilder();
        try
        {
        	InputStream in = Class.class.getResourceAsStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while((line = reader.readLine())!=null)
            {
                shaderSource.append(line);
            
                if(appendSlashes) shaderSource.append("//");
                if(!returnInOneLine) shaderSource.append("\n");
            }
            reader.close();
            
            return shaderSource.toString();
        }
        catch(IOException e)
        {
        	System.out.println("This file '" + file + "' cound be read!");
            e.printStackTrace();
            Runtime.getRuntime().exit(-1);
        }
        
        return "[Reading Error]: This file '" + file + "' cound be read!";
	}
	
	public void bind()
	{
		glUseProgram(this.programId);
	}
	
	public void unBind()
	{
		glUseProgram(0);
	}
	
	public void destroy()
	{
		glDeleteProgram(this.programId);
	}
	
	public void setUnifromInt(String name, int value)
	{
		glUniform1i(glGetUniformLocation(programId, name), value);
	}
	
	public void setUnifromInt2(String name, int x, int y)
	{
		glUniform2i(glGetUniformLocation(programId, name), x, y);
	}
	
	public void setUnifromInt3(String name, int x, int y, int z)
	{
		glUniform3i(glGetUniformLocation(programId, name), x, y, z);
	}
	
	public void setUnifromInt4(String name, int x, int y, int z, int w)
	{
		glUniform4i(glGetUniformLocation(programId, name), x, y, z, w);
	}
	
	public void setUnifromFloat(String name, float value)
	{
		glUniform1f(glGetUniformLocation(programId, name), value);
	}
	
	public void setUnifromFloat2(String name , float x, float y)
	{
		glUniform2f(glGetUniformLocation(programId, name), x, y);
	}
	
	public void setUnifromFloat3(String name, float x, float y, float z)
	{
		glUniform3f(glGetUniformLocation(programId, name), x, y, z);
	}
	
	public void setUnifromFloat4(String name, float x, float y, float z, float w)
	{
		glUniform4f(glGetUniformLocation(programId, name), x, y, z, w);
	}
	
	public void setUniformBoolean(String name, boolean value)
	{
		glUniform1i(glGetUniformLocation(programId, name), value == true ? 1 : 0);
	}

	public void setUnifromVec2(String name, Vector2f value)
	{
		glUniform2f(glGetUniformLocation(programId, name), value.x, value.y);
	}
	
	public void setUnifromVec3(String name, Vector3f value)
	{
		glUniform3f(glGetUniformLocation(programId, name), value.x, value.y, value.z);
	}

	public void setUnifromVec4(String name, Vector4f value)
	{
		glUniform4f(glGetUniformLocation(programId, name), value.x, value.y, value.z, value.w);
	}
	
	public void setUniformMat4(String name, Matrix4f matrix)
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(4 * 4);
		matrix.get(buffer);
		
		glUniformMatrix4fv(glGetUniformLocation(programId, name), false, buffer);
	}
}
