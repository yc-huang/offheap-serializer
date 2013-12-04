package org.yong3.offheap.serialization.serializer;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.yong3.offheap.serialization.MemoryAllocatorFactory;

public class StringSerializerTest {

	StringSerializer serializer = new StringSerializer();
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetNotNullObjectSizeString() {
		readAndWrite(null);
		readAndWrite("");
		readAndWrite("123456");
	}
	
	private void readAndWrite(String s) {
		int size = serializer.getOffheapSize(s);
		long addr = MemoryAllocatorFactory.get().allocate(size);
		
		int wSize = serializer.write(addr, s);
		assertEquals(size, wSize);
		
		String r = serializer.read(addr, String.class);
		assertEquals(s, r);
	}

}
