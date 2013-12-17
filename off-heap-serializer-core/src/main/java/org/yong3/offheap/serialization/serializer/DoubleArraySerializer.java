package org.yong3.offheap.serialization.serializer;

public class DoubleArraySerializer extends BaseObjectSerializer<double[]>{
	
	@Override
	protected int getNotNullObjectSize(double[] instance) {
		return instance.length * allocator.fixSizeOf(double.class) + allocator.fixSizeOf(int.class);
	}

	@Override
	protected int writeNotNullObjectSize(long address, double[] instance) {
		return allocator.putDoubleArray(address, instance);
	}

	@Override
	protected double[] readNotNullObject(long address, double[] instance) {
		return allocator.getDoubleArray(address);
	}

	@Override
	protected double[] newInstance(Class<double[]> cls){
		return null;
	}
}
