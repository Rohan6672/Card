package jrJava.memoryCardGame2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class DrawingPanel extends JPanel {

	public static final int MARGIN = 50;
	private CardManager cardManager;
	
	
	public DrawingPanel(String title, int x, int y, int width, int height){
		JFrame frame = new JFrame(title);
		frame.setLocation(x, y);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setPreferredSize(new Dimension(width, height));
		
		frame.getContentPane().add(this); 
		
		frame.pack();
		frame.setVisible(true);
	}
	
	
	public void setCardManager(CardManager cardManager){ this.cardManager = cardManager; }
	

	
	public void paintComponent(Graphics g){
		
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		
		g.setColor(Color.white);
		g.fillRect(0,  0, getWidth(), getHeight());
		
		if(cardManager!=null) cardManager.draw(g); 
	}
	
}






 


















