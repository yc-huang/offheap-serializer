package org.yong3.offheap.serialization.serializer;

public class FloatArraySerializer extends BaseObjectSerializer<float[]>{
	
	@Override
	protected int getNotNullObjectSize(float[] instance) {
		return instance.length * allocator.fixSizeOf(float.class) + allocator.fixSizeOf(int.class);
	}

	@Override
	protected int writeNotNullObjectSize(long address, float[] instance) {
		return allocator.putFloatArray(address, instance);
	}

	@Override
	protected float[] readNotNullObject(long address, float[] instance) {
		return allocator.getFloatArray(address);
	}

	@Override
	protected float[] newInstance(Class<float[]> cls){
		return null;
	}
}
