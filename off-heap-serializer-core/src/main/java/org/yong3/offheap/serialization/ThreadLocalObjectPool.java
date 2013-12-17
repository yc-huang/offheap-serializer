package org.yong3.offheap.serialization;

import java.lang.reflect.Array;

import sun.misc.Unsafe;

public class ThreadLocalObjectPool<T> {

	static Unsafe unsafe = OffheapUtils.unsafe;
	ThreadLocal<Pool<T>> pool;
	private int size;

	public ThreadLocalObjectPool(final Class<T> cls, final int size)
			throws InstantiationException, IllegalAccessException {
		this.size = size;
		pool = new ThreadLocal<Pool<T>>() {
			@Override
			protected Pool<T> initialValue() {
				return new Pool<T>(cls, size);
			}

		};
	}

	public T borrow() {
		return pool.get().borrow();
	}

	public boolean returnBack(T instance) {
		return pool.get().returnBack(instance);
	}

	public int size() {
		return this.size;
	}

	@SuppressWarnings({ "unchecked", "hiding" })
	class Pool<T> {
		T[] instances;
		int idx;

		Pool(Class<T> cls, int size) {
			instances = (T[]) Array.newInstance(cls, size);
			for (int i = 0; i < size; i++) {
				try {
					instances[i] = (T) unsafe.allocateInstance(cls);
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}// cls.newInstance();
			}
		}

		public T borrow() {
			if (idx < size) {
				return instances[idx++];
			} else
				return null;
		}

		public boolean returnBack(T instance) {
			if (idx > 0) {
				instances[--idx] = instance;
				return true;
			} else {
				return false;
			}
		}

	}
}
