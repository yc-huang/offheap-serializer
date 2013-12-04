package org.yong3.offheap.serialization;

public interface OffheapObject {
	public long getOffheapAddress();
	public void setOffheapAddress(final long address);
}
