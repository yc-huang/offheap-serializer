package org.yong3.offheap.serialization.serializer;


public class StringSerializer extends BaseObjectSerializer<String> {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	ObjectArraySerializer<char[]> charArraySerializer = new ObjectArraySerializer(
			char[].class);

	public int getNotNullObjectSize(String instance) {
		return charArraySerializer.getNotNullObjectSize(instance.toCharArray());
	}

	@Override
	protected int writeNotNullObjectSize(long address, String instance) {
//		return charArraySerializer.writeNotNullObjectSize(address,
//				instance.toCharArray());
		long addr = address;
		char[] chars = instance.toCharArray();
		allocator.putInt(addr, chars.length);
		addr += allocator.sizeOf(chars.length);
		for(char c : chars){
			allocator.putChar(addr, c);
			addr += allocator.sizeOf(c);
		}
		
		return (int) (addr - address);
	}

	@Override
	protected String readNotNullObject(long address, String instance) {
		char[] chars = null;
//		chars = charArraySerializer.readNotNullObject(address, null);
//		return new String(chars);
		long addr = address;
		chars = new char[allocator.getInt(addr)];
		addr += allocator.sizeOf(chars.length);
		for(int i = 0; i < chars.length; i++){
			chars[i] = allocator.getChar(addr);
			addr += allocator.sizeOf(chars[i]);
		}
		
		return new String(chars);
	}

	@Override
	protected String newInstance(Class<String> cls) {
		return null;
	}

}