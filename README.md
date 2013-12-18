offheap-serializer
==================

A simple library which can serialize any POJO to be stored at offheap memory.

##Sample Usage:

#serialize a POJO to off-heap:
    Serializer<String> serializer = SerializerFactory.get(String.class);
    MemoryAllocator allocator = MemoryAllocatorFactory.get();
    
    String s = "123456abcdef@#$%^&";
    
    //calc the required off-heap memory size
    int size = serializer.getOffheapSize(s);
    //allocate off-heap memory
    long addr = allocator.allocate(size);
    //write object instance to off-heap memory
    serializer.write(addr, s);
    
    //read back from off-heap memory
    String r = serializer.read(addr, String.class);
    
    //free allocated off-heap memory
    allocator.deallocate(addr);
    
    
    


    

