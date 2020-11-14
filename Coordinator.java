package jrJava.memoryCardGame2;

import java.awt.Image;
import java.util.Scanner;

import javax.swing.ImageIcon;

public class Coordinator {

	public static boolean gameOver;
	
	
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Number of cards?");
		int numOfCards = scanner.nextInt();
		System.out.println("number of columns?");
		int numOfCols = scanner.nextInt();
		
		CardManager cardManager = new CardManager(numOfCards, numOfCols);
		int numOfRows = cardManager.getRowNumber();
		
		DrawingPanel panel = 
		new DrawingPanel("Memory Card Game", 200, 0, 
		numOfCols*Card.getSize() + 2*DrawingPanel.MARGIN, 
		numOfRows*Card.getSize() + 2*DrawingPanel.MARGIN);
		
		panel.setCardManager(cardManager);
		panel.addMouseListener(cardManager); 
		
		panel.repaint();
		
		
		while(!gameOver){
			
			cardManager.applyGameLogic();
			if(cardManager.needToPaint()) panel.repaint();
			
			try {
				Thread.sleep(50); 
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}

}


























