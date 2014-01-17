package org.yong3.offheap.serialization;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.yong3.offheap.serialization.serializer.BooleanArraySerializer;
import org.yong3.offheap.serialization.serializer.ByteArraySerializer;
import org.yong3.offheap.serialization.serializer.CharArraySerializer;
import org.yong3.offheap.serialization.serializer.DoubleArraySerializer;
import org.yong3.offheap.serialization.serializer.FloatArraySerializer;
import org.yong3.offheap.serialization.serializer.IntArraySerializer;
import org.yong3.offheap.serialization.serializer.LongArraySerializer;
import org.yong3.offheap.serialization.serializer.ObjectArraySerializer;
import org.yong3.offheap.serialization.serializer.OffheapObjectSerializer;
import org.yong3.offheap.serialization.serializer.ShortArraySerializer;
import org.yong3.offheap.serialization.serializer.SimpleObjectSerializer;
import org.yong3.offheap.serialization.serializer.StringSerializer;

/**
 * Factory class to get a serializer for given class. For primitive type, should
 * use BaseSerializer directly to avoid auto-boxing overhead which might produce
 * more garbages.
 * 
 * @author yc
 * 
 */

@SuppressWarnings({ "unchecked", "rawtypes" })
public class SerializerFactory {

	/*
	 * map to store generated serializer
	 */
	static private Map<Type, Serializer> serializerMap = new HashMap<Type, Serializer>(
			32);

	static {
		put(String.class, new StringSerializer());
		put(int[].class, new IntArraySerializer());
		put(byte[].class, new ByteArraySerializer());
		put(boolean[].class, new BooleanArraySerializer());
		put(char[].class, new CharArraySerializer());
		put(short[].class, new ShortArraySerializer());
		put(float[].class, new FloatArraySerializer());
		put(long[].class, new LongArraySerializer());
		put(double[].class, new DoubleArraySerializer());
	}

	/**
	 * get serializer for given class; will generate one if not exist.
	 * 
	 * @param cls
	 * @return
	 */
	public static <T> Serializer<T> get(Class<T> cls) {
		Serializer<T> serializer = serializerMap.get(cls);
		if (serializer == null) {

			if (cls.isArray()) {
				serializer = new ObjectArraySerializer(cls);
			} else if (OffheapObject.class.isAssignableFrom(cls)) {
				serializer = new OffheapObjectSerializer();
			} else {
				serializer = new SimpleObjectSerializer(cls);
			}
			serializerMap.put(cls, serializer);
		}

		return serializer;
	}

	public static <T> void register(Class<T> cls) {
		get(cls);
	}

	public static <T> void put(Class<T> cls, Serializer<T> serializer) {
		serializerMap.put(cls, serializer);
	}
}
