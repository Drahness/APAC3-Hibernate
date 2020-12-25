package com.catalanrenegado.tinkdatabase.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * It's a ClassType list, one reference can contain objects from a different natures. Can be Parametrized with {@link Object} to be supergeneral
 * @author Catalan Renegado
 *
 * @param <U>
 */
public class ClassInstanceList<U extends Object>{
	private Map<Class<? extends U>,List<U>> map = new HashMap<>();
	/**
	 * @param an Object of type/subtype {@link U} 
	 * @return the class or superclass registered 
	 */
	private Class<?> getClassRegistered(Class<?> klass) {
		if(map.containsKey(klass)) {
			return klass;
		}
		for (Class<?> iInterface: klass.getInterfaces()) {
			if(map.containsKey(iInterface)) {
				return iInterface;
			}
		}
		if(klass == Object.class) {
			return null;
		}
		else {
			return getClassRegistered( klass.getSuperclass());
		}
	}
	/**
	 * @param an Object of type/subtype {@link U} 
	 * @return the class or superclass registered 
	 */
	private Class<?> getClassRegistered(U o) {
		return getClassRegistered(o.getClass());
	}
	public boolean add(Class<? extends U> klass, U o) {
		return map.get(klass).add(o);
	}
	public void add(int index, U element) {
		if(!contains(element.getClass())) {
			throw new NullPointerException("Class "+element.getClass()+" is not registered in the list.");
		}
		map.get(element.getClass()).add(index,element);
	}
	public boolean add(U o) {
		Class<?> klass = getClassRegistered(o);
		if(klass == null) {
			throw new NullPointerException();
		}
		return map.get(klass).add(o);
	}
	/**
	 * Safe implementation, will add the collection to a specified class
	 * @param klass
	 * @param c
	 * @return
	 */
	public boolean addAll(Class<?> klass, Collection<? extends U> c) {
		if(klass == null) {
			throw new NullPointerException("Class "+klass+" is not registered in the list.");
		}
		return map.get(klass).addAll(c);
	}
	public List<?> addRegistry(Class<? extends U> klass) {
		return map.put(klass, new ArrayList<U>());
	}
	public void clear() {
		for (Class<? extends U> key : map.keySet()) {
			map.get(key).clear();
		}
		
	}
	/*public boolean contains(U o) {
		return map.get(getClassRegistered(o)).contains(o);
	}*/
	@Override
	public String toString() {
		return map.toString();
	}
	public boolean contains(Class<?> klazz) {
		return map.containsKey(klazz);
	}
	public boolean contains(Object o) {
		if(!contains(o.getClass())) {
			return false;
		}
		return map.get(o.getClass()).contains(o);
	}
	@SuppressWarnings("unchecked") // Is checked
	public <T extends U> List<T> get(Class<T> klass) {
		return (List<T>) map.get(klass);
	}
	@SuppressWarnings("unchecked") // Is checked
	public <T extends U> T get(Class<T> klass, int i) {
		return (T) map.get(klass).get(i);
	}
	public int size() {
		int i = 0;
		for (Class<? extends U> key : map.keySet()) {
			i += map.get(key).size();
		}
		return i;
	}
}
