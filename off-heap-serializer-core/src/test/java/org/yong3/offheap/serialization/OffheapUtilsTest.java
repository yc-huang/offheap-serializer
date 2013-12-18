package org.yong3.offheap.serialization;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class OffheapUtilsTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCopyByteArrayToOffheap() {
		Serializer<String> serializer = SerializerFactory.get(String.class);
		String s = "123456abcdef@#$%^&汉字";
		int size = serializer.getOffheapSize(s);
		MemoryAllocator allocator = MemoryAllocatorFactory.get();
		long addr = allocator.allocate(size);
		
		int wSize = serializer.write(addr, s);
		assertEquals(size, wSize);
		
		byte[] bytes = new byte[size];
		OffheapUtils.readByteArrayromOffheap(addr, bytes, 0, size);
		allocator.deallocate(addr);
		
		addr = allocator.allocate(size);
		OffheapUtils.writeByteArrayToOffheap(bytes, 0, addr, size);
		
		String r = serializer.read(addr, String.class);
		assertEquals(s, r);
		
		allocator.deallocate(addr);
	}

}
