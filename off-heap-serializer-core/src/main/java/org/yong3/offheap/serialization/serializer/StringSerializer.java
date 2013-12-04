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
		return charArraySerializer.writeNotNullObjectSize(address,
				instance.toCharArray());
	}

	@Override
	protected String readNotNullObject(long addr, String instance) {
		char[] chars = null;
		chars = charArraySerializer.readNotNullObject(addr, null);
		return new String(chars);
	}

	@Override
	protected String newInstance(Class<String> cls) {
		return null;
	}

}
