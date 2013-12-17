package org.yong3.offheap.serialization.allocator;

import org.yong3.offheap.serialization.MemoryAllocator;
import org.yong3.offheap.serialization.OffheapUtils;

import sun.misc.Unsafe;

@SuppressWarnings({"restriction", "rawtypes"})
public class SimpleMemoryAllocator implements MemoryAllocator {
	protected static Unsafe unsafe = OffheapUtils.unsafe;

	public static final byte SIZE_OF_BYTE = 1;
	public static final byte SIZE_OF_BOOLEAN = 1;
	public static final byte SIZE_OF_SHORT = 2;
	public static final byte SIZE_OF_CHAR = 2;
	public static final byte SIZE_OF_INT = 4;
	public static final byte SIZE_OF_LONG = 8;
	public static final byte SIZE_OF_FLOAT = 4;
	public static final byte SIZE_OF_DOUBLE = 8;

	public static final byte BYTE_BOOLEAN_TRUE = 1;
	public static final byte BYTE_BOOLEAN_FALSE = 0;
	
	public static final int ARRAY_INT_INDEX_SCALE = Unsafe.ARRAY_INT_INDEX_SCALE;
	public static final int ARRAY_SHORT_INDEX_SCALE = Unsafe.ARRAY_SHORT_INDEX_SCALE;
	public static final int ARRAY_BYTE_INDEX_SCALE = Unsafe.ARRAY_BYTE_INDEX_SCALE;
	public static final int ARRAY_CHAR_INDEX_SCALE = Unsafe.ARRAY_CHAR_INDEX_SCALE;
	public static final int ARRAY_FLOAT_INDEX_SCALE = Unsafe.ARRAY_FLOAT_INDEX_SCALE;
	public static final int ARRAY_LONG_INDEX_SCALE = Unsafe.ARRAY_LONG_INDEX_SCALE;
	public static final int ARRAY_DOUBLE_INDEX_SCALE = Unsafe.ARRAY_DOUBLE_INDEX_SCALE;
	public static final int ARRAY_BOOLEAN_INDEX_SCALE = Unsafe.ARRAY_BOOLEAN_INDEX_SCALE;
	public static final int ARRAY_OBJECT_INDEX_SCALE = Unsafe.ARRAY_OBJECT_INDEX_SCALE;
	
	public static final int ARRAY_INT_BASE_OFFSET = Unsafe.ARRAY_INT_BASE_OFFSET;
	public static final int ARRAY_BYTE_BASE_OFFSET = Unsafe.ARRAY_BYTE_BASE_OFFSET;
	public static final int ARRAY_BOOLEAN_BASE_OFFSET = Unsafe.ARRAY_BOOLEAN_BASE_OFFSET;
	public static final int ARRAY_SHORT_BASE_OFFSET = Unsafe.ARRAY_SHORT_BASE_OFFSET;
	public static final int ARRAY_CHAR_BASE_OFFSET = Unsafe.ARRAY_CHAR_BASE_OFFSET;
	public static final int ARRAY_LONG_BASE_OFFSET = Unsafe.ARRAY_LONG_BASE_OFFSET;
	public static final int ARRAY_DOUBLE_BASE_OFFSET = Unsafe.ARRAY_DOUBLE_BASE_OFFSET;
	public static final int ARRAY_FLOAT_BASE_OFFSET = Unsafe.ARRAY_FLOAT_BASE_OFFSET;
	public static final int ARRAY_OBJECT_BASE_OFFSET = Unsafe.ARRAY_OBJECT_BASE_OFFSET;
	

	public long allocate(long size) {
		return unsafe.allocateMemory(size);
	}
	
	public void deallocate(long address){
		unsafe.freeMemory(address);
	}

	public int putInt(long addr, int v) {
		unsafe.putInt(addr, v);
		return SIZE_OF_INT;
	}

	public int putLong(long addr, long v) {
		unsafe.putLong(addr, v);
		return SIZE_OF_LONG;
	}

	public int putByte(long addr, byte v) {
		unsafe.putByte(addr, v);
		return SIZE_OF_BYTE;
	}

	public int putBoolean(long addr, boolean v) {
		return putByte(addr, (v) ? BYTE_BOOLEAN_TRUE : BYTE_BOOLEAN_FALSE);
	}

	public int putChar(long addr, char v) {
		unsafe.putChar(addr, v);
		return SIZE_OF_CHAR;
	}

	public int putShort(long addr, short v) {
		unsafe.putShort(addr, v);
		return SIZE_OF_SHORT;
	}

	public int putFloat(long addr, float v) {
		unsafe.putFloat(addr, v);
		return SIZE_OF_FLOAT;
	}

	public int putDouble(long addr, double v) {
		unsafe.putDouble(addr, v);
		return SIZE_OF_DOUBLE;
	}

	public int getInt(long addr) {
		return unsafe.getInt(addr);
	}

	public long getLong(long addr) {
		return unsafe.getLong(addr);
	}

	public byte getByte(long addr) {
		return unsafe.getByte(addr);
	}

	public boolean getBoolean(long addr) {
		return getByte(addr) == BYTE_BOOLEAN_TRUE;
	}

	public char getChar(long addr) {
		return unsafe.getChar(addr);
	}

	public short getShort(long addr) {
		return unsafe.getShort(addr);
	}

	public float getFloat(long addr) {
		return unsafe.getFloat(addr);
	}

	public double getDouble(long addr) {
		return unsafe.getDouble(addr);
	}

	public int putIntArray(long addr, int[] v) {
		int arrayLen = v.length;
		unsafe.putInt(addr, arrayLen);
		int len = ARRAY_INT_INDEX_SCALE * arrayLen;
		unsafe.copyMemory(v, ARRAY_INT_BASE_OFFSET, null, addr + SIZE_OF_INT, len);
		return len + SIZE_OF_INT;
	}

	public int putLongArray(long addr, long[] v) {
		int arrayLen = v.length;
		unsafe.putInt(addr, arrayLen);
		int len = ARRAY_LONG_INDEX_SCALE * arrayLen;
		unsafe.copyMemory(v, ARRAY_LONG_BASE_OFFSET, null, addr + SIZE_OF_INT, len);
		return len + SIZE_OF_INT;
	}

	public int putByteArray(long addr, byte[] v) {
		int arrayLen = v.length;
		unsafe.putInt(addr, arrayLen);
		int len = ARRAY_BYTE_INDEX_SCALE * arrayLen;
		unsafe.copyMemory(v, ARRAY_BYTE_BASE_OFFSET, null, addr + SIZE_OF_INT, len);
		return len + SIZE_OF_INT;
	}

	public int putBooleanArray(long addr, boolean[] v) {
		int arrayLen = v.length;
		unsafe.putInt(addr, arrayLen);
		int len = ARRAY_BOOLEAN_INDEX_SCALE * arrayLen;
		unsafe.copyMemory(v, ARRAY_BOOLEAN_BASE_OFFSET, null, addr + SIZE_OF_INT, len);
		return len + SIZE_OF_INT;
	}

	public int putCharArray(long addr, char[] v) {
		int arrayLen = v.length;
		unsafe.putInt(addr, arrayLen);
		int len = ARRAY_CHAR_INDEX_SCALE * arrayLen;
		unsafe.copyMemory(v, ARRAY_CHAR_BASE_OFFSET, null, addr + SIZE_OF_INT, len);
		return len + SIZE_OF_INT;
	}

	public int putShortArray(long addr, short[] v) {
		int arrayLen = v.length;
		unsafe.putInt(addr, arrayLen);
		int len = ARRAY_SHORT_INDEX_SCALE * arrayLen;
		unsafe.copyMemory(v, ARRAY_SHORT_BASE_OFFSET, null, addr + SIZE_OF_INT, len);
		return len + SIZE_OF_INT;
	}

	public int putFloatArray(long addr, float[] v) {
		int arrayLen = v.length;
		unsafe.putInt(addr, arrayLen);
		int len = ARRAY_FLOAT_INDEX_SCALE * arrayLen;
		unsafe.copyMemory(v, ARRAY_FLOAT_BASE_OFFSET, null, addr + SIZE_OF_INT, len);
		return len + SIZE_OF_INT;
	}

	public int putDoubleArray(long addr, double[] v) {
		int arrayLen = v.length;
		unsafe.putInt(addr, arrayLen);
		int len = ARRAY_DOUBLE_INDEX_SCALE * arrayLen;
		unsafe.copyMemory(v, ARRAY_DOUBLE_BASE_OFFSET, null, addr + SIZE_OF_INT, len);
		return len + SIZE_OF_INT;
	}

	public int[] getIntArray(long addr) {
		int len = unsafe.getInt(addr);
		int[] ret = new int[len];
		int bytes = ARRAY_INT_INDEX_SCALE * len;
		unsafe.copyMemory(null,addr + SIZE_OF_INT, ret, ARRAY_INT_BASE_OFFSET, bytes);
		return ret;
	}

	public long[] getLongArray(long addr) {
		int len = unsafe.getInt(addr);
		long[] ret = new long[len];
		int bytes = ARRAY_LONG_INDEX_SCALE * len;
		unsafe.copyMemory(null,addr + SIZE_OF_INT, ret, ARRAY_LONG_BASE_OFFSET, bytes);
		return ret;
	}

	public byte[] getByteArray(long addr) {
		int len = unsafe.getInt(addr);
		byte[] ret = new byte[len];
		int bytes = ARRAY_BYTE_INDEX_SCALE * len;
		unsafe.copyMemory(null,addr + SIZE_OF_INT, ret, ARRAY_BYTE_BASE_OFFSET, bytes);
		return ret;
	}

	public boolean[] getBooleanArray(long addr) {
		int len = unsafe.getInt(addr);
		boolean[] ret = new boolean[len];
		int bytes = ARRAY_BYTE_INDEX_SCALE * len;
		unsafe.copyMemory(null,addr + SIZE_OF_INT, ret, ARRAY_BYTE_BASE_OFFSET, bytes);
		return ret;
	}

	public char[] getCharArray(long addr) {
		int len = unsafe.getInt(addr);
		char[] ret = new char[len];
		int bytes = ARRAY_CHAR_INDEX_SCALE * len;
		if(len > 0) unsafe.copyMemory(null,addr + SIZE_OF_INT, ret, ARRAY_CHAR_BASE_OFFSET, bytes);
		return ret;
	}

	public short[] getShortArray(long addr) {
		int len = unsafe.getInt(addr);
		short[] ret = new short[len];
		int bytes = ARRAY_SHORT_INDEX_SCALE * len;
		unsafe.copyMemory(null,addr + SIZE_OF_INT, ret, ARRAY_SHORT_BASE_OFFSET, bytes);
		return ret;
	}

	public float[] getFloatArray(long addr) {
		int len = unsafe.getInt(addr);
		float[] ret = new float[len];
		int bytes = ARRAY_FLOAT_INDEX_SCALE * len;
		unsafe.copyMemory(null,addr + SIZE_OF_INT, ret, ARRAY_FLOAT_BASE_OFFSET, bytes);
		return ret;
	}

	public double[] getDoubleArray(long addr) {
		int len = unsafe.getInt(addr);
		double[] ret = new double[len];
		int bytes = ARRAY_DOUBLE_INDEX_SCALE * len;
		unsafe.copyMemory(null,addr + SIZE_OF_INT, ret, ARRAY_DOUBLE_BASE_OFFSET, bytes);
		return ret;
	}

	public boolean isFixLength(Class cls) {
		if (cls.isPrimitive())
			return true;
		else
			return false;
	}

	public int fixSizeOf(Class cls) {
		if (cls == int.class) {
			return SIZE_OF_INT;
		} else if (cls == byte.class) {
			return SIZE_OF_BYTE;
		} else if (cls == long.class) {
			return SIZE_OF_LONG;
		} else if (cls == char.class) {
			return SIZE_OF_CHAR;
		} else if (cls == boolean.class) {
			return SIZE_OF_BOOLEAN;
		} else if (cls == short.class) {
			return SIZE_OF_SHORT;
		} else if (cls == float.class) {
			return SIZE_OF_FLOAT;
		} else if (cls == double.class) {
			return SIZE_OF_DOUBLE;
		}
		return -1;
	}

	public int sizeOf(int v) {
		return SIZE_OF_INT;
	}

	public int sizeOf(long v) {
		return SIZE_OF_LONG;
	}

	public int sizeOf(byte v) {
		return SIZE_OF_BYTE;
	}

	public int sizeOf(boolean v) {
		return SIZE_OF_BOOLEAN;
	}

	public int sizeOf(char v) {
		return SIZE_OF_CHAR;
	}

	public int sizeOf(short v) {
		return SIZE_OF_SHORT;
	}

	public int sizeOf(float v) {
		return SIZE_OF_FLOAT;
	}

	public int sizeOf(double v) {
		return SIZE_OF_DOUBLE;
	}

	public int sizeOf(Class type, Object instance, long fieldOffset) {
		if (type == int.class) {
			return sizeOf(unsafe.getInt(instance, fieldOffset));
		} else if (type == long.class) {
			return sizeOf(unsafe.getLong(instance, fieldOffset));
		} else if (type == byte.class) {
			return sizeOf(unsafe.getByte(instance, fieldOffset));
		} else if (type == char.class) {
			return sizeOf(unsafe.getChar(instance, fieldOffset));
		} else if (type == boolean.class) {
			return sizeOf(unsafe.getBoolean(instance, fieldOffset));
		} else if (type == short.class) {
			return sizeOf(unsafe.getShort(instance, fieldOffset));
		} else if (type == float.class) {
			return sizeOf(unsafe.getFloat(instance, fieldOffset));
		} else if (type == double.class) {
			return sizeOf(unsafe.getDouble(instance, fieldOffset));
		}else{
			return -1;
		}
	}
}
