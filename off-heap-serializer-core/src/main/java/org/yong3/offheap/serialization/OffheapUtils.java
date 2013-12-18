package org.yong3.offheap.serialization;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

@SuppressWarnings("restriction")
public class OffheapUtils {
	private final static int ARRAY_BYTE_BASE_OFFSET = Unsafe.ARRAY_BYTE_BASE_OFFSET;
	
	public static Unsafe unsafe;
	static {
		try {
			Field field = Unsafe.class.getDeclaredField("theUnsafe");
			field.setAccessible(true);
			unsafe = (Unsafe) field.get(null);
		} catch (Exception e) {
		}
	}
	
	
	public static void writeByteArrayToOffheap(byte[] bytes, int offset, long toAddress, int length){
		unsafe.copyMemory(bytes, ARRAY_BYTE_BASE_OFFSET + offset, null, toAddress, length);
	}
	
	public static void readByteArrayromOffheap(long fromAddress, byte[] bytes, int offset, int length){
		unsafe.copyMemory(null, fromAddress, bytes, ARRAY_BYTE_BASE_OFFSET + offset, length);
	}
	
	public static void writeArrayToOffheap(Object fromArray, int baseOffset, int len, long toAddress){
		unsafe.copyMemory(fromArray, baseOffset, null, toAddress, len);
	}
	
	public static void readArrayromOffheap(long fromAddress, int len, Object toArray, int baseOffset){
		unsafe.copyMemory(null, fromAddress, toArray, baseOffset, len);
	}
}
