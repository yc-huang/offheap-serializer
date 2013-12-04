package org.yong3.offheap.serialization;

import org.yong3.offheap.serialization.allocator.SimpleMemoryAllocator;

public class MemoryAllocatorFactory {
	static MemoryAllocator allocator;
	
	public static MemoryAllocator get(){
		if(allocator == null){
			allocator = new SimpleMemoryAllocator();
		}
		return allocator;
	}
	
	public static void set(MemoryAllocator allocator){
		MemoryAllocatorFactory.allocator = allocator;
	}
}
