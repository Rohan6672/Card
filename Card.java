package jrJava.memoryCardGame2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;


public class Card {

	private static Image backImage; 
	private static int size;
	private static int padding, inset;
	private static Color borderColor, paddingBorderColor;
		
	private Image image; // hidden image
	private int x, y; // top, left
	private boolean matched; 
	private boolean shouldReveal;
	
	
	static{
		backImage = new ImageIcon("jrJava/memoryCardGame2/backside.png").getImage();
		size = 100;
		inset = 10;
		padding = 5;
		borderColor = Color.gray;
		paddingBorderColor = Color.black;
	}
	
	
	public Card(Image image, int x, int y){
		this.image = image;
		this.x = x;
		this.y = y;
	}
	
	
	public static int getSize(){ return size; }
	
	
	public boolean isSelected(int mx, int my){
		return mx>=x && mx<=x+size && my>=y && my<=y+size;
	}
	
	public boolean equals(Object o){
		Card theOther = (Card) o;
		return this.image==theOther.image;
	}
	
	
	
	public void show(){ shouldReveal = true; }
	public void hide(){ shouldReveal = false; }
	public void setMatched(){ matched = true; }
	public void setUnMatched(){ matched = false; }
	public boolean isMatched(){ return matched; }
	
	
	public void draw(Graphics g){
		
		if(matched) return;
		
		if(shouldReveal){
			g.drawImage(image, x+inset, y+inset, size-2*inset, size-2*inset, null);
		}
		else {
			g.drawImage(backImage, x+inset, y+inset, size-2*inset, size-2*inset, null);
		}
		
		g.setColor(borderColor);
		g.drawRect(x, y, size, size);
		
		g.setColor(paddingBorderColor);
		g.drawRect(x+padding, y+padding, size-2*padding, size-2*padding);
	}
	
} 














