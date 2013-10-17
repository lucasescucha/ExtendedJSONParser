package ExtendedJSONParser.Test;

public class Rueda {

	private double tamaño;
	
	public void settamaño(int tamaño)
	{
		this.tamaño=tamaño;
	}
	
	@Override
	public String toString() {
		return Double.toString(tamaño);
	}
}
