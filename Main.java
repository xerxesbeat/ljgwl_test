package net.xerxesbeat.lwjgl_default;

import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwTerminate;

import org.lwjgl.glfw.GLFWErrorCallback;

public class Main
{
	public static void main ( String [] args )
	{
		// init
		if ( !glfwInit() )
			throw new IllegalStateException ( "Unable to initialize GLFW" );
		GLFWErrorCallback.createPrint( System.err ).set();
		try
		{
			Window test = new Test ( "test", 800, 600, false );
			Window test2 = new Test ( "test2", 800, 600, false );
			test.init();
			test2.init();
			while ( true )
			{
				int i = 0;
				if ( !test.closed() )
				{
					test.pump();
					i++;
				}
				if ( !test2.closed() )
				{
					test2.pump();
					i++;
				}
				if ( i <= 0 )
					break;
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		finally
		{
			// cleanup
			glfwTerminate();
			glfwSetErrorCallback( null ).free();
		}
	}
}