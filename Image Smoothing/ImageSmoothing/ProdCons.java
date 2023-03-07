package ImageSmoothing;


public abstract class ProdCons extends Thread {
	private Boolean producer;
	private Buffer buffer;
	
	ProdCons() {
		producer = true;
	}
	ProdCons(Boolean bool, Buffer buf) { // retin buffer-ul primit si data ProdCons-ul curent este Producer sau Consumer
		producer = bool;
		buffer = buf;
	}
	public void run() {
		if(producer) { // ProdCons-ul este Producer
			
			int width = Smoothing.imagineInit.getWidth(); // latimea imaginii
			int height = Smoothing.imagineInit.getHeight(); // inaltimea imaginii
			
			for (int i = 0; i < 4; i++) { // impart imaginea in 4  
				
				int[][] pixeli = new int[width][height];// declar o matrice prin care trimit cate
				// un sfert din imagine
				
				for (int j = height/4 * i; j < height/4 * (i + 1); j++) { // se alege sfertul curent
					// (imaginea este impartita pe inaltime)
					
					for (int r = 0; r < width; r++) {
						pixeli[r][j] = Smoothing.imagineInit.getRGB(r, j);
						// se retine pixelul curent din imaginea originala
					}
				}
				
				buffer.put(pixeli);// se pune sfertul curent in Buffer
				
				System.out.println("Producatorul a pus sfertul cu numarul " + (i + 1) + " al imaginii.");
				
				try {
						sleep(1000);// se executa o secventa de sleep inainte de a trimite urmatorul sfert
					} catch (InterruptedException e) {
						System.out.println(e);// s-a intampinat o eroare; se afiseaza eroarea intampinata
					}
			}
		}
		else {
			// ProdCons-ul este Consumer
			
			int width = Smoothing.imagineInit.getWidth();// latimea imaginii
			int height = Smoothing.imagineInit.getHeight();// inaltimea imaginii
			
			for (int i = 0; i < 4; i++) { // impart imaginea in 4
				
				int[][] pixeli = new int[width][height/4+3]; // declar o matrice in care voi stoca sfertul de imagine primit
				
				pixeli = buffer.get();// iau sfertul curent din Buffer
				
				for (int j = height/4 * i; j < height/4 * (i + 1); j++) {
					// aleg sfertul curent (imaginea este impartita pe inaltime) 
					
					for (int r = 0; r < width; r++) {
						Smoothing.imaginePentruProcesare.setRGB(r, j, pixeli[r][j]);
						// setez pixelul curent in imaginea care va fi trimisa spre procesare
					}
				}
				
				System.out.println("Consumer-ul preia sfertul " + (i + 1) + " din imagine.");
				try {
					sleep(1000);// execut o secventa de sleep pana sa fie trimis urmatorul sfert
				} catch (InterruptedException exp) {
					System.out.println(exp);// a aparut o eroare
				}
			}
		}
	}
}
