package org.yong3.offheap.serialization.serializer;

public class BooleanArraySerializer extends BaseObjectSerializer<boolean[]>{
	
	@Override
	protected int getNotNullObjectSize(boolean[] instance) {
		return instance.length * allocator.fixSizeOf(boolean.class) + allocator.fixSizeOf(int.class);
	}

	@Override
	protected int writeNotNullObjectSize(long address, boolean[] instance) {
		return allocator.putBooleanArray(address, instance);
	}

	@Override
	protected boolean[] readNotNullObject(long address, boolean[] instance) {
		return allocator.getBooleanArray(address);
	}

	@Override
	protected boolean[] newInstance(Class<boolean[]> cls){
		return null;
	}
}
