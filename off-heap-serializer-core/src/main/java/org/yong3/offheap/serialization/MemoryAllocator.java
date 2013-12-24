package org.yong3.offheap.serialization;

/**
 * 
 * @author yc
 * 
 *         Proxy use to manipulate off-heap memory
 */
@SuppressWarnings("rawtypes")
public interface MemoryAllocator {

	/**
	 * allocate a block of memory with the given size in bytes.
	 * 
	 * @param size
	 *            size in bytes
	 * @return absolute off-heap address, but might not the same as what
	 *         unsafe.allocateMemory returned
	 *         
	 * @throws IllegalArgumentException
	 *             if the size is negative or too large for the native size_t
	 *             type
	 * 
	 * @throws OutOfMemoryError
	 *             if the allocation is refused by the system
	 */
	public long allocate(long size);
	

	/**
	 * change the block of memory at given address to the specified size.
	 * 
	 * @param address
	 *            off-heap address of a memory block
	 * @param size
	 *            new size, in bytes
	 * @return absolute off-heap address, but might not the same as what
	 *         unsafe.allocateMemory returned
	 *         
	 * @throws IllegalArgumentException
	 *             if the size is negative or too large for the native size_t
	 *             type
	 * 
	 * @throws OutOfMemoryError
	 *             if the allocation is refused by the system
	 */
	public long reallocate(long address, long size);
	
	/**
	 * free memory block represent by given address.
	 * @param address
	 */
	public void deallocate(long address);
	
	/**
	 * store a primitive value to given off-heap memory address.
	 * @param addr absolute address to write the value
	 * @param v primitive value to be copied
	 * @return size of off-heap memory occupied by this
	 */
	public int putInt(long addr, int v);
	public int putLong(long addr, long v);
	public int putByte(long addr, byte v);
	public int putBoolean(long addr, boolean v);
	public int putChar(long addr, char v);
	public int putShort(long addr, short v);
	public int putFloat(long addr, float v);
	public int putDouble(long addr, double v);

	public int putIntArray(long addr, int[] v);
	public int putLongArray(long addr, long[] v);
	public int putByteArray(long addr, byte[] v);
	public int putBooleanArray(long addr, boolean[] v);
	public int putCharArray(long addr, char[] v);
	public int putShortArray(long addr, short[] v);
	public int putFloatArray(long addr, float[] v);
	public int putDoubleArray(long addr, double[] v);
	
	/**
	 * read a primitive value from given off-heap memory address.
	 * @param addr absolute address to read from
	 * @return 
	 */	
	public int getInt(long addr);
	public long getLong(long addr);
	public byte getByte(long addr);
	public boolean getBoolean(long addr);
	public char getChar(long addr);
	public short getShort(long addr);
	public float getFloat(long addr);
	public double getDouble(long addr);

	
	public int[] getIntArray(long addr);
	public long[] getLongArray(long addr);
	public byte[] getByteArray(long addr);
	public boolean[] getBooleanArray(long addr);
	public char[] getCharArray(long addr);
	public short[] getShortArray(long addr);
	public float[] getFloatArray(long addr);
	public double[] getDoubleArray(long addr);
	
	//test if a class will be serialized as fixed size
	public boolean isFixLength(Class cls);
	
	//return the size of a serialized fix-length class
	public int fixSizeOf(Class cls);
	
	public int sizeOf(int v);
	public int sizeOf(long v);
	public int sizeOf(byte v);
	public int sizeOf(boolean v);
	public int sizeOf(char v);
	public int sizeOf(short v);
	public int sizeOf(float v);
	public int sizeOf(double v);
	
	public int sizeOf(Class type, Object instance, long fieldOffset);
}
