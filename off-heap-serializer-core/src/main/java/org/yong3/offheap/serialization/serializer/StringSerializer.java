package org.yong3.offheap.serialization.serializer;

import org.yong3.offheap.serialization.SerializerFactory;

public class StringSerializer extends BaseObjectSerializer<String> {
	final static boolean COMPACT = false;
	CharArraySerializer charArraySerializer = (CharArraySerializer) SerializerFactory
			.get(char[].class);
	ByteArraySerializer byteArraySerializer = (ByteArraySerializer) SerializerFactory
			.get(byte[].class);

	public int getNotNullObjectSize(String instance) {
		return (COMPACT) ? byteArraySerializer.getNotNullObjectSize(instance
				.getBytes()) : charArraySerializer
				.getNotNullObjectSize(instance.toCharArray());
	}

	@Override
	protected int writeNotNullObjectSize(long address, String instance) {
		return (COMPACT) ? byteArraySerializer.writeNotNullObjectSize(address,
				instance.getBytes()) : charArraySerializer
				.writeNotNullObjectSize(address, instance.toCharArray());
	}

	@Override
	protected String readNotNullObject(long address, String instance) {
		return (COMPACT) ? new String(byteArraySerializer.readNotNullObject(
				address, null)) : new String(
				charArraySerializer.readNotNullObject(address, null));
	}

	@Override
	protected String newInstance(Class<String> cls) {
		return null;
	}

}
