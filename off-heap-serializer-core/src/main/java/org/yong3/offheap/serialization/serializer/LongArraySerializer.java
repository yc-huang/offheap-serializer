package org.yong3.offheap.serialization.serializer;

public class LongArraySerializer extends BaseObjectSerializer<long[]>{
	
	@Override
	protected int getNotNullObjectSize(long[] instance) {
		return instance.length * allocator.fixSizeOf(long.class) + allocator.fixSizeOf(int.class);
	}

	@Override
	protected int writeNotNullObjectSize(long address, long[] instance) {
		return allocator.putLongArray(address, instance);
	}

	@Override
	protected long[] readNotNullObject(long address, long[] instance) {
		return allocator.getLongArray(address);
	}

	@Override
	protected long[] newInstance(Class<long[]> cls){
		return null;
	}
}
