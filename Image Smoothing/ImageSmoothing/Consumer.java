package ImageSmoothing;

public class Consumer extends ProdCons {
	Consumer() { // constructor fara parametri
		super();
	}
	public Consumer(Buffer buf) {
		super(false, buf);// apelam constructorul clasei ProdCons
		// am setat producer = false
		System.out.println("A fost apelat constructorul pentru Consumer");
	}
}

