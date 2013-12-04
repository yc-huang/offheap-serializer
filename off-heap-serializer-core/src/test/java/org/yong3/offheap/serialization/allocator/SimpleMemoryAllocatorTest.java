package org.yong3.offheap.serialization.allocator;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SimpleMemoryAllocatorTest {

	SimpleMemoryAllocator allocator;
	@Before
	public void setUp() throws Exception {
		allocator = new SimpleMemoryAllocator();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAllocate() {
		long addr = allocator.allocate(10);
		assertTrue(addr > 0);
		allocator.deallocate(addr);
	}

	@Test
	public void testPutInt() {
		long addr = allocator.allocate(10);
		int v = 1234;
		int size = allocator.putInt(addr, v);
		assertEquals(size, allocator.sizeOf(v));
		int r = allocator.getInt(addr);
		assertEquals(v, r);
		allocator.deallocate(addr);
	}

	@Test
	public void testPutLong() {
		long addr = allocator.allocate(10);
		long v = 1234;
		int size = allocator.putLong(addr, v);
		assertEquals(size, allocator.sizeOf(v));
		long r = allocator.getLong(addr);
		assertEquals(v, r);
		allocator.deallocate(addr);
	}

	@Test
	public void testPutByte() {
		long addr = allocator.allocate(10);
		byte v = 12;
		int size = allocator.putByte(addr, v);
		assertEquals(size, allocator.sizeOf(v));
		byte r = allocator.getByte(addr);
		assertEquals(v, r);
		allocator.deallocate(addr);
	}

	@Test
	public void testPutBoolean() {
		long addr = allocator.allocate(10);
		boolean v = true;
		int size = allocator.putBoolean(addr, v);
		assertEquals(size, allocator.sizeOf(v));
		boolean r = allocator.getBoolean(addr);
		assertEquals(v, r);
		
		v = false;
		size = allocator.putBoolean(addr, v);
		assertEquals(size, allocator.sizeOf(v));
		r = allocator.getBoolean(addr);
		assertEquals(v, r);
		allocator.deallocate(addr);
	}

	@Test
	public void testPutChar() {
		long addr = allocator.allocate(10);
		char v = 'w';
		int size = allocator.putChar(addr, v);
		assertEquals(size, allocator.sizeOf(v));
		char r = allocator.getChar(addr);
		assertEquals(v, r);
		allocator.deallocate(addr);
	}

	@Test
	public void testPutShort() {
		long addr = allocator.allocate(10);
		short v = 12345;
		int size = allocator.putShort(addr, v);
		assertEquals(size, allocator.sizeOf(v));
		short r = allocator.getShort(addr);
		assertEquals(v, r);
		allocator.deallocate(addr);
	}

	@Test
	public void testPutFloat() {
		long addr = allocator.allocate(10);
		float v = 1.0f;
		int size = allocator.putFloat(addr, v);
		assertEquals(size, allocator.sizeOf(v));
		float r = allocator.getFloat(addr);
		assertTrue(v == r);
		allocator.deallocate(addr);
	}

	@Test
	public void testPutDouble() {
		long addr = allocator.allocate(10);
		double v = 1.234;
		int size = allocator.putDouble(addr, v);
		assertEquals(size, allocator.sizeOf(v));
		double r = allocator.getDouble(addr);
		assertTrue(v == r);
		allocator.deallocate(addr);
	}
}
