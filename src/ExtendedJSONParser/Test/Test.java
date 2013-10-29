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
				+ "'ruedas':[{'tama�o':21},{'tama�o':21},{'tama�o':20},{'tama�o':20}]"					
			+ "}"
		+ ","
		+ "{"
				+ "'marca':'Peugeot',"
				+ "'modelo':607,"
				+ "'descapotable':true,"
				+ "'ruedas':[{'tama�o':19},{'tama�o':19},{'tama�o':19},{'tama�o':19}]"
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
