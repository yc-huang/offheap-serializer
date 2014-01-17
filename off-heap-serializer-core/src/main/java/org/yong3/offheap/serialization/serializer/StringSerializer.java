package org.yong3.offheap.serialization.serializer;

import org.yong3.offheap.serialization.OffheapUtils;
import org.yong3.offheap.serialization.Serializer;
import org.yong3.offheap.serialization.SerializerFactory;

import sun.misc.Unsafe;

public class StringSerializer implements Serializer<String> {
	// store as byte[] is more compact as char[]
	final static boolean COMPACT = true;
	protected static Serializer<char[]> charArraySerializer = SerializerFactory
			.get(char[].class);
	protected static Serializer<byte[]> byteArraySerializer = SerializerFactory
			.get(byte[].class);
	protected static Unsafe unsafe;
	protected static long valueOffset;

	static {
		try {
			unsafe = OffheapUtils.unsafe;
			valueOffset = unsafe.objectFieldOffset(String.class
					.getDeclaredField("value"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.err.println(byteArraySerializer.getClass());
	}

	@Override
	public int getOffheapSize(String instance) {
		if (COMPACT) {
			return byteArraySerializer
					.getOffheapSize((instance != null) ? instance.getBytes()
							: null);
		} else {
			return charArraySerializer
					.getOffheapSize((instance != null) ? instance.toCharArray()
							: null);
		}
	}

	@Override
	public int write(long address, String instance) {
		if (COMPACT) {
			return byteArraySerializer.write(address,
					(instance != null) ? instance.getBytes() : null);
		} else {
			return charArraySerializer.write(address,
					(instance != null) ? instance.toCharArray() : null);
		}
	}

	@Override
	public String read(long addr, Class<String> cls) {
		return read(addr, (String)null);
	}

	@Override
	public String read(long addr, String stub) {
		try {
			if (COMPACT) {
				byte[] bytes = byteArraySerializer.read(addr, byte[].class);
				return (bytes != null) ? new String(bytes) : null;
			} else {
				char[] chars = charArraySerializer.read(addr, char[].class);
				if (chars != null) {
					if(stub == null) stub = (String) unsafe.allocateInstance(String.class);
					unsafe.putObject(stub, valueOffset, chars);
					return stub;
				} else {
					return null;
				}
			}
		} catch (InstantiationException e) {
			return null;
		}
	}

}
