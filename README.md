ExtendedJSONParser
==================

Json parser very easy to use

An example:
-----------

**Vechiculo.java**
```java
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

```
**Rueda.java**
```java
package ExtendedJSONParser.Test;

import ExtendedJSONParser.JSONElements;

public class Rueda {

	private double tamaño;
	
	@JSONElements(names="tamaño")
	public void setTamaño(int tam)
	{
		this.tamaño=tam;
	}
	
	@Override
	public String toString() {
		return Double.toString(tamaño);
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
				+ "'modelo':'2010',"
				+ "'descapotable':'false',"
				+ "'ruedas':[{'tamaño':'21'},{'tamaño':'21'},{'tamaño':'20'},{'tamaño':'20'}]"					
			+ "}"
		+ ","
		+ "{"
				+ "'marca':'Fiat',"
				+ "'modelo':'2005',"
				+ "'descapotable':'true',"
				+ "'ruedas':[{'tamaño':'19'},{'tamaño':'19'},{'tamaño':'19'},{'tamaño':'19'}]"
		+ "}]";			
		
		try {
			System.out.println(Arrays.toString(ExtendedJSONParser.GetObject(json, Vehiculo[].class)));
		} catch (Exception e) {
			System.out.println(e.toString());
		}		
	}
}
