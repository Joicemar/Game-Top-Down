package gs;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Window extends JFrame {

	
	
	public Window( int widht, int height, String title, Game game ) {
		JFrame frame = new JFrame( title );
		frame.setPreferredSize( new Dimension ( widht, height) );
		frame.setMaximumSize  ( new Dimension ( widht, height) );
		frame.setMinimumSize  ( new Dimension ( widht, height) );
		
		frame.add(game);
		frame.setResizable( false );
		frame.setDefaultCloseOperation( frame.EXIT_ON_CLOSE );
		frame.setLocationRelativeTo( null );
		frame.setVisible( true );
	}
	
	

}
