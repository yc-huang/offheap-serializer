package org.yong3.offheap.serialization.serializer;

import org.yong3.offheap.serialization.MemoryAllocator;
import org.yong3.offheap.serialization.MemoryAllocatorFactory;
import org.yong3.offheap.serialization.OffheapUtils;
import org.yong3.offheap.serialization.Serializer;

import sun.misc.Unsafe;

/**
 * Basic object serializer that handles null object.
 * @author yc
 *
 * @param <V>
 */

@SuppressWarnings({"restriction", "unchecked"})
public abstract class BaseObjectSerializer<V> implements Serializer<V> {
	protected static MemoryAllocator allocator = MemoryAllocatorFactory.get();
	protected static final int SIZE_OF_NULL_FLAG = allocator.sizeOf(true);

	protected static Unsafe unsafe = OffheapUtils.unsafe;

	public int getOffheapSize(V instance) {
		return SIZE_OF_NULL_FLAG
				+ ((instance == null) ? 0 : getNotNullObjectSize(instance));
	}

	public int write(long address, V instance) {
		if (instance == null) {
			return allocator.putBoolean(address, true);
		} else {
			long addr = address;
			addr +=  allocator.putBoolean(addr, false);
			addr += writeNotNullObjectSize(addr, instance);
			return (int) (addr - address);
		}
	}

	public V read(long addr, Class<V> cls) {
		return read(addr, newInstance(cls));
	}

	public V read(long addr, V reused) {
		long address = addr;
		boolean isNull = allocator.getBoolean(address);
		if (isNull) {
			return null;
		} else {
			address += SIZE_OF_NULL_FLAG;
			return readNotNullObject(address, reused);
		}
	}

	//return the object size; passed in object is guaranteed not null
	protected abstract int getNotNullObjectSize(V instance);

	//write the object to specified offheap address; passed in object is guaranteed not null
	protected abstract int writeNotNullObjectSize(long address, V instance);
	
	//read from specified offheap address, and populate read values to specified object instance
	protected abstract V readNotNullObject(long address, V instance);
	
	//get an instance of specified class
	protected V newInstance(Class<V> cls){
		try {
			return (V) unsafe.allocateInstance(cls);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
