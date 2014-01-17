package org.yong3.offheap.serialization.serializer;

import org.yong3.offheap.serialization.OffheapUtils;
import org.yong3.offheap.serialization.Serializer;
import org.yong3.offheap.serialization.SerializerFactory;

import sun.misc.Unsafe;

public class StringSerializer implements Serializer<String> {
	// store as byte[] is more compact as char[]
	final static boolean COMPACT = true;
	protected static CharArraySerializer charArraySerializer = (CharArraySerializer) SerializerFactory
			.get(char[].class);
	protected static ByteArraySerializer byteArraySerializer = (ByteArraySerializer) SerializerFactory
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
		if (COMPACT) {
			byte[] bytes = byteArraySerializer.read(addr, (byte[]) null);
			return (bytes != null) ? new String(bytes) : null;
		} else {
			char[] chars = charArraySerializer.read(addr, (char[]) null);
			if (chars != null) {
				String s = null;
				try {
					s = (String) unsafe.allocateInstance(String.class);
				} catch (InstantiationException e) {
				}
				unsafe.putObject(s, valueOffset, chars);
				return s;
			} else {
				return null;
			}
		}
	}

	@Override
	public String read(long addr, String stub) throws InstantiationException {
		return read(addr, String.class);
	}

}
