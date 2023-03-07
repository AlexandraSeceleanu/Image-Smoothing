package Test;

import java.io.IOException;
import java.util.Scanner;

import ImageSmoothing.Smoothing;

public class Main {
	static {
		System.out.println("Image Smoothing! ");   //bloc static de initializare 
	}
	
	 static void display(String... values){   //varargs
		 for(String s:values){  
			   System.out.println(s);  
		 }  
		 } 
	
	public static void main(String[] args) throws IOException{
		display("Alegeti metoda de introducere a argumentelor: 1 sau 0"); 
		// 1 - preluarea argumentelor din linia de comanda
		// 0 - preluarea argumentelor de la tastatura
		Scanner scanner = new Scanner(System.in);
		int x = scanner.nextInt();
		if(x == 1){
		try{
			String in = args[0]; // locatia imaginii
			String out = args[1]; // locatia pentru imaginea de output
			
			Smoothing mySmoothing = new Smoothing(in, out); // declar un obiect de tip Smoothing
			mySmoothing.startSmoothing(); // start aplicatie
		} catch (ArrayIndexOutOfBoundsException exp) {
			System.out.println("Nu sunt destule argumente.");
			// lipseste locatia fisierului de in sau out
		}
		
	 }
		else
		{
			//citire path fisier de editat
			display("Calea fisierului de unde se citeste poza","Fisier(BMP):");
			Scanner scanner1 = new Scanner(System.in);
			String in = scanner1.nextLine();
			
			//citire path fisier pentru scrierea imaginii procesate
			display("Calea fisierului unde se va face scrierea","Fisier(BMP):");
			Scanner scanner2 = new Scanner(System.in);
			String out = scanner2.nextLine();
			
			Smoothing mySmoothing = new Smoothing(in, out); // declar un obiect de tip Smoothing
			mySmoothing.startSmoothing(); // start aplicatie
			
			scanner.close(); // opresc scanner-ul pentru metoda de preluare a argumentelor
			scanner1.close(); // opresc scanner-ul pentru citirea path-ului fisierului de editat
			scanner2.close(); // opresc scanner-ul pemtru citirea path-ului fisierului in care scriu imaginea procesata
		}
	}

}