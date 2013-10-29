package ExtendedJSONParser.Test;

public class Rueda {

	private int tamaño;
	
	public Rueda(){}
	
	@Override
	public String toString() {
		return Integer.toString(tamaño);
	}
	
	@Override
	public boolean equals(Object obj) {		
		return ((Rueda)obj).tamaño==this.tamaño;
	}
}
