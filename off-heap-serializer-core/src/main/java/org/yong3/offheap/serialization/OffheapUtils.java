package org.yong3.offheap.serialization;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

@SuppressWarnings("restriction")
public class OffheapUtils {
	
	public static Unsafe unsafe;
	static {
		try {
			Field field = Unsafe.class.getDeclaredField("theUnsafe");
			field.setAccessible(true);
			unsafe = (Unsafe) field.get(null);
		} catch (Exception e) {
		}
	}
	
	
	public static void copyArrayToOffheap(Object fromArray, int baseOffset, int len, long toAddress){
		unsafe.copyMemory(fromArray, baseOffset, null, toAddress, len);
	}
	
	public static void readArrayromOffheap(long fromAddress, int len, Object toArray, int baseOffset){
		unsafe.copyMemory(null, fromAddress, toArray, baseOffset, len);
	}
}
