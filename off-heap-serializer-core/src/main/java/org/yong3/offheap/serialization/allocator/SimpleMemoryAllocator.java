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

//	public int putIntArray(long addr, int[] v) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	public int putLongArray(long addr, long[] v) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	public int putByteArray(long addr, byte[] v) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	public int putBooleanArray(long addr, boolean[] v) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	public int putCharArray(long addr, char[] v) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	public int putShortArray(long addr, short[] v) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	public int putFloatArray(long addr, float[] v) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	public int putDoubleArray(long addr, double[] v) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	public int[] getIntArray(long addr) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	public long[] getLongArray(long addr) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	public byte[] getByteArray(long addr) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	public boolean[] getBooleanArray(long addr) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	public char[] getCharArray(long addr) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	public short[] getShortArray(long addr) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	public float[] getFloatArray(long addr) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	public double[] getDoubleArray(long addr) {
//		// TODO Auto-generated method stub
//		return null;
//	}

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
