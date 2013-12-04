package org.yong3.offheap.serialization.serializer;

import org.yong3.offheap.serialization.OffheapObject;
import org.yong3.offheap.serialization.OffheapUtils;

import com.google.common.base.Objects;

import sun.misc.Unsafe;

@SuppressWarnings("restriction")
public class OffheapTrade implements OffheapObject {
	private static long offset = 0;

	private static final long tradeIdOffset = offset += 0;
	private static final long clientIdOffset = offset += 8;
	private static final long venueCodeOffset = offset += 8;
	private static final long instrumentCodeOffset = offset += 4;
	private static final long priceOffset = offset += 4;
	private static final long quantityOffset = offset += 8;
	private static final long sideOffset = offset += 8;

	public static final long objectSize = offset += 2;

	private long objectOffset;

	static Unsafe unsafe = OffheapUtils.unsafe;

	@Override
	public long getOffheapAddress() {
		return objectOffset;
	}

	@Override
	public void setOffheapAddress(long address) {
		this.objectOffset = address;
	}

	public long getTradeId() {
		return unsafe.getLong(objectOffset + tradeIdOffset);
	}

	public void setTradeId(final long tradeId) {
		unsafe.putLong(objectOffset + tradeIdOffset, tradeId);
	}

	public long getClientId() {
		//System.err.println("base addr:" + objectOffset);
		return unsafe.getLong(objectOffset + clientIdOffset);
	}

	public void setClientId(final long clientId) {
		unsafe.putLong(objectOffset + clientIdOffset, clientId);
	}

	public int getVenueCode() {
		return unsafe.getInt(objectOffset + venueCodeOffset);
	}

	public void setVenueCode(final int venueCode) {
		unsafe.putInt(objectOffset + venueCodeOffset, venueCode);
	}

	public int getInstrumentCode() {
		return unsafe.getInt(objectOffset + instrumentCodeOffset);
	}

	public void setInstrumentCode(final int instrumentCode) {
		unsafe.putInt(objectOffset + instrumentCodeOffset, instrumentCode);
	}

	public long getPrice() {
		return unsafe.getLong(objectOffset + priceOffset);
	}

	public void setPrice(final long price) {
		unsafe.putLong(objectOffset + priceOffset, price);
	}

	public long getQuantity() {
		return unsafe.getLong(objectOffset + quantityOffset);
	}

	public void setQuantity(final long quantity) {
		unsafe.putLong(objectOffset + quantityOffset, quantity);
	}

	public char getSide() {
		return unsafe.getChar(objectOffset + sideOffset);
	}

	public void setSide(final char side) {
		unsafe.putChar(objectOffset + sideOffset, side);
	}

	public String toString() {
		return Objects.toStringHelper(this).add("clientId", this.getClientId())
				.add("instrumentCode", this.getInstrumentCode())
				.add("price", this.getPrice()).add("quantity", this.getQuantity()).add("side", this.getSide()).add("tradeId", this.getTradeId())
				.add("venueCode", this.getVenueCode()).toString();
	}

	public boolean equals(Object o) {
		OffheapTrade other = (OffheapTrade) o;
		return o != null
				&& Objects.equal(this.objectOffset, other.objectOffset)
				&& Objects.equal(this.getClientId(), other.getClientId())
				&& Objects.equal(this.getInstrumentCode(),
						other.getInstrumentCode())
				&& Objects.equal(this.getPrice(), other.getPrice())
				&& Objects.equal(this.getQuantity(), other.getQuantity())
				&& Objects.equal(this.getSide(), other.getSide())
				&& Objects.equal(this.getTradeId(), other.getTradeId())
				&& Objects.equal(this.getVenueCode(), other.getVenueCode());
	}
}
