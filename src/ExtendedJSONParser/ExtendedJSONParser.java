package ExtendedJSONParser;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.json.JSONArray;
import org.json.JSONObject;

public class ExtendedJSONParser {

	@SuppressWarnings("unchecked")
	public static <T> T GetObject(String json, Class<T> objectClass) throws Exception
	{			
		try {		
			if(objectClass.isArray())
				return (T)GetArrayObject(objectClass,new JSONArray(json));
			else
				return (T)GetObject(objectClass, new JSONObject(json));
		} catch (Exception e) {
			throw new Exception("Parser internal error:" + e.getMessage());
		}			
	}	
	
	private static Object GetObject(Class<?> objectClass, JSONObject json) throws Exception
	{
		Object result = null;
		Constructor<?>[] constructors=objectClass.getConstructors();
		
		for(Constructor<?> constructor : constructors)
		{
			if(constructor.getAnnotation(JSONElements.class)==null)
				continue;
			
			result=BuildObjectWithConstructor(constructor,json);
			break;							
		}
		
		if(result==null)
			result=objectClass.newInstance();
		
		FillObjectWithMethods(result,objectClass,json);
		
		return result;
	}
	
	private static Object BuildObjectWithConstructor(Constructor<?> constructor,  JSONObject json) throws Exception
	{
		String[] names=constructor.getAnnotation(JSONElements.class).names().split(",");
		return constructor.newInstance(GetParametersFromJson(constructor.getParameterTypes(),names,json));
	}
	
	private static Object[] GetParametersFromJson(Class<?>[] parametersTypes, String[] parametersNames, JSONObject json) throws Exception
	{
		Object[] parameters=new Object[parametersNames.length];
		
		for(int p=0;p<parametersNames.length;p++){	
			Class<?> patameterType=parametersTypes[p];
			if(patameterType.isPrimitive()||patameterType==String.class)
				parameters[p]=GetPrimitiveElement(patameterType,parametersNames[p],json);
			else
			{
				if(patameterType.isArray())
					parameters[p]=GetArrayObject(patameterType,json.getJSONArray(parametersNames[p]));
				else
					parameters[p]=GetObject(patameterType,json.getJSONObject(parametersNames[p]));
			}								
		}
					
		return parameters;
	}
	
	private static void FillObjectWithMethods(Object object, Class<?> objectClass, JSONObject json) throws Exception	{
		
		for(Method method : objectClass.getMethods())
		{
			String[] names;
			
			if(method.getAnnotation(JSONElements.class) != null)
				names=method.getAnnotation(JSONElements.class).names().split(",");		
			else if(method.getName().startsWith("set"))
				names= new String[]{method.getName().substring(3)};
			else
				continue;
			
			method.invoke(object, GetParametersFromJson(method.getParameterTypes(),names,json));						
		}		
	}
	
	private static Object GetArrayObject(Class<?> arrayClass, JSONArray jsonArray) throws Exception
	{
		Object resultArray=Array.newInstance(arrayClass.getComponentType(), jsonArray.length());
		Class<?> componentType=arrayClass.getComponentType();
		
		for(int e=0;e<jsonArray.length();e++){			
			Object value;
			if(componentType.isPrimitive()||componentType==String.class)
				value=GetPrimitiveElement(componentType,e,jsonArray);										
			else
			{
				if(componentType.isArray())
					value=GetArrayObject(componentType,jsonArray.getJSONArray(e));
				else
					value=GetObject(componentType,jsonArray.getJSONObject(e));
			}				
			Array.set(resultArray, e, value);		
		}
		
		return resultArray;
	}
	
	private static Object GetPrimitiveElement(Class<?> elementClass, String elementName, JSONObject json) throws Exception
	{			
		if(elementClass.equals(double.class))
			return json.getDouble(elementName);
		if(elementClass.equals(int.class))
			return json.getInt(elementName);
		if(elementClass.equals(long.class))
			return json.getLong(elementName);
		if(elementClass.equals(boolean.class))
			return json.getBoolean(elementName);
		if(elementClass.equals(String.class))
			return json.getString(elementName);
		
		throw new Exception("Invalid primitve type class");				
	}
	
	private static Object GetPrimitiveElement(Class<?> elementClass, int index, JSONArray json) throws Exception
	{	
		if(elementClass.equals(double.class))
			return json.getDouble(index);
		if(elementClass.equals(int.class))
			return json.getInt(index);
		if(elementClass.equals(long.class))
			return json.getLong(index);
		if(elementClass.equals(boolean.class))
			return json.getBoolean(index);
		if(elementClass.equals(String.class))
			return json.getString(index);
		
		throw new Exception("Invalid primitve type class");			
	}
}
