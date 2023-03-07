package ImageSmoothing;

public class Producer extends ProdCons {
	Producer() { // constructor fara parametri
		super();
	}
	public Producer(Buffer buf) {
		super(true, buf);// apelam constructorul clasei ProdCons
		// am setat producer = true
		System.out.println("A fost apelat constructorul pentru Producer");
	}
}
