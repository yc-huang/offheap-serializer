package org.yong3.offheap.serialization;

import static org.junit.Assert.*;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ThreadLocalObjectPoolTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws InstantiationException, IllegalAccessException, InterruptedException {
		final int size = 8;
		final ThreadLocalObjectPool<VO> pool = new ThreadLocalObjectPool<VO>(
				VO.class, size);
		
		VO vo1 = pool.borrow();
		VO vo2 = pool.borrow();
		assertFalse(vo1 == vo2);
		pool.returnBack(vo1);
		pool.returnBack(vo2);
		
		
		int threads = 2;

		final Semaphore available = new Semaphore(threads, true);
		
		//warm up
		test(pool, 1 * 1000 * 1000);
		
		for (int i = 0; i < threads; i++) {
			Thread t = new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						available.acquire(1);
						test(pool, 10 * 1000 * 1000);
						available.release(1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			});
			t.setDaemon(false);
			t.start();
		}
		Thread.sleep(1000);
		available.acquire(threads);
		System.gc();
	}

	
	private void test(ThreadLocalObjectPool<VO> pool, int num){
		ThreadLocalRandom random = ThreadLocalRandom.current();
		long start = System.nanoTime();
		int count = 0;
		for (int i = 0; i < num; i++) {
			int it = random.nextInt(pool.size());
			for(int j = 0; j < it; j++){
				//getFromPool(pool);
				getByNew();
				count ++;
			}
		}
		long total = System.nanoTime() - start;
	
		System.err.println("total:" + total/1000/1000 + "ms for " + count + " ops, cost per get:" + (total)/count);
		
	}
	
	private void getFromPool(ThreadLocalObjectPool<VO> pool){
		VO vo = pool.borrow();
		assertNotNull(vo);
		//vo.id = "" + System.nanoTime();
		//Thread.yield();
		boolean ret = pool.returnBack(vo);
		assertTrue(ret);
	}
	
	private void getByNew(){
		VO vo = new VO();
		//vo.id = "" + System.nanoTime();
		assertNotNull(vo);
		vo = null;
	}

	
	class VO {
		String id = "id1234id1234id1234id1234id1234id1234id1234id1234id1234id1234id1234id1234id1234id1234id1234id1234id1234id1234", name = "bob";
		int age = 21, salary = 1000;
		//byte[] desc = new byte[65];
	}

}
