package org.yong3.offheap.serialization;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.yong3.offheap.serialization.serializer.ByteArraySerializer;
import org.yong3.offheap.serialization.serializer.StringSerializer;

public class SerializerFactoryTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		Serializer<byte[]> byteArraySerializer = SerializerFactory.get(byte[].class);
		assertEquals(ByteArraySerializer.class, byteArraySerializer.getClass());
		
		Serializer<String> stringSerializer = SerializerFactory.get(String.class);
		assertEquals(StringSerializer.class, stringSerializer.getClass());
	}

}
