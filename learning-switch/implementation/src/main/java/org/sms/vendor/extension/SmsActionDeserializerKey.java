package org.sms.vendor.extension;

public final class SmsActionDeserializerKey {

	private final short version;
	private final int subtype;

	/**
	 * @param version protocol wire version
	 * @param subtype nx_action_subtype
	*/
	public SmsActionDeserializerKey(short version, int subtype) {
		if (!isValueUint16(subtype)) {
			throw new IllegalArgumentException(
				"Sms subtype is uint16. A value of subtype has to be between 0 and 65535 include.");
		}
		this.version = version;
		this.subtype = subtype;
	}

	public short getVersion() {
		return version;
	}

	public int getSubtype() {
		return subtype;
	}

	private static final boolean isValueUint16(int value) {
		if (value >= 0 && value <= 65535L)
			return true;
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + subtype;
		result = prime * result + version;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SmsActionDeserializerKey other = (SmsActionDeserializerKey) obj;
		if (subtype != other.subtype)
			return false;
		if (version != other.version)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "NiciraActionDeserializerKey [version=" + version + ", subtype=" + subtype + "]";
	}

}
