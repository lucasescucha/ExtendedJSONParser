ExtendedJSONParser
==================

JSON parser very easy to use

An example:
-----------

**Vechiculo.java**
```java
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

```
**Rueda.java**
```java
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

```
**Test.java**
```java
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
