package ExtendedJSONParser;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

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
			throw new Exception("[json->obj] Parser internal error:" + e.getMessage());
		}			
	}	
	
	public static String GetJSON(Object instance) throws Exception
	{
		try {
			if(instance.getClass().isArray())
				return GetJSONFromArray(instance);
			else
				return GetJSONFromObject(instance);			
		} catch (Exception e) {
			throw new Exception("[obj->json] Parser internal error:" + e.getMessage());
		}	
	}
	
	private static String GetJSONFromArray(Object instance) throws Exception
	{
		StringBuilder json=new StringBuilder("[");
		int instanceLength=Array.getLength(instance);
		int i=0;
		
		while(true)
		{
			json.append(GetJSON(Array.get(instance, i++)));
			if(i>=instanceLength) break;
			json.append(',');
		}
		
		json.append(']');		
		return json.toString();
	}
	
	private static String GetJSONFromObject(Object instance) throws Exception
	{		
		Class<?> instanceClass=instance.getClass();
		if(instanceClass.isPrimitive()||instanceClass==String.class)
			return GetPrimitiveFromJson(instance,instanceClass);
		
		StringBuilder json=new StringBuilder();
		Field[] allFields = instance.getClass().getDeclaredFields();
		
		for (Field field : allFields) {
			if(json.length()==0) json.append("{");
			else	json.append(",");
			
	        if (!Modifier.isPrivate(field.getModifiers())) 
	        	continue;
	        
	        field.setAccessible(true);	        	        	        
	        Object value=field.get(instance);
	        Class<?> fieldType= field.getType();	        
	        boolean isPrimitiveType=fieldType.isPrimitive()||fieldType==String.class;
	        
	        json.append("'"+ field.getName()+"':");
	        json.append(isPrimitiveType?GetPrimitiveFromJson(value,fieldType):GetJSON(value));	        
    	}
	
		json.append("}");
		return json.toString();		
	}
	
	private static String GetPrimitiveFromJson(Object instance, Class<?> type) throws Exception
	{
		if(type.isPrimitive())
			return GetPrimitiveJson(instance); 
		else if(type==String.class)
			return "'" + (String)instance + "'";
		
		throw new Exception("Invalid json primitve type class");		
	}
	
	private static String GetPrimitiveJson(Object instance) throws Exception
	{
		Class<?> instanceClass=instance.getClass();
		
		if(instanceClass.equals(Double.class))
			return ((Double)instance).toString();
		if(instanceClass.equals(Integer.class))
			return ((Integer)instance).toString(); 
		if(instanceClass.equals(Long.class))
			return ((Long)instance).toString(); 
		if(instanceClass.equals(Boolean.class))
			return ((Boolean)instance).toString();		
		
		throw new Exception("Invalid primitve type class");				
	}
	
	private static Object GetObject(Class<?> objectClass, JSONObject json) throws Exception
	{
		Object result = objectClass.newInstance();
		ConfigureInstance(result,json);
		return result ;
	}
	
	private static void ConfigureInstance(Object instance, JSONObject json) throws Exception
	{
		Field[] allFields = instance.getClass().getDeclaredFields();
		for (Field field : allFields) {
	        if (!Modifier.isPrivate(field.getModifiers()))
	        	continue;
	        field.setAccessible(true);
	        field.set(instance, GetFieldFromJson(field.getType(),field.getName(),json));	        
	    }
	}	
	
	private static Object GetFieldFromJson(Class<?> fieldType, String fieldName, JSONObject json) throws Exception
	{
		Object field=null;
		
		if(fieldType.isPrimitive()||fieldType==String.class)
			field=GetPrimitiveElement(fieldType,fieldName,json);
		else
		{
			if(fieldType.isArray())
				field=GetArrayObject(fieldType,json.getJSONArray(fieldName));
			else
				field=GetObject(fieldType,json.getJSONObject(fieldName));
		}
			
		return field;
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
