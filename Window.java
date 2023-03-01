package net.xerxesbeat.lwjgl_default;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.MemoryStack;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.IntBuffer;

public abstract class Window
{
	public abstract void draw ();

	public final int width ()
	{
		MemoryStack stack = stackPush();
		IntBuffer pWidth = stack.mallocInt( 1 );
		IntBuffer pHeight = stack.mallocInt( 1 );
		glfwGetWindowSize( window, pWidth, pHeight );
		stackPop();
		return pWidth.get();
	}

	public final int height ()
	{
		MemoryStack stack = stackPush();
		IntBuffer pWidth = stack.mallocInt( 1 );
		IntBuffer pHeight = stack.mallocInt( 1 );
		glfwGetWindowSize( window, pWidth, pHeight );
		stackPop();
		return pHeight.get();
	}

	private boolean resizable = false;
	private int width, height;
	private String title = "";

	private long window;

	public Window ( String title, int width, int height, boolean resizable )
	{
		this.title = title;
		this.width = width;
		this.height = height;
		this.resizable = resizable;
	}

	private GLCapabilities capabilities;

	protected void init ()
	{
		glfwDefaultWindowHints();
		glfwWindowHint( GLFW_VISIBLE, GLFW_FALSE );
		glfwWindowHint( GLFW_RESIZABLE, this.resizable ? GLFW_TRUE : GLFW_FALSE );
		window = glfwCreateWindow( this.width, this.height, this.title, NULL, NULL );
		if ( window == NULL )
			throw new RuntimeException ( "Failed to greate the GLFW window" );
		try ( MemoryStack stack = stackPush() )
		{
			IntBuffer pWidth = stack.mallocInt( 1 );
			IntBuffer pHeight = stack.mallocInt( 1 );
			glfwGetWindowSize( window, pWidth, pHeight );

			// TODO fix this for multi-monitor
			GLFWVidMode vidmode = glfwGetVideoMode( glfwGetPrimaryMonitor() );

			glfwSetWindowPos( window, ( vidmode.width() - pWidth.get( 0 ) ) / 2, ( vidmode.height() - pHeight.get( 0 ) ) / 2 );
		}
		glfwMakeContextCurrent( window );
		glfwShowWindow( window );
		capabilities = GL.createCapabilities();
	}

	protected final void pump ()
	{
		GL.setCapabilities( capabilities );
		glfwPollEvents();
		draw();
		glfwSwapBuffers( window );
	}

	private boolean open = true;

	protected boolean closed ()
	{
		// TODO be less cringe
		if ( glfwWindowShouldClose( window ) )
		{
			if ( open )
				glfwDestroyWindow( window ); // probably horribad to hook this here
			open = false;
		}
		return !open;
	}
}
