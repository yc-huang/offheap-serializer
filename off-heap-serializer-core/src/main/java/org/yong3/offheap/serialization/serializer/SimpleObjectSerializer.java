package org.yong3.offheap.serialization.serializer;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.yong3.offheap.serialization.Serializer;
import org.yong3.offheap.serialization.SerializerFactory;

/**
 * Simple object serializer, which prefix 1 byte for null detection.
 * 
 * @author yc
 * 
 * @param <V>
 */
@SuppressWarnings({ "rawtypes", "restriction", "unchecked"})
public class SimpleObjectSerializer<V> extends BaseObjectSerializer<V> {
	private final static List<Class> ignoreTypes = Arrays.asList(new Class[] {
			Class.class, Object.class, AccessibleObject.class, Field.class,
			Method.class, Constructor.class });

	protected Field[] allFields;
	protected Field[] objFields;

	protected Serializer[] objFieldSerializers;
	protected Serializer[] allFieldSerializers;

	protected long[] allOffset;
	protected long[] objOffset;
	protected Class[] allTypes;
	protected Class[] objTypes;

	protected int fixedLen = 0;


	public SimpleObjectSerializer(Class<V> cls) {
		List<Field> af = new ArrayList<Field>();// all fields
		List<Field> of = new ArrayList<Field>();// non-fixed length fields

		Class parent = cls;
		while (parent != null) {
			for (Field f : parent.getDeclaredFields()) {
				addField(f, af, of);
			}
			parent = parent.getSuperclass();
		}

		allFields = new Field[af.size()];
		allFields = af.toArray(allFields);
		// System.err.println(allFields.length);

		objFields = new Field[of.size()];
		objFields = of.toArray(objFields);

		objOffset = new long[objFields.length];
		objTypes = new Class[objFields.length];
		for (int i = 0; i < objFields.length; i++) {
			objTypes[i] = objFields[i].getType();
//			System.err.println("class: " + cls + " field: "
//					+ objFields[i].getName() + " type: " + objTypes[i]);
			objOffset[i] = unsafe.objectFieldOffset(objFields[i]);
		}

		allOffset = new long[allFields.length];
		allTypes = new Class[allFields.length];
		for (int i = 0; i < allFields.length; i++) {
			// System.err.println(allFields[i].getName());
			allOffset[i] = unsafe.objectFieldOffset(allFields[i]);
			// System.err.println(allFields[i].getName() + " offset "
			// + allOffset[i]);
			allTypes[i] = allFields[i].getType();
		}

		allFieldSerializers = new Serializer[allTypes.length];
		objFieldSerializers = new Serializer[objTypes.length];
		int idx = 0;
		for (int i = 0; i < allTypes.length; i++) {
			if (of.contains(allFields[i])) {
				allFieldSerializers[i] = SerializerFactory.get(allTypes[i]);
				objFieldSerializers[idx] = allFieldSerializers[i];
				idx++;
			}
		}

	}

	private void addField(Field f, List<Field> all, List<Field> objs) {
		int modifier = f.getModifiers();
		Class type = f.getType();
		String name = f.getName();

		if (Modifier.isStatic(modifier) || ignoreTypes.contains(type)
				|| name.startsWith("this$")) {
			// ignore this field
		} else {
			all.add(f);
			if (allocator.isFixLength(type)) {
				fixedLen += allocator.fixSizeOf(type);
			} else {
				objs.add(f);
			}
		}
	}


	@Override
	protected int getNotNullObjectSize(V instance) {
		int varLen = 0;
		int size = 0;
		for (int i = 0; i < objTypes.length; i++) {
			size = allocator.sizeOf(objTypes[i], instance, objOffset[i]);
			if (size == -1) {
				//
				size = objFieldSerializers[i].getOffheapSize(unsafe.getObject(
						instance, objOffset[i]));
			}
			varLen += size;
		}
		return fixedLen + varLen;
	}

	// TODO: sort by offset
	@Override
	protected int writeNotNullObjectSize(long address, V instance) {
		long addr = address;
		for (int i = 0; i < allTypes.length; i++) {
			Class type = allTypes[i];
			long offset = allOffset[i];
			if (type.isPrimitive()) {
				if (byte.class == type) {
					addr += allocator.putByte(addr, unsafe.getByte(instance, offset));
				} else if (char.class == type) {
					addr += allocator.putChar(addr, unsafe.getChar(instance, offset));
				} else if (int.class == type) {
					addr += allocator.putInt(addr, unsafe.getInt(instance, offset));
				} else if (long.class == type) {
					addr += allocator.putLong(addr, unsafe.getLong(instance, offset));
				} else if (boolean.class == type) {
					addr += allocator.putBoolean(addr,
							unsafe.getBoolean(instance, offset));
				} else if (float.class == type) {
					addr += allocator.putFloat(addr, unsafe.getFloat(instance, offset));
				} else if (short.class == type) {
					addr += allocator.putShort(addr, unsafe.getShort(instance, offset));
				} else if (double.class == type) {
					addr += allocator.putDouble(addr,
							unsafe.getDouble(instance, offset));
				}
			} else {
				//System.err.println(i + " field type: " + allTypes[i] + ", serializer cls:" + allFieldSerializers[i].getClass());
				addr += allFieldSerializers[i].write(addr, unsafe.getObject(instance, allOffset[i]));
			}
		}
		return (int) (addr - address);
	}

	@Override
	protected V readNotNullObject(long address, V instance) {
		long addr = address;
		for (int i = 0; i < allTypes.length; i++) {
			Class type = allTypes[i];
			long offset = allOffset[i];
			if (type.isPrimitive()) {
				if (byte.class == type) {
					byte v = allocator.getByte(addr);
					unsafe.putByte(instance, offset, v);
					addr += allocator.sizeOf(v);
				} else if (char.class == type) {
					char v = allocator.getChar(addr);
					unsafe.putChar(instance, offset, v);
					addr += allocator.sizeOf(v);
				} else if (int.class == type) {
					int v = allocator.getInt(addr);
					unsafe.putInt(instance, offset, v);
					addr += allocator.sizeOf(v);
				} else if (long.class == type) {
					long v = allocator.getLong(addr);
					unsafe.putLong(instance, offset, v);
					addr += allocator.sizeOf(v);
				} else if (boolean.class == type) {
					boolean v = allocator.getBoolean(addr);
					unsafe.putBoolean(instance, offset, v);
					addr += allocator.sizeOf(v);
				} else if (float.class == type) {
					float v = allocator.getFloat(addr);
					unsafe.putFloat(instance, offset, v);
					addr += allocator.sizeOf(v);
				} else if (short.class == type) {
					short v = allocator.getShort(addr);
					unsafe.putShort(instance, offset, v);
					addr += allocator.sizeOf(v);
				} else if (double.class == type) {
					double v = allocator.getDouble(addr);
					unsafe.putDouble(instance, offset, v);
					addr += allocator.sizeOf(v);
				}
			} else {
				Object v = allFieldSerializers[i].read(addr, allTypes[i]);
				unsafe.putObject(instance, offset, v);
				addr += allFieldSerializers[i].getOffheapSize(v);
			}
		}
		return instance;
	}

}
