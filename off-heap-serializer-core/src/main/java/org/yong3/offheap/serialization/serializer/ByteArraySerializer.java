package org.yong3.offheap.serialization.serializer;

public class ByteArraySerializer extends BaseObjectSerializer<byte[]>{
	
	@Override
	protected int getNotNullObjectSize(byte[] instance) {
		return instance.length * allocator.fixSizeOf(byte.class) + allocator.fixSizeOf(int.class);
	}

	@Override
	protected int writeNotNullObjectSize(long address, byte[] instance) {
		return allocator.putByteArray(address, instance);
	}

	@Override
	protected byte[] readNotNullObject(long address, byte[] instance) {
		return allocator.getByteArray(address);
	}

	@Override
	protected byte[] newInstance(Class<byte[]> cls){
		return null;
	}
}
