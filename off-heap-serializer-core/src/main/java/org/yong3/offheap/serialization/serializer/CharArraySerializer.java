package org.yong3.offheap.serialization.serializer;

public class CharArraySerializer extends BaseObjectSerializer<char[]>{
	
	@Override
	protected int getNotNullObjectSize(char[] instance) {
		return instance.length * allocator.fixSizeOf(char.class) + allocator.fixSizeOf(int.class);
	}

	@Override
	protected int writeNotNullObjectSize(long address, char[] instance) {
		return allocator.putCharArray(address, instance);
	}

	@Override
	protected char[] readNotNullObject(long address, char[] instance) {
		return allocator.getCharArray(address);
	}

	@Override
	protected char[] newInstance(Class<char[]> cls){
		return null;
	}
}
