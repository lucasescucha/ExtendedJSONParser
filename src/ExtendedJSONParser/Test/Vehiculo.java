package ExtendedJSONParser.Test;

import java.util.Arrays;

import ExtendedJSONParser.JSONElements;

public class Vehiculo {

	private String marca;
	private int modelo;
	private boolean descapotable;
	private Rueda[] ruedas;
	
	@JSONElements(names="marca,modelo,descapotable,ruedas")
	public Vehiculo(String marca,int modelo,boolean descapotable,Rueda[] ruedas)
	{
		this.marca=marca;
		this.modelo=modelo;
		this.descapotable=descapotable;
		this.ruedas=ruedas;
	}
	
	@Override
	public String toString() {
		return marca + " " + modelo + " " + descapotable + " " + Arrays.toString(ruedas);
	}
}
