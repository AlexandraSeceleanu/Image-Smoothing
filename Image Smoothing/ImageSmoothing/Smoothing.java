package ImageSmoothing;

import java.io.File;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.RescaleOp;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Smoothing implements Interface{
	
	BufferedImage bufferedImage; // creez o fereastra in care afisez poza procesata
    String path, pathOut;       
    float offset = 1000.0f;
    RescaleOp rescale;
    ImageIcon icon;
    ImageIcon icon1;
    JLabel picLabel=new JLabel();
    JLabel picLabel1=new JLabel();
    File fileIn, fileOut;
	
	private long timeC; // contor timp citire
	private long timeP; // contor timp procesare
	private long timeS; // contor timp scriere
   
	
	private String inFile; // locatia imaginii initiale
	private String outFile; // locatia pentru imaginea procesata

	
	public static BufferedImage imagineInit = null; // imaginea citita din fisier
	public static BufferedImage imaginePentruProcesare = null; // imaginea inainte de procesare, dar dupa
	// trimiterea acesteia pe bucati
	
	public static BufferedImage imagineProcesata = null; // imaginea finala, dupa procesare
	
	Smoothing(){
		System.out.println("Am inclus polimorfismul");
	}
	
	public Smoothing(String in, String out) {
		inFile = in;// se primeste locatia imaginii inainte de zoom
		outFile = out;// se primeste locatia imaginii dupa zoom
	}
	
	
	public void startSmoothing() {
		File file = null;// declaram un fisier
		
		JFrame jf=new JFrame();
	       JPanel jp=new JPanel();        
	       jf.add(jp);
	       jp.add(picLabel1);
	       jp.add(picLabel);
	       
	   
	    // proprietatile ferestrei in care afisam imaginea prelucrata
	       
	    jf.setVisible(true);
	    jf.setSize(700,300);
	    jf.setLocation(150,50);
	    jf.setTitle("Image Smoothing");
	    jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
        // partea de citire a imaginii
	    
		timeC = System.currentTimeMillis(); //variabila folosita pentru a prelua timpul curent
		
		try {
			file = new File(inFile); //deschid fisierul de input
			imagineInit = ImageIO.read(file); // citesc imaginea initiala
			
		} catch(IOException exp) {
			System.out.println("Error: " + exp); // eroare
		}
		
		timeC = System.currentTimeMillis() - timeC;  //calculeaza timpul de citire
		
		imaginePentruProcesare = new BufferedImage(
		imagineInit.getWidth(), imagineInit.getHeight(), imagineInit.getType());
		
		// declar obiecte de tip Buffer, Producer si Consumer
		
		Buffer buffer = new Buffer(); 
		Producer producer = new Producer(buffer); 
		Consumer consumer = new Consumer(buffer); 
		
		producer.start(); // start thread pentru Producer
		consumer.start(); // start thread pentru Consumer
		
		try {
			consumer.join(); // unesc thread-ul curent cu cel al Consumer-ului 
			// procesarea imaginii asteapta pana cand Consumer-ul isi termina executia
		} catch (InterruptedException exp) {
			exp.printStackTrace(); // eroare la unire
		}
		
	
		// procesarea imaginii

		timeP = System.currentTimeMillis(); //variabila folosita pentru a prelua timpul curent
		        
		// creare kernel prin instantierea clasei Kernel
		Kernel kernel = new Kernel(3, 3, new float[] { 1f / 9f, 1f / 9f, 1f / 9f, 1f / 9f, 1f / 9f, 1f / 9f, 1f / 9f, 1f / 9f, 1f / 9f });
		        
		// creare matrice de convolutie prin instantierea clasei ConvolveOp
		BufferedImageOp op = new ConvolveOp(kernel);
		        
		// modificare imagine prin realizarea convolutiei dintre matricea de convolutie si BufferedImage
		imagineProcesata = op.filter(imaginePentruProcesare, null);   
		        
		timeP = System.currentTimeMillis() - timeP;  //calculeaza timpul de procesare
		    

        // partea de scriere a noii imagini in fisier
		
		timeS = System.currentTimeMillis(); //variabila folosita pentru a prelua timpul curent
		        
		try {
			file = new File(outFile); // locatia imaginii de output
			ImageIO.write(imagineProcesata, "bmp", file); // scriu imaginea obtinuta la adresa data ca argument
			
			System.out.println("\n Imaginea a fost procesata cu succes!");
			// procesul s-a incheiat cu bine
			icon1 = new ImageIcon(imagineInit); 
			icon = new ImageIcon(imagineProcesata);   			
			picLabel1.setIcon(icon1);
	        picLabel.setIcon(icon);		        	
		} catch(IOException exp) {
			System.out.println("Error: " + exp); // eroare
		}
		timeS = System.currentTimeMillis() - timeS; // calculeaza timpul de scriere
		
		System.out.println("Citirea imaginii a durat: " + (timeC / 1000.0f) + " secunde\n"); // afisare timp citire
		System.out.println("Procesarea imaginii a durat: " + (timeP / 1000.0f) + " secunde\n"); // afisare timp procesare
		System.out.println("Scrierea imaginii a durat: " + (timeS / 1000.0f) + " secunde\n"); // afisare timp scriere
	}
	
}
