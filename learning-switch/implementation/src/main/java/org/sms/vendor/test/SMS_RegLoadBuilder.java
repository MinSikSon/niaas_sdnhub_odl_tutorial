package org.sms.vendor.test;

import java.math.BigInteger;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opendaylight.yangtools.concepts.Builder;
import org.opendaylight.yangtools.yang.binding.Augmentation;
import org.opendaylight.yangtools.yang.binding.AugmentationHolder;
import org.opendaylight.yangtools.yang.binding.DataObject;

import com.google.common.collect.Range;

/**
 * Class that builds {@link SMS_RegLoad} instances.
 *
 * @see SMS_RegLoad
 *
 */

public class SMS_RegLoadBuilder implements Builder <SMS_RegLoad> {
	
	private SMS_Dst _sms_Dst;
	private BigInteger _value;
	
	Map<java.lang.Class<? extends Augmentation<SMS_RegLoad>>, Augmentation<SMS_RegLoad>> augmentation = Collections.emptyMap();

	public SMS_RegLoadBuilder() {
	}

	public SMS_RegLoadBuilder(SMS_RegLoad base) {
		this._sms_Dst = base.getSMS_Dst();
		this._value = base.getValue();
		if (base instanceof SMS_RegLoadImpl) {
			SMS_RegLoadImpl impl = (SMS_RegLoadImpl) base;
			if (!impl.augmentation.isEmpty()) {
				this.augmentation = new HashMap<>(impl.augmentation);
			}
		} else if (base instanceof AugmentationHolder) {
			@SuppressWarnings("unchecked")
			AugmentationHolder<SMS_RegLoad> casted = (AugmentationHolder<SMS_RegLoad>) base;
			if (!casted.augmentations().isEmpty()) {
				this.augmentation = new HashMap<>(casted.augmentations());
			}
		}
	}
	
	public SMS_Dst getSMS_Dst() {
		return _sms_Dst;
	}
	    
	public BigInteger getValue() {
		return _value;
	}
	    
	@SuppressWarnings("unchecked")
	public <E extends Augmentation<SMS_RegLoad>> E getAugmentation(java.lang.Class<E> augmentationType) {
		if (augmentationType == null) {
			throw new IllegalArgumentException("Augmentation Type reference cannot be NULL!");
		}
		return (E) augmentation.get(augmentationType);
	}

	public SMS_RegLoadBuilder setSMS_Dst(SMS_Dst value) {
		this._sms_Dst = value;
		return this;
	}
	    
	private static final com.google.common.collect.Range<java.math.BigInteger>[] CHECKVALUERANGE_RANGES;
	static {
		@SuppressWarnings("unchecked")
		final com.google.common.collect.Range<java.math.BigInteger>[] a = (com.google.common.collect.Range<java.math.BigInteger>[]) java.lang.reflect.Array.newInstance(com.google.common.collect.Range.class, 1);
		a[0] = com.google.common.collect.Range.closed(java.math.BigInteger.ZERO, new java.math.BigInteger("18446744073709551615"));
		CHECKVALUERANGE_RANGES = a;
	}
	private static void checkValueRange(final java.math.BigInteger value) {
		for (com.google.common.collect.Range<java.math.BigInteger> r : CHECKVALUERANGE_RANGES) {
			if (r.contains(value)) {
				return;
	        }
		}
		throw new IllegalArgumentException(String.format("Invalid range: %s, expected: %s.", value, java.util.Arrays.asList(CHECKVALUERANGE_RANGES)));
	}
	    
	public SMS_RegLoadBuilder setValue(BigInteger value) {
		if (value != null) {
			checkValueRange(value);
		}
		this._value = value;
		return this;
	}
	/**
     * @deprecated This method is slated for removal in a future release. See BUG-1485 for details.
     */
	
	@Deprecated
    public static List<Range<BigInteger>> _value_range() {
        final List<Range<BigInteger>> ret = new java.util.ArrayList<>(1);
        ret.add(Range.closed(BigInteger.ZERO, new BigInteger("18446744073709551615")));
        return ret;
    }
    
	public SMS_RegLoadBuilder addAugmentation(java.lang.Class<? extends Augmentation<SMS_RegLoad>> augmentationType, Augmentation<SMS_RegLoad> augmentation) {
		if (augmentation == null) {
			return removeAugmentation(augmentationType);
		}

		if (!(this.augmentation instanceof HashMap)) {
			this.augmentation = new HashMap<>();
		}

		this.augmentation.put(augmentationType, augmentation);
		return this;
	}
    
	public SMS_RegLoadBuilder removeAugmentation(java.lang.Class<? extends Augmentation<SMS_RegLoad>> augmentationType) {
		if (this.augmentation instanceof HashMap) {
			this.augmentation.remove(augmentationType);
		}
		return this;
	}

	public SMS_RegLoad build() {
		return new SMS_RegLoadImpl(this);
	}

	private static final class SMS_RegLoadImpl implements SMS_RegLoad {

		public java.lang.Class<SMS_RegLoad> getImplementedInterface() {
			return SMS_RegLoad.class;
		}

		private final SMS_Dst _sms_Dst;
		private final BigInteger _value;

		private Map<java.lang.Class<? extends Augmentation<SMS_RegLoad>>, Augmentation<SMS_RegLoad>> augmentation = Collections.emptyMap();

		private SMS_RegLoadImpl(SMS_RegLoadBuilder base) {
			this._sms_Dst = base.getSMS_Dst();
			this._value = base.getValue();
			switch (base.augmentation.size()) {
			case 0:
				this.augmentation = Collections.emptyMap();
				break;
			case 1:
				final Map.Entry<java.lang.Class<? extends Augmentation<SMS_RegLoad>>, Augmentation<SMS_RegLoad>> e = base.augmentation.entrySet().iterator().next();
				this.augmentation = Collections.<java.lang.Class<? extends Augmentation<SMS_RegLoad>>, Augmentation<SMS_RegLoad>>singletonMap(e.getKey(), e.getValue());
				break;
			default :
				this.augmentation = new HashMap<>(base.augmentation);
			}
		}

		@Override
		public SMS_Dst getSMS_Dst() {
			return _sms_Dst;
		}

		@Override
		public BigInteger getValue() {
			return _value;
		}

		@SuppressWarnings("unchecked")
		@Override
		public <E extends Augmentation<SMS_RegLoad>> E getAugmentation(java.lang.Class<E> augmentationType) {
			if (augmentationType == null) {
				throw new IllegalArgumentException("Augmentation Type reference cannot be NULL!");
			}
			return (E) augmentation.get(augmentationType);
		}

		private int hash = 0;
		private volatile boolean hashValid = false;

		@Override
		public int hashCode() {
			if (hashValid) {
				return hash;
			}
        
			final int prime = 31;
			int result = 1;
			result = prime * result + ((_sms_Dst == null) ? 0 : _sms_Dst.hashCode());
			result = prime * result + ((_value == null) ? 0 : _value.hashCode());
			result = prime * result + ((augmentation == null) ? 0 : augmentation.hashCode());

			hash = result;
			hashValid = true;
			return result;
		}

		@Override
		public boolean equals(java.lang.Object obj) {
			if (this == obj) {
				return true;
			}
			if (!(obj instanceof DataObject)) {
				return false;
			}
			if (!SMS_RegLoad.class.equals(((DataObject) obj).getImplementedInterface())) {
				return false;
			}
			SMS_RegLoad other = (SMS_RegLoad) obj;
			if (_sms_Dst == null) {
				if (other.getSMS_Dst() != null) {
					return false;
				}
			} else if (!_sms_Dst.equals(other.getSMS_Dst())) {
				return false;
			}
			if (_value == null) {
				if (other.getValue() != null) {
					return false;
				}
			} else if (!_value.equals(other.getValue())) {
				return false;
			}
			if (getClass() == obj.getClass()) {
				// Simple case: we are comparing against self
				SMS_RegLoadImpl otherImpl = (SMS_RegLoadImpl) obj;
				if (augmentation == null) {
					if (otherImpl.augmentation != null) {
						return false;
					}
				} else if (!augmentation.equals(otherImpl.augmentation)) {
					return false;
				}
			} else {
				// Hard case: compare our augments with presence there...
				for (Map.Entry<java.lang.Class<? extends Augmentation<SMS_RegLoad>>, Augmentation<SMS_RegLoad>> e : augmentation
						.entrySet()) {
					if (!e.getValue().equals(other.getAugmentation(e.getKey()))) {
						return false;
					}
				}
				// .. and give the other one the chance to do the same
				if (!obj.equals(this)) {
					return false;
				}
			}
			return true;
		}

		@Override
		public java.lang.String toString() {
			java.lang.StringBuilder builder = new java.lang.StringBuilder("SMS_RegLoad [");
			boolean first = true;

			if (_sms_Dst != null) {
				if (first) {
					first = false;
				} else {
					builder.append(", ");
				}
				builder.append("_sms_Dst=");
				builder.append(_sms_Dst);
			}
			if (_value != null) {
				if (first) {
					first = false;
				} else {
					builder.append(", ");
				}
				builder.append("_value=");
				builder.append(_value);
			}
			if (first) {
				first = false;
			} else {
				builder.append(", ");
			}
			builder.append("augmentation=");
			builder.append(augmentation.values());
			return builder.append(']').toString();
		}
	}

}
