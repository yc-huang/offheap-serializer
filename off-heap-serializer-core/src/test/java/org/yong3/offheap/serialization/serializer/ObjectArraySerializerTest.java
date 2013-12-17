package org.yong3.offheap.serialization.serializer;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.yong3.offheap.serialization.MemoryAllocatorFactory;
import org.yong3.offheap.serialization.Serializer;
import org.yong3.offheap.serialization.SerializerFactory;

import com.google.common.base.Objects;

public class ObjectArraySerializerTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}


	@Test
	public void testInts() throws InstantiationException {
		System.err.println("in test int array");
		int[] ints = new int[] { 1, 2, 3 };
		int[] rInts = (int[]) writeAndRead(ints);
		assertArrayEquals(ints, rInts);
		
		for(int i = 0; i < 1000000; i++){
			rInts = (int[]) writeAndRead(ints);
			assertArrayEquals(ints, rInts);
		}
	}

	@Test
	public void testChars() throws InstantiationException {
		System.err.println("in test char array");
		char[] chars = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', '*', '~', '汗', '魍'};
		char[] rChars = (char[]) writeAndRead(chars);
		assertArrayEquals(chars, rChars);

		for (int i = 0; i < 1000000; i++) {
			rChars = (char[]) writeAndRead(chars);
			assertArrayEquals(chars, rChars);
		}
	}
	
	@Test
	public void testBytes() throws InstantiationException {
		byte[] in = new byte[] { 1, 2, 3 };
		byte[] out = (byte[]) writeAndRead(in);
		assertArrayEquals(in, out);
		for (int i = 0; i < 1000000; i++) {
			out = (byte[]) writeAndRead(in);
			assertArrayEquals(in, out);
		}
	}

	@Test
	public void testShorts() throws InstantiationException {
		short[] in = new short[] { 1, 2, 3 };
		short[] out = (short[]) writeAndRead(in);
		assertArrayEquals(in, out);
		for (int i = 0; i < 1000000; i++) {
			out = (short[]) writeAndRead(in);
			assertArrayEquals(in, out);
		}
	}

	@Test
	public void testLongs() throws InstantiationException {
		long[] in = new long[] { 1, 2, 3 };
		long[] out = (long[]) writeAndRead(in);
		assertArrayEquals(in, out);
		for (int i = 0; i < 1000000; i++) {
			out = (long[]) writeAndRead(in);
			assertArrayEquals(in, out);
		}
	}

	@Test
	public void testFloats() throws InstantiationException {
		float[] in = new float[] { 1, 2, 3 };
		float[] out = (float[]) writeAndRead(in);
		assertArrayEquals(in, out, 0);
		for (int i = 0; i < 1000000; i++) {
			out = (float[]) writeAndRead(in);
			assertArrayEquals(in, out, 0);
		}
	}

	@Test
	public void testBooleans() throws InstantiationException {
		boolean[] in = new boolean[] { true, false };
		boolean[] out = (boolean[]) writeAndRead(in);
		assertTrue(out[0]);
		assertFalse(out[1]);
		for (int i = 0; i < 1000000; i++) {
			out = (boolean[]) writeAndRead(in);
			assertTrue(out[0]);
			assertFalse(out[1]);
		}
	}

	@Test
	public void testDoubles() throws InstantiationException {
		double[] in = new double[] { 1, 2, 3 };
		double[] out = (double[]) writeAndRead(in);
		assertArrayEquals(in, out, 0);
		for (int i = 0; i < 1000000; i++) {
			out = (double[]) writeAndRead(in);
			assertArrayEquals(in, out, 0);
		}
	}

	@Test
	public void testObjects() throws InstantiationException {
		VO[] in = new VO[] { new VO("1", "bob", 20, 100.0f),
				new VO("2", "alice", 24, 200.0f) };
		VO[] out = (VO[]) writeAndRead(in);
		assertArrayEquals(in, out);

		for (int i = 0; i < 100000; i++) {
			out = (VO[]) writeAndRead(in);
			assertArrayEquals(in, out);
		}
	}
	
	@Test
	public void testIntegers() throws InstantiationException {
		Integer[] in = new Integer[] { new Integer(1), new Integer(2), new Integer(3)};
		Integer[] out = (Integer[]) writeAndRead(in);
		assertArrayEquals(in, out);

		for (int i = 0; i < 100000; i++) {
			out = (Integer[]) writeAndRead(in);
			assertArrayEquals(in, out);
		}
	}

	private Object writeAndRead(Object o) {
		// only support array
		assertTrue(o.getClass().isArray());
		//System.err.println("class:" + o.getClass());
		Serializer serializer = SerializerFactory.get(o.getClass());
		//System.err.println("get a serializer:" + serializer.getClass());
		int size = serializer.getOffheapSize(o);
		// System.err.println("allocate mem:" + size);
		long addr = MemoryAllocatorFactory.get().allocate(size);

		int wSize = serializer.write(addr, o);
		assertEquals(size, wSize);

		Object r = serializer.read(addr, o.getClass());

		MemoryAllocatorFactory.get().deallocate(addr);
		return r;

	}

	class VO {
		String id, name;
		int age;
		float salary;

		VO(String id, String name, int age, float salary) {
			this.id = id;
			this.name = name;
			this.age = age;
			this.salary = salary;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj != null && obj instanceof VO) {
				VO other = (VO) obj;
				// TODO: null check
				return other.age == age && other.salary == salary
						&& other.id.equals(id) && other.name.equals(name);
			} else {
				return false;
			}
		}

		@Override
		public String toString() {
//			return Objects.toStringHelper("").add("id", this.id)
//					.add("name", this.name).add("age", this.age)
//					.add("salary", this.salary).toString();
			
			return id + "," + name + "," + age + "," + salary;
		}
	}
}
