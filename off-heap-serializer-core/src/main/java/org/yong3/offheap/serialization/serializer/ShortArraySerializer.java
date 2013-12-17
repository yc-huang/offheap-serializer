package org.yong3.offheap.serialization.serializer;

public class ShortArraySerializer extends BaseObjectSerializer<short[]>{
	
	@Override
	protected int getNotNullObjectSize(short[] instance) {
		return instance.length * allocator.fixSizeOf(short.class) + allocator.fixSizeOf(int.class);
	}

	@Override
	protected int writeNotNullObjectSize(long address, short[] instance) {
		return allocator.putShortArray(address, instance);
	}

	@Override
	protected short[] readNotNullObject(long address, short[] instance) {
		return allocator.getShortArray(address);
	}

	@Override
	protected short[] newInstance(Class<short[]> cls){
		return null;
	}
}
