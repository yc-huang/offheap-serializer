package org.yong3.offheap.serialization.serializer;

public class IntArraySerializer extends BaseObjectSerializer<int[]>{
	
	@Override
	protected int getNotNullObjectSize(int[] instance) {
		return instance.length * allocator.fixSizeOf(int.class) + allocator.fixSizeOf(int.class);
	}

	@Override
	protected int writeNotNullObjectSize(long address, int[] instance) {
		return allocator.putIntArray(address, instance);
	}

	@Override
	protected int[] readNotNullObject(long address, int[] instance) {
		return allocator.getIntArray(address);
	}

	@Override
	protected int[] newInstance(Class<int[]> cls){
		return null;
	}
}
