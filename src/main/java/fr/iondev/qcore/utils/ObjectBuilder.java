package fr.iondev.qcore.utils;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ObjectBuilder {

	private List<Object> object;

	public ObjectBuilder() {
		this.object = new ArrayList<>();
	}

	public ObjectBuilder(List<Object> object) {
		this.object = object;
	}

	public void addObject(Object o) {
		object.add(o);
	}

	public List<Object> getObject() {
		return this.object;
	}

	public Object getObject(int index) {
		return object.get(index);
	}

	public Object build(Class<?> classBuild) {
		try {
			Constructor<?> constructor[] = classBuild.getConstructors();

			System.out.println(Arrays.toString(constructor));

			for (Constructor<?> constru : constructor) {
				System.out.println(Arrays.toString(constru.getParameters()));
				if (constru.getParameterCount() == object.size()) {
					System.out.println("building object");
					return constru.newInstance(object.toArray());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Map<Class<?>, Object> infosBuild() {
		Map<Class<?>, Object> objT = new HashMap<>();

		for (Object o : object) {
			objT.put(o.getClass(), o);
		}

		return objT;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder("");
		for (Entry<Class<?>, Object> entry : this.infosBuild().entrySet()) {
			builder.append(entry.getKey().getClass().getName()).append("  ").append(entry.getValue()).append("\n");
		}
		return builder.toString();
	}

}
