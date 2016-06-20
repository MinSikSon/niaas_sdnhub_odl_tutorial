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

public class SMS_DstBuilder implements Builder <SMS_Dst>{

	private SMS_DstChoice _sms_DstChoice;
	private java.lang.Integer _end;
	private java.lang.Integer _start;
	
	Map<java.lang.Class<? extends Augmentation<SMS_Dst>>, Augmentation<SMS_Dst>> augmentation = Collections.emptyMap();
	
	public SMS_DstBuilder() {
    }
    public SMS_DstBuilder(SMS_DstChoiceGrouping arg) {
        this._sms_DstChoice = arg.getSMS_DstChoice();
    }
    public SMS_DstBuilder(SMS_RangeGrouping arg) {
        this._start = arg.getStart();
        this._end = arg.getEnd();
    }

    public SMS_DstBuilder(SMS_Dst base) {
        this._sms_DstChoice = base.getSMS_DstChoice();
        this._end = base.getEnd();
        this._start = base.getStart();
        if (base instanceof SMS_DstImpl) {
        	SMS_DstImpl impl = (SMS_DstImpl) base;
            if (!impl.augmentation.isEmpty()) {
                this.augmentation = new HashMap<>(impl.augmentation);
            }
        } else if (base instanceof AugmentationHolder) {
            @SuppressWarnings("unchecked")
            AugmentationHolder<SMS_Dst> casted =(AugmentationHolder<SMS_Dst>) base;
            if (!casted.augmentations().isEmpty()) {
                this.augmentation = new HashMap<>(casted.augmentations());
            }
        }
    }
	
    /**
     *Set fields from given grouping argument. Valid argument is instance of one of following types:
     * <ul>
     * <li>SMS_DstChoiceGrouping</li>
     * <li>SMS_RangeGrouping</li>
     * </ul>
     *
     * @param arg grouping object
     * @throws IllegalArgumentException if given argument is none of valid types
    */
    
	public void fieldsFrom(DataObject arg) {
		boolean isValidArg = false;
		if (arg instanceof SMS_DstChoiceGrouping) {
			this._sms_DstChoice = ((SMS_DstChoiceGrouping) arg).getSMS_DstChoice();
			isValidArg = true;
		}
		if (arg instanceof SMS_RangeGrouping) {
			this._start = ((SMS_RangeGrouping) arg).getStart();
			this._end = ((SMS_RangeGrouping) arg).getEnd();
			isValidArg = true;
		}
		if (!isValidArg) {
			throw new IllegalArgumentException(
				"expected one of: [SMS_DstChoiceGrouping, SMS_RangeGrouping] \n" + 
				"but was: " + arg);
		}
	}
	
	public SMS_DstChoice getSMS_DstChoice() {
		return _sms_DstChoice;
	}

	public java.lang.Integer getEnd() {
		return _end;
	}

	public java.lang.Integer getStart() {
		return _start;
	}
	
	@SuppressWarnings("unchecked")
    public <E extends Augmentation<SMS_Dst>> E getAugmentation(java.lang.Class<E> augmentationType) {
        if (augmentationType == null) {
            throw new IllegalArgumentException("Augmentation Type reference cannot be NULL!");
        }
        return (E) augmentation.get(augmentationType);
    }

    public SMS_DstBuilder setSMS_DstChoice(SMS_DstChoice value) {
        this._sms_DstChoice = value;
        return this;
    }
    
    private static void checkEndRange(final int value) {
        if (value >= 0 && value <= 65535) {
            return;
        }
        throw new IllegalArgumentException(String.format("Invalid range: %s, expected: [[0‥65535]].", value));
    }
    
    public SMS_DstBuilder setEnd(java.lang.Integer value) {
        if (value != null) {
            checkEndRange(value);
        }
        this._end = value;
        return this;
    }
    /**
     * @deprecated This method is slated for removal in a future release. See BUG-1485 for details.
     */
    @Deprecated
    public static List<Range<BigInteger>> _end_range() {
        final List<Range<BigInteger>> ret = new java.util.ArrayList<>(1);
        ret.add(Range.closed(BigInteger.ZERO, BigInteger.valueOf(65535L)));
        return ret;
    }
    
    private static void checkStartRange(final int value) {
        if (value >= 0 && value <= 65535) {
            return;
        }
        throw new IllegalArgumentException(String.format("Invalid range: %s, expected: [[0‥65535]].", value));
    }
    
    public SMS_DstBuilder setStart(java.lang.Integer value) {
        if (value != null) {
            checkStartRange(value);
        }
        this._start = value;
        return this;
    }
    /**
     * @deprecated This method is slated for removal in a future release. See BUG-1485 for details.
     */
	
    @Deprecated
    public static List<Range<BigInteger>> _start_range() {
        final List<Range<BigInteger>> ret = new java.util.ArrayList<>(1);
        ret.add(Range.closed(BigInteger.ZERO, BigInteger.valueOf(65535L)));
        return ret;
    }
    
    public SMS_DstBuilder addAugmentation(java.lang.Class<? extends Augmentation<SMS_Dst>> augmentationType, Augmentation<SMS_Dst> augmentation) {
        if (augmentation == null) {
            return removeAugmentation(augmentationType);
        }
    
        if (!(this.augmentation instanceof HashMap)) {
            this.augmentation = new HashMap<>();
        }
    
        this.augmentation.put(augmentationType, augmentation);
        return this;
    }
    
    public SMS_DstBuilder removeAugmentation(java.lang.Class<? extends Augmentation<SMS_Dst>> augmentationType) {
        if (this.augmentation instanceof HashMap) {
            this.augmentation.remove(augmentationType);
        }
        return this;
    }
	
	@Override
	public SMS_Dst build() {
		// TODO Auto-generated method stub
		return new SMS_DstImpl(this);
	}
	
	private static final class SMS_DstImpl implements SMS_Dst {

		public java.lang.Class<SMS_Dst> getImplementedInterface() {
			return SMS_Dst.class;
		}

		private final SMS_DstChoice _sms_DstChoice;
		private final java.lang.Integer _end;
		private final java.lang.Integer _start;

		private Map<java.lang.Class<? extends Augmentation<SMS_Dst>>, Augmentation<SMS_Dst>> augmentation = Collections.emptyMap();

		private SMS_DstImpl(SMS_DstBuilder base) {
			this._sms_DstChoice = base.getSMS_DstChoice();
			this._end = base.getEnd();
			this._start = base.getStart();
			switch (base.augmentation.size()) {
			case 0:
				this.augmentation = Collections.emptyMap();
				break;
			case 1:
				final Map.Entry<java.lang.Class<? extends Augmentation<SMS_Dst>>, Augmentation<SMS_Dst>> e = base.augmentation.entrySet().iterator().next();
				this.augmentation = Collections.<java.lang.Class<? extends Augmentation<SMS_Dst>>, Augmentation<SMS_Dst>> singletonMap(e.getKey(), e.getValue());
				break;
			default:
				this.augmentation = new HashMap<>(base.augmentation);
			}
		}

		@Override
		public SMS_DstChoice getSMS_DstChoice() {
			return _sms_DstChoice;
		}
        
		@Override
		public java.lang.Integer getEnd() {
			return _end;
		}

		@Override
		public java.lang.Integer getStart() {
			return _start;
		}
        
		@SuppressWarnings("unchecked")
		@Override
		public <E extends Augmentation<SMS_Dst>> E getAugmentation(java.lang.Class<E> augmentationType) {
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
			result = prime * result + ((_sms_DstChoice == null) ? 0 : _sms_DstChoice.hashCode());
			result = prime * result + ((_end == null) ? 0 : _end.hashCode());
			result = prime * result + ((_start == null) ? 0 : _start.hashCode());
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
			if (!SMS_Dst.class.equals(((DataObject) obj).getImplementedInterface())) {
				return false;
			}
			SMS_Dst other = (SMS_Dst) obj;
			if (_sms_DstChoice == null) {
				if (other.getSMS_DstChoice() != null) {
					return false;
				}
			} else if (!_sms_DstChoice.equals(other.getSMS_DstChoice())) {
				return false;
			}
			if (_end == null) {
				if (other.getEnd() != null) {
					return false;
				}
			} else if (!_end.equals(other.getEnd())) {
				return false;
			}
			if (_start == null) {
				if (other.getStart() != null) {
					return false;
				}
			} else if (!_start.equals(other.getStart())) {
				return false;
			}
			if (getClass() == obj.getClass()) {
				// Simple case: we are comparing against self
				SMS_DstImpl otherImpl = (SMS_DstImpl) obj;
				if (augmentation == null) {
					if (otherImpl.augmentation != null) {
						return false;
					}
				} else if (!augmentation.equals(otherImpl.augmentation)) {
					return false;
				}
			} else {
				// Hard case: compare our augments with presence there...
				for (Map.Entry<java.lang.Class<? extends Augmentation<SMS_Dst>>, Augmentation<SMS_Dst>> e : augmentation.entrySet()) {
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
			java.lang.StringBuilder builder = new java.lang.StringBuilder("Dst [");
			boolean first = true;

			if (_sms_DstChoice != null) {
				if (first) {
					first = false;
				} else {
					builder.append(", ");
				}
				builder.append("_sms_DstChoice=");
				builder.append(_sms_DstChoice);
			}
			if (_end != null) {
				if (first) {
					first = false;
				} else {
					builder.append(", ");
				}
				builder.append("_end=");
				builder.append(_end);
			}
			if (_start != null) {
				if (first) {
					first = false;
				} else {
					builder.append(", ");
				}
				builder.append("_start=");
				builder.append(_start);
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












