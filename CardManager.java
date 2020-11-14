package jrJava.memoryCardGame2;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;

import resources.SoundPlayer;

public class CardManager implements MouseListener {
	

	private Card[][] cards;
	private int row, col;
	private boolean oneSelected, twoSelected;
	private Card firstSelected, secondSelected;
	private long timeTwoCardsSelected;
	private SoundPlayer tick, chime, buzz;
	private boolean needToPaint;
	private int score;
	private Font font;
	

	private String[] imageNames = {
			"apple", "bank", "basketball", "bubble_blue", 
			"bubble_green", "bubble_red", "building", "cat",
			"cheese", "denture", "dog", "hockey_stick", 
			"keys", "phone", "pizza", "santa",
			"soccer_ball", "sock", "toilet_bowl", "toilet_paper",
			"xmas_tree"
	};



	public CardManager(int numberOfCards, int col){

		this.col = col;

		if(numberOfCards%2==1) numberOfCards++;
		if(numberOfCards>imageNames.length*2) numberOfCards = imageNames.length*2;

		//row = numberOfCards/col; 
		//if(numberOfCards%col!=0) row++;

		row = numberOfCards/col + (numberOfCards%col==0? 0:1);

		int i, j;
		Image image;
		
		// Create 2D array (array of arrays)
		cards = new Card[row][];
		for(i=0; i<cards.length; i++){
			if(i!=cards.length-1) cards[i] = new Card[col];
			//else cards[i] = new Card[numberOfCards%col];  // WRONG!!!
			else cards[i] = new Card[numberOfCards-col*(cards.length-1)]; 
		}

		// Prepare Image[] of numberOfCards size.
		Image[] images = new Image[numberOfCards];
		for(i=0; i<numberOfCards/2; i++){
			image = new ImageIcon("jrJava/memoryCardGame2/" + imageNames[i] + ".png").getImage();
			images[i] = image;
			images[numberOfCards/2 + i] = image;
		}
		
		// shuffle the images.
		Image temp;
		int rand;
		for(i=0; i<images.length; i++){
			rand = (int)(Math.random()*images.length);
			// swap the references in [i] and [rand].
			temp = images[i];
			images[i] = images[rand];
			images[rand] = temp;
		}
		
		
		// Create Cards.
		int x, y;
		for(i=0; i<cards.length; i++){
			// cards[i] will be the secondary array's reference.
			for(j=0; j<cards[i].length; j++){
				image = images[i*col + j];
				x = 50 + j*Card.getSize();
				y = 50 + i*Card.getSize();
				cards[i][j] = new Card(image, x, y);
				//cards[i][j].show();
			}
		}
		
		tick = new SoundPlayer("jrJava/memoryCardGame2/tick.wav");
		chime = new SoundPlayer("jrJava/memoryCardGame2/chime.wav");
		buzz = new SoundPlayer("jrJava/memoryCardGame2/buzz.wav");
		
		font = new Font("Arial", Font.BOLD, 14);
	}

	
	public int getRowNumber(){ return row; }
	public boolean needToPaint(){ return needToPaint; }
	
	
	public void draw(Graphics g){
		int i, j;
		for(i=0; i<cards.length; i++){
			for(j=0; j<cards[i].length; j++){
				cards[i][j].draw(g); 
			}
		}
		
		g.setColor(Color.blue);
		g.setFont(font);
		g.drawString("score: " + score, 100, 40);
		
		if(Coordinator.gameOver){
			g.setFont(new Font("Courier", Font.BOLD, 8*col));
			int yPos = 100;
			g.setColor(Color.orange);
			g.drawString("Congratulations!", 50, yPos);
			yPos += 30*row;
			g.setColor(Color.cyan);
			g.drawString("Great Job", 50, yPos);
			yPos += 30*row;
			g.setColor(Color.green);
			g.drawString("Game Over", 50, yPos);
		}
		
		needToPaint = false;
	}
	
	
	public void showAll(){
		int i, j;
		for(i=0; i<cards.length; i++){
			for(j=0; j<cards[i].length; j++){
				cards[i][j].show(); 
			}
		}
	}
	
	
	public void hideAll(){
		int i, j;
		for(i=0; i<cards.length; i++){
			for(j=0; j<cards[i].length; j++){
				cards[i][j].hide(); 
			}
		}
	}


	public void applyGameLogic(){

		if(!twoSelected || System.currentTimeMillis()<timeTwoCardsSelected+2000) return;
		
		boolean checkAllCardsMatched = false;
		
		if(firstSelected.equals(secondSelected)){
			firstSelected.setMatched();
			secondSelected.setMatched();
			chime.play();
			needToPaint = true;
			score += 100;
			
			checkAllCardsMatched = true;
		}
		else {
			firstSelected.hide();
			secondSelected.hide();
			buzz.play();
			needToPaint = true;
			score -= 50;
		}
		
		oneSelected = false;
		twoSelected = false;
		firstSelected = null;
		secondSelected = null;
		
		if(!checkAllCardsMatched) return;
		
		int i, j;
		for(i=0; i<cards.length; i++){
			for(j=0; j<cards[i].length; j++){
				if(!cards[i][j].isMatched()) return;
			}
		}
		
		Coordinator.gameOver = true;
	}
	
	
	
	public void mousePressed(MouseEvent e) {
		
		if(twoSelected) return;
		
		int mx = e.getX();
		int my = e.getY();
		
		int i, j;
		for(i=0; i<cards.length; i++){
			for(j=0; j<cards[i].length; j++){
				
				if(cards[i][j].isSelected(mx, my) && cards[i][j]!=firstSelected && !cards[i][j].isMatched()){
					cards[i][j].setUnMatched();
					cards[i][j].show();
					tick.play();
					needToPaint = true;
					
					if(!oneSelected){ // if currently, no card has been selected. 
						oneSelected = true;
						firstSelected = cards[i][j];
					}
					else { // if currently, 1 card has been selected.
						oneSelected = false;
						twoSelected = true;
						secondSelected = cards[i][j];
						
						timeTwoCardsSelected = System.currentTimeMillis();
					}
					return;
				}
			}
		}
		
	}


	
	public void mouseClicked(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	
	
}

















/*

 */