package org.yong3.offheap.serialization.serializer;

import java.lang.reflect.Array;

import org.yong3.offheap.serialization.Serializer;
import org.yong3.offheap.serialization.SerializerFactory;
import org.yong3.offheap.serialization.OffheapUtils;

@SuppressWarnings({"unchecked", "rawtypes", "restriction"})
public class ObjectArraySerializer<V> extends BaseObjectSerializer<V> {

	// TODO: here assume allocator always use fixed size to write an int, which
	// might not be true...
	// protected static final int SIZE_OF_ARRAY_LEN = allocator.sizeOf(1);
	Class arrayType;
	Class componentType;
	//boolean isPrimitive;
	Serializer serializer;
	//int arrayBaseOffset, arrayIndexScale;

	public ObjectArraySerializer(Class arrayCls) {
		arrayType = arrayCls;
		componentType = arrayType.getComponentType();
		//isPrimitive = componentType.isPrimitive();
		//arrayBaseOffset = unsafe.arrayBaseOffset(arrayType);
		//arrayIndexScale = unsafe.arrayIndexScale(arrayType);
		//if (!isPrimitive) {
			serializer = SerializerFactory.get(componentType);
		//}
		
		//System.err.println("create new array serializer,isPrimitive=" + isPrimitive + ",componentType=" + componentType + ",arrayIndexScale="+ arrayIndexScale + ",arrayBaseOffset="+arrayBaseOffset);
	}

	@Override
	protected int getNotNullObjectSize(V instance) {
		// int size = SIZE_OF_ARRAY_LEN;//size of array, in int
		int len = Array.getLength(instance);
		int size = allocator.sizeOf(len);
		//if (isPrimitive) {
			// size += allocator.fixSizeOf(componentType) * len;
		//	size += arrayIndexScale * len;
		//} else {
			for (int i = 0; i < len; i++) {
				size += serializer.getOffheapSize(Array.get(instance, i));
			}

//		}
		return size;
	}

	
	@Override
	protected int writeNotNullObjectSize(final long address, V instance) {
		int len = 0;
		if (instance != null) {
			len = Array.getLength(instance);
		}
		long addr = address;
		// put array length
		allocator.putInt(addr, len);
		addr += allocator.sizeOf(len);
//		if (isPrimitive) {
//			int length = arrayIndexScale * len;
////			OffheapUtils.copyArrayToOffheap(instance, arrayBaseOffset, length,
////					addr);
//			
//			//unsafe.copyMemory(instance, arrayBaseOffset, null, addr, length);
//			
//			addr += length;
//		} else {
			for (int i = 0; i < len; i++) {
				addr += serializer.write(addr, Array.get(instance, i));
		//	}
		}
		return (int) (addr - address);
	}

	//passed in instance should always be null
	@Override
	protected V readNotNullObject(final long address, V instance) {
		long addr = address;
		int len = allocator.getInt(addr);
		addr += allocator.sizeOf(len);
		if(instance == null) instance = (V) Array.newInstance(componentType, len);
//		if (isPrimitive) {
//			//OffheapUtils.readArrayromOffheap(addr, len * arrayIndexScale, instance, arrayBaseOffset);
//			unsafe.copyMemory(null, addr, instance, arrayBaseOffset, len * arrayIndexScale);
//		}else{
			for (int i = 0; i < len; i++) {
				Object value = serializer.read(addr, componentType);
				Array.set(instance, i, value);
				addr += serializer.getOffheapSize(value);
			}
//		}
		return instance;
	}

	@Override
	protected V newInstance(Class<V> cls) {
		// TODO Auto-generated method stub
		return null;
	}

}
