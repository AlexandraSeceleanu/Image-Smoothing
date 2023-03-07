package ImageSmoothing;

public class Buffer {
	private int[][] pixeli;
	private boolean liber = false;
	
	public synchronized int[][] get() {
		while (!liber) {
			try {// astept pana resursa este disponibila pentru citire
				wait(); //Producer-ul asteapta sa puna o valoare
				
			} catch (InterruptedException exp) {
				exp.printStackTrace(); // daca am eroare afisez stiva
			}
		}		
		liber = false;// setez disponibilitatea
		notifyAll();// anunt incheierea procedurii
		return this.pixeli;// returnez matricea actualizata
	}
	
	public synchronized void put(int[][] pixeli) {
		while (liber) {
			try {// astept pana resursa nu mai e disponibila pentru citire
				wait(); // Consumer-ul asteapta sa ia o valoare
				
			} catch (InterruptedException exp) {
				exp.printStackTrace(); // daca am eroare afisez stiva
			}
		}
		
		this.pixeli = pixeli; // matricea primita
		liber = true; // setez disponibilitatea
		notifyAll(); // anunt incheierea procedurii
	}
}
