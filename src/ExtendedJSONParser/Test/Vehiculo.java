package ExtendedJSONParser.Test;

import java.util.Arrays;

public class Vehiculo {

	private String marca;
	private int modelo;
	private boolean descapotable;
	private Rueda[] ruedas;
	
	public Vehiculo(){}
	
	@Override
	public String toString() {
		return marca + " " + modelo + " " + descapotable + " " + Arrays.toString(ruedas);
	}
	
	@Override
	public boolean equals(Object obj) {
		Vehiculo vehiculo=(Vehiculo)obj;
		return vehiculo.marca.equals(this.marca) &&
				vehiculo.modelo==this.modelo &&
				vehiculo.descapotable==this.descapotable &&
				Arrays.equals(vehiculo.ruedas, this.ruedas);
	}
}
