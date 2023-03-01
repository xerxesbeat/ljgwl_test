package net.xerxesbeat.lwjgl_default;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

public class Test extends Window
{
	public Test ( String title, int width, int height, boolean resizable )
	{
		super( title, width, height, resizable );
	}

	@Override
	public void draw ()
	{
		glClearColor( 0f, 0f, 0f, 1f );
		glClear( GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT );
		// TODO Auto-generated method stub
		
	}
}
