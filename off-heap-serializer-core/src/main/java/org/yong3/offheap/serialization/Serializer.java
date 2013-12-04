package org.yong3.offheap.serialization;

/**
 * @author yc
 * 
 *         Serializer which copy content of an instance of V to off-heap memory
 *         and read it back.
 * @param <V>
 */
public interface Serializer<V> {
	/**
	 * return the required off-heap memory size of this object.
	 * 
	 * @param instance
	 *            an instance of V
	 * @return off-heap size in bytes
	 */
	int getOffheapSize(V instance);

	/**
	 * write the instance to specified off-heap address.
	 * 
	 * @param address
	 *            absolute off-heap address to write
	 * @param instance
	 *            an instance of V to be copied to off-heap
	 */
	int write(long address, V instance);

	/**
	 * read an object instance back from specified off-heap address.
	 * 
	 * @param addr
	 *            absolute off-heap address to read
	 * @param clz
	 * @return an instance of V with it's fields filled from off-heap
	 * @throws InstantiationException
	 */
	V read(long addr, Class<V> cls);// throws InstantiationException;

	/**
	 * read an object instance back from specified off-heap address. reuse the
	 * passed in instance without needing to create one.
	 * 
	 * @param addr
	 *            absolute off-heap address to read
	 * @param stub
	 *            passed in instance of V, it's fields will be filled from
	 *            off-heap
	 * @return instance of V
	 * @throws InstantiationException
	 */
	V read(long addr, V stub) throws InstantiationException;
}
