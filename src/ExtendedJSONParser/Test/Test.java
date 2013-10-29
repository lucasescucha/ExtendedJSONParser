package ExtendedJSONParser.Test;

import java.util.Arrays;

import ExtendedJSONParser.ExtendedJSONParser;

public class Test {

	public static void main(String[] args) {
		String json=
		"["
			+ "{"
				+ "'marca':'Peugeot',"
				+ "'modelo':405,"
				+ "'descapotable':false,"
				+ "'ruedas':[{'tamaño':21},{'tamaño':21},{'tamaño':20},{'tamaño':20}]"					
			+ "}"
		+ ","
		+ "{"
				+ "'marca':'Peugeot',"
				+ "'modelo':607,"
				+ "'descapotable':true,"
				+ "'ruedas':[{'tamaño':19},{'tamaño':19},{'tamaño':19},{'tamaño':19}]"
		+ "}]";		
		
		try {
			Vehiculo[] vehiculos=ExtendedJSONParser.GetObject(json, Vehiculo[].class);
			String generatedJson=ExtendedJSONParser.GetJSON(vehiculos);
			Vehiculo[] vehiculosCopy=ExtendedJSONParser.GetObject(generatedJson, Vehiculo[].class);
			
			System.out.println(Arrays.equals(vehiculos,vehiculosCopy));
		} catch (Exception e) {
			System.out.println(e.toString());
		}		
	}
}
