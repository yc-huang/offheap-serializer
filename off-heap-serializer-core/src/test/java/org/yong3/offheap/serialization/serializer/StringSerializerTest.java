package org.yong3.offheap.serialization.serializer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.ComparisonFailure;
import org.junit.Test;
import org.yong3.offheap.serialization.MemoryAllocator;
import org.yong3.offheap.serialization.MemoryAllocatorFactory;
import org.yong3.offheap.serialization.Serializer;
import org.yong3.offheap.serialization.SerializerFactory;

public class StringSerializerTest {

	StringSerializer serializer = new StringSerializer();
	CharArraySerializer charArraySerializer = new CharArraySerializer();

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetNotNullObjectSizeString() throws InstantiationException {
		readAndWrite(null);
		readAndWrite("");
		readAndWrite("123456");

		for (int i = 10000; i < 10000000; i++) {
			readAndWrite(String.valueOf(i));
			// readAndWriteAsCharArray(String.valueOf(i).toCharArray());
		}
	}

	private void readAndWrite(String s) {
		int size = serializer.getOffheapSize(s);
		MemoryAllocator allocator = MemoryAllocatorFactory.get();
		long addr = allocator.allocate(size);
		try {
			int wSize = serializer.write(addr, s);
			assertEquals(size, wSize);

			String r = serializer.read(addr, String.class);
			try {
				assertEquals(s, r);
			} catch (ComparisonFailure fail) {
				System.err.println(size);
				for (int i = 0; i < size; i++) {
					System.err.print(Integer.toHexString(allocator.getByte(addr
							+ i)));
					System.err.print(' ');
				}
				throw fail;
			}

		} finally {
			allocator.deallocate(addr);
		}
	}

	private void readAndWriteAsCharArray(char[] chars)
			throws InstantiationException {
		int size = charArraySerializer.getOffheapSize(chars);
		MemoryAllocator allocator = MemoryAllocatorFactory.get();
		long addr = allocator.allocate(size);

		try {
			int wSize = charArraySerializer.write(addr, chars);
			assertEquals(size, wSize);

			char[] r = charArraySerializer.read(addr, (char[]) null);
			assertArrayEquals(chars, r);
		} finally {
			allocator.deallocate(addr);
		}
	}

}
