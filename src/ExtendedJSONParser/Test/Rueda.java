package ExtendedJSONParser.Test;

public class Rueda {

	private double tama�o;
	
	public void settama�o(int tama�o)
	{
		this.tama�o=tama�o;
	}
	
	@Override
	public String toString() {
		return Double.toString(tama�o);
	}
}
