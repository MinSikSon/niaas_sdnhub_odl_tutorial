package org.sms.vendor.test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.opendaylight.yangtools.concepts.Builder;
import org.opendaylight.yangtools.yang.binding.Augmentation;
import org.opendaylight.yangtools.yang.binding.DataContainer;
import org.opendaylight.yangtools.yang.binding.DataObject;

/**
 * Class that builds
 * {@link SMS_RegCase}
 * instances.
 *
 * @see SMS_RegCase
 *
 */
public class SMS_RegCaseBuilder implements Builder<SMS_RegCase> {

	private java.lang.Class<? extends SMS_Reg> _smsReg;

	Map<java.lang.Class<? extends Augmentation<SMS_RegCase>>, Augmentation<SMS_RegCase>> augmentation = Collections
			.emptyMap();

	public SMS_RegCaseBuilder() {
	}

	public SMS_RegCaseBuilder(SMS_RegGrouping arg) {
		this._smsReg = arg.getSMS_Reg();
	}

	public SMS_RegCaseBuilder(SMS_RegCase base) {
		 this._smsReg = base.getSMS_Reg();
	}

	/**
	 * Set fields from given grouping argument. Valid argument is instance of
	 * one of following types:
	 * <ul>
	 * <li>SMS_RegGrouping</li>
	 * </ul>
	 *
	 * @param arg
	 *            grouping object
	 * @throws IllegalArgumentException
	 *             if given argument is none of valid types
	 */
	public void fieldsFrom(DataObject arg) {
		boolean isValidArg = false;
		if (arg instanceof SMS_RegGrouping) {
			this._smsReg = ((SMS_RegGrouping) arg).getSMS_Reg();
			isValidArg = true;
		}
		if (!isValidArg) {
			throw new IllegalArgumentException("expected one of: [SMS_RegGrouping] \n" + "but was: " + arg);
		}
	}

	public java.lang.Class<? extends SMS_Reg> getSMS_Reg() {
		return _smsReg;
	}

	@SuppressWarnings("unchecked")
	public <E extends Augmentation<SMS_RegCase>> E getAugmentation(java.lang.Class<E> augmentationType) {
		if (augmentationType == null) {
			throw new IllegalArgumentException("Augmentation Type reference cannot be NULL!");
		}
		return (E) augmentation.get(augmentationType);
	}

	public SMS_RegCaseBuilder setSMS_Reg(java.lang.Class<? extends SMS_Reg> value) {
		this._smsReg = value;
		return this;
	}

	public SMS_RegCaseBuilder addAugmentation(java.lang.Class<? extends Augmentation<SMS_RegCase>> augmentationType,
			Augmentation<SMS_RegCase> augmentation) {
		if (augmentation == null) {
			return removeAugmentation(augmentationType);
		}

		if (!(this.augmentation instanceof HashMap)) {
			this.augmentation = new HashMap<>();
		}

		this.augmentation.put(augmentationType, augmentation);
		return this;
	}

	public SMS_RegCaseBuilder removeAugmentation(
			java.lang.Class<? extends Augmentation<SMS_RegCase>> augmentationType) {
		if (this.augmentation instanceof HashMap) {
			this.augmentation.remove(augmentationType);
		}
		return this;
	}

	@Override
	public SMS_RegCase build() {
		// TODO Auto-generated method stub
		return new SMS_RegCaseImpl(this);
	}

	private static final class SMS_RegCaseImpl implements SMS_RegCase {
		@Override
		public Class<? extends DataContainer> getImplementedInterface() {
			return SMS_RegCase.class;
		}

		private final java.lang.Class<? extends SMS_Reg> _smsReg;

		private Map<java.lang.Class<? extends Augmentation<SMS_RegCase>>, Augmentation<SMS_RegCase>> augmentation = Collections
				.emptyMap();

		private SMS_RegCaseImpl(SMS_RegCaseBuilder base) {
			this._smsReg = base.getSMS_Reg();
			switch (base.augmentation.size()) {
			case 0:
				this.augmentation = Collections.emptyMap();
				break;
			case 1:
				final Map.Entry<java.lang.Class<? extends Augmentation<SMS_RegCase>>, Augmentation<SMS_RegCase>> e = base.augmentation
						.entrySet().iterator().next();
				this.augmentation = Collections.<java.lang
						.Class<? extends Augmentation<SMS_RegCase>>, Augmentation<SMS_RegCase>> singletonMap(e.getKey(),
								e.getValue());
				break;
			default:
				this.augmentation = new HashMap<>(base.augmentation);
			}
		}

		@Override
		public Class<? extends SMS_Reg> getSMS_Reg() {
			return _smsReg;
		}

		@SuppressWarnings("unchecked")
		public <E extends Augmentation<SMS_RegCase>> E getAugmentation(Class<E> augmentationType) {
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
			result = prime * result + ((_smsReg == null) ? 0 : _smsReg.hashCode());
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
			if (!SMS_RegCase.class.equals(((DataObject) obj).getImplementedInterface())) {
				return false;
			}
			SMS_RegCase other = (SMS_RegCase) obj;
			if (_smsReg == null) {
				if (other.getSMS_Reg() != null) {
					return false;
				}
			} else if (!_smsReg.equals(other.getSMS_Reg())) {
				return false;
			}
			if (getClass() == obj.getClass()) {
				// Simple case: we are comparing against self
				SMS_RegCaseImpl otherImpl = (SMS_RegCaseImpl) obj;
				if (augmentation == null) {
					if (otherImpl.augmentation != null) {
						return false;
					}
				} else if (!augmentation.equals(otherImpl.augmentation)) {
					return false;
				}
			} else {
				// Hard case: compare our augments with presence there...
				for (Map.Entry<java.lang.Class<? extends Augmentation<SMS_RegCase>>, Augmentation<SMS_RegCase>> e : augmentation.entrySet()) {
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
            java.lang.StringBuilder builder = new java.lang.StringBuilder ("SMS_RegCase [");
            boolean first = true;
        
            if (_smsReg != null) {
                if (first) {
                    first = false;
                } else {
                    builder.append(", ");
                }
                builder.append("_smsReg=");
                builder.append(_smsReg);
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
