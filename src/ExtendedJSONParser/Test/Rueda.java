package ExtendedJSONParser.Test;

public class Rueda {

	private int tama�o;
	
	public Rueda(){}
	
	@Override
	public String toString() {
		return Integer.toString(tama�o);
	}
	
	@Override
	public boolean equals(Object obj) {		
		return ((Rueda)obj).tama�o==this.tama�o;
	}
}
