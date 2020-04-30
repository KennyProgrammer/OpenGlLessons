package com.kenny.engine.main;

import org.joml.Math;
import org.joml.Vector3f;
import org.joml.Vector4f;

import com.kenny.engine.Engine;

public class Main 
{
	 static boolean absEqualsOne(float r) {
	        return (Float.floatToRawIntBits(r) & 0x7FFFFFFF) == 0x3F800000;
	    }
	 
	 private static Vector3f rotateAxisInternal(Vector3f v, float angle, float aX, float aY, float aZ, Vector3f dest) {
	        float hangle = angle * 0.5f;
	        float sinAngle = Math.sin(hangle);
	        float qx = aX * sinAngle, qy = aY * sinAngle, qz = aZ * sinAngle;
	        float qw = Math.cosFromSin(sinAngle, hangle);
	        float w2 = qw * qw, x2 = qx * qx, y2 = qy * qy, z2 = qz * qz, zw = qz * qw;
	        float xy = qx * qy, xz = qx * qz, yw = qy * qw, yz = qy * qz, xw = qx * qw;
	        float x = v.x, y = v.y, z = v.z;
	        dest.x = (w2 + x2 - z2 - y2) * x + (-zw + xy - zw + xy) * y + (yw + xz + xz + yw) * z;
	        dest.y = (xy + zw + zw + xy) * x + ( y2 - z2 + w2 - x2) * y + (yz + yz - xw - xw) * z;
	        dest.z = (xz - yw + xz - yw) * x + ( yz + yz + xw + xw) * y + (z2 - y2 - x2 + w2) * z;
	        return dest;
	    }
	 
	
	 public static Vector3f rotateAxis(Vector3f v, float angle, float aX, float aY, float aZ, Vector3f dest) {
	        if (aY == 0.0f && aZ == 0.0f && aX == 1)
	            return v.rotateX(aX * angle, dest);
	        else if (aX == 0.0f && aZ == 0.0f && aY == 1)
	            return v.rotateY(aY * angle, dest);
	        else if (aX == 0.0f && aY == 0.0f && aZ == 1)
	            return v.rotateZ(aZ * angle, dest);
	        return rotateAxisInternal(v, angle, aX, aY, aZ, dest);
	    }
	 
	public static void main(String... args)
	{
		new Engine().run();
		
		//Vector3f v = new Vector3f( 1.0f, 1.0f, 1.0f );
		//Vector3f v1 = new Vector3f( 1.0f, 2.0f, 4.0f );
		
		//Vector4f v11 = new Vector4f( 1.0f, 2.0f, 4.0f, 1.0f ).angle(v);
		
		//System.out.println(v1.rotateX(40.0f));
		//System.out.println(v1.rotateY(40.0f));
		//System.out.println(v1.rotateZ(40.0f));
		//System.out.println(v1.rotateAxis(40.0f, v.x, v.y, v.z));
		
		//v1.half(v);
		//System.out.println("x: "+  v1.x + ", y: " + v1.y + ", z: " + v1.z);
	}
}
