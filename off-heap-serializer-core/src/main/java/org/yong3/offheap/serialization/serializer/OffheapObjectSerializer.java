package org.yong3.offheap.serialization.serializer;

import org.yong3.offheap.serialization.OffheapObject;

public class OffheapObjectSerializer<T extends OffheapObject> extends BaseObjectSerializer<T> {

	@Override
	protected int getNotNullObjectSize(T instance) {
		return allocator.sizeOf(instance.getOffheapAddress());
	}

	@Override
	protected int writeNotNullObjectSize(long address, T instance) {
		long addr = instance.getOffheapAddress();
		//System.err.println("write " + addr + " to " + address);
		return allocator.putLong(address, addr);
	}

	@Override
	protected T readNotNullObject(long address, T instance) {
		long addr = allocator.getLong(address);
		//System.err.println("read " + addr + " from " + address);
		instance.setOffheapAddress(addr);
		return instance;
	}

}
