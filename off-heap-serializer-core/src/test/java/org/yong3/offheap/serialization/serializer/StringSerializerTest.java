package org.yong3.offheap.serialization.serializer;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.ComparisonFailure;
import org.junit.Test;
import org.yong3.offheap.serialization.MemoryAllocator;
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
		
		for(int i = 0; i < 10000000; i++){
			readAndWrite("" + i);
		}
	}
	
	private void readAndWrite(String s) {
		int size = serializer.getOffheapSize(s);
		MemoryAllocator allocator = MemoryAllocatorFactory.get();
		long addr = allocator.allocate(size);
		
		int wSize = serializer.write(addr, s);
		assertEquals(size, wSize);
		
		String r = serializer.read(addr, String.class);
		try{
			assertEquals(s, r);
		}catch(ComparisonFailure fail){
			System.err.println(size);
			for(int i = 0; i < size; i++){
				System.err.print(Integer.toHexString(allocator.getByte(addr + i)));
				System.err.print(' ');
			}
			throw fail;
		}
		
		allocator.deallocate(addr);
	}

}
