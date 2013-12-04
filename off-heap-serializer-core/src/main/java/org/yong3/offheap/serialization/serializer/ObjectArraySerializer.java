package org.yong3.offheap.serialization.serializer;

import java.lang.reflect.Array;

import org.yong3.offheap.serialization.Serializer;
import org.yong3.offheap.serialization.SerializerFactory;
import org.yong3.offheap.serialization.OffheapUtils;

@SuppressWarnings({"unchecked", "rawtypes", "restriction"})
public class ObjectArraySerializer<V> extends BaseObjectSerializer<V> {
	// public static final int ARRAY_BOOLEAN_BASE_OFFSET
	// = unsafe.arrayBaseOffset(boolean[].class);
	//
	// public static final int ARRAY_BYTE_BASE_OFFSET
	// = unsafe.arrayBaseOffset(byte[].class);
	//
	// public static final int ARRAY_SHORT_BASE_OFFSET
	// = unsafe.arrayBaseOffset(short[].class);
	//
	// public static final int ARRAY_CHAR_BASE_OFFSET
	// = unsafe.arrayBaseOffset(char[].class);
	//
	// public static final int ARRAY_INT_BASE_OFFSET
	// = unsafe.arrayBaseOffset(int[].class);
	//
	// public static final int ARRAY_LONG_BASE_OFFSET
	// = unsafe.arrayBaseOffset(long[].class);
	//
	// public static final int ARRAY_FLOAT_BASE_OFFSET
	// = unsafe.arrayBaseOffset(float[].class);
	//
	// public static final int ARRAY_DOUBLE_BASE_OFFSET
	// = unsafe.arrayBaseOffset(double[].class);
	//
	// public static final int ARRAY_OBJECT_BASE_OFFSET
	// = unsafe.arrayBaseOffset(Object[].class);
	//
	// public native int arrayIndexScale(Class arrayClass);
	//
	// public static final int ARRAY_BOOLEAN_INDEX_SCALE
	// = unsafe.arrayIndexScale(boolean[].class);
	//
	// public static final int ARRAY_BYTE_INDEX_SCALE
	// = unsafe.arrayIndexScale(byte[].class);
	//
	// public static final int ARRAY_SHORT_INDEX_SCALE
	// = unsafe.arrayIndexScale(short[].class);
	//
	// public static final int ARRAY_CHAR_INDEX_SCALE
	// = unsafe.arrayIndexScale(char[].class);
	//
	// public static final int ARRAY_INT_INDEX_SCALE
	// = unsafe.arrayIndexScale(int[].class);
	//
	// public static final int ARRAY_LONG_INDEX_SCALE
	// = unsafe.arrayIndexScale(long[].class);
	//
	// public static final int ARRAY_FLOAT_INDEX_SCALE
	// = unsafe.arrayIndexScale(float[].class);
	//
	// public static final int ARRAY_DOUBLE_INDEX_SCALE
	// = unsafe.arrayIndexScale(double[].class);

	// TODO: here assume allocator always use fixed size to write an int, which
	// might not be true...
	// protected static final int SIZE_OF_ARRAY_LEN = allocator.sizeOf(1);
	Class arrayType;
	Class componentType;
	boolean isPrimitive;
	Serializer serializer;
	int arrayBaseOffset, arrayIndexScale;

	public ObjectArraySerializer(Class arrayCls) {
		arrayType = arrayCls;
		componentType = arrayType.getComponentType();
		isPrimitive = componentType.isPrimitive();
		arrayBaseOffset = unsafe.arrayBaseOffset(arrayType);
		arrayIndexScale = unsafe.arrayIndexScale(arrayType);
		if (!isPrimitive) {
			serializer = SerializerFactory.get(componentType);
		}
	}

	@Override
	protected int getNotNullObjectSize(V instance) {
		// int size = SIZE_OF_ARRAY_LEN;//size of array, in int
		int len = Array.getLength(instance);
		int size = allocator.sizeOf(len);
		if (isPrimitive) {
			// size += allocator.fixSizeOf(componentType) * len;
			size += arrayIndexScale * len;
		} else {
			for (int i = 0; i < len; i++) {
				size += serializer.getOffheapSize(Array.get(instance, i));
			}

		}
		return size;
	}

	
	@Override
	protected int writeNotNullObjectSize(long address, V instance) {
		int len = 0;
		if (instance != null) {
			len = Array.getLength(instance);
		}
		long addr = address;
		// put array length
		allocator.putInt(addr, len);
		addr += allocator.sizeOf(len);
		if (isPrimitive) {
			int length = arrayIndexScale * len;
			OffheapUtils.copyArrayToOffheap(instance, arrayBaseOffset, length,
					addr);
			addr += length;
		} else {
			for (int i = 0; i < len; i++) {
				addr += serializer.write(addr, Array.get(instance, i));
			}
		}
		return (int) (addr - address);
	}

	//passed in instance should always be null
	@Override
	protected V readNotNullObject(long address, V instance) {
		long addr = address;
		int len = allocator.getInt(addr);
		addr += allocator.sizeOf(len);
		if(instance == null) instance = (V) Array.newInstance(componentType, len);
		if (isPrimitive) {
			OffheapUtils.readArrayromOffheap(addr, len * arrayIndexScale, instance, arrayBaseOffset);
		}else{
			for (int i = 0; i < len; i++) {
				Object value = serializer.read(addr, componentType);
				Array.set(instance, i, value);
				addr += serializer.getOffheapSize(value);
			}
		}
		return instance;
	}

	@Override
	protected V newInstance(Class<V> cls) {
		// TODO Auto-generated method stub
		return null;
	}

}
