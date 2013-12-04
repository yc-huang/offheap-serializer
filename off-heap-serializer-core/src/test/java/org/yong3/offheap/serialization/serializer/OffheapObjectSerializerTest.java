package org.yong3.offheap.serialization.serializer;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.yong3.offheap.serialization.MemoryAllocatorFactory;
import org.yong3.offheap.serialization.Serializer;

public class OffheapObjectSerializerTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testWriteAndRead() {
		OffheapTrade trade = new OffheapTrade();
		long size = trade.objectSize;
		long addr = MemoryAllocatorFactory.get().allocate(size);
		
		trade.setOffheapAddress(addr);
		trade.setClientId(12345678);
		trade.setInstrumentCode(1);
		trade.setPrice(45678);
		trade.setQuantity(120000);
		trade.setSide('s');
		trade.setTradeId(10000009);
		trade.setVenueCode(300);
		System.err.println(trade.toString());
		
		Serializer<OffheapTrade> serializer = new OffheapObjectSerializer();
		
		size = serializer.getOffheapSize(trade);
		addr = MemoryAllocatorFactory.get().allocate(size);
		
		int wSize = serializer.write(addr, trade);
		assertEquals(size, wSize);
		
		OffheapTrade r = serializer.read(addr, OffheapTrade.class);
		System.err.println(r.toString());
		assertTrue(trade.equals(r));
	}

}
