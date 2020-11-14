package jrJava.memoryCardGame2;

public class Temp {

	public static void main(String[] args) {
		
		//System.out.println( 2>3? 11:9 ); 
		
		int a = 15;
		int b = 11;
		int c = 20;
		
		//int c = a-1>b+2? a:b;
		//System.out.println(c);
		 
		int bigger = a>b? a:b; //int bigger = Math.max(a, b);  
		
		int biggest = (a>b? a:b)>c? (a>b? a:b):c;
		System.out.println(biggest);
	}

}
 