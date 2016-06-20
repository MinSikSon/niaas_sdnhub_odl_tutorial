package org.sms.vendor.test;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.opendaylight.yangtools.concepts.Builder;
import org.opendaylight.yangtools.yang.binding.Augmentation;
import org.opendaylight.yangtools.yang.binding.AugmentationHolder;
import org.opendaylight.yangtools.yang.binding.DataObject;

/**
 * Class that builds {@link SMS_ActionRegLoadNodesNodeTableFlowApplyActionsCase} instances.
 *
 * @see SMS_ActionRegLoadNodesNodeTableFlowApplyActionsCase
 *
 */
public class SMS_ActionRegLoadNodesNodeTableFlowApplyActionsCaseBuilder implements Builder <SMS_ActionRegLoadNodesNodeTableFlowApplyActionsCase> {

	private SMS_RegLoad _smsRegLoad;

	Map<java.lang.Class<? extends Augmentation<SMS_ActionRegLoadNodesNodeTableFlowApplyActionsCase>>, Augmentation<SMS_ActionRegLoadNodesNodeTableFlowApplyActionsCase>> augmentation = Collections.emptyMap();

    public SMS_ActionRegLoadNodesNodeTableFlowApplyActionsCaseBuilder() {
    }

	public SMS_ActionRegLoadNodesNodeTableFlowApplyActionsCaseBuilder(SMS_ActionRegLoadGrouping arg) {
		this._smsRegLoad = arg.getSMS_RegLoad();
	}

	public SMS_ActionRegLoadNodesNodeTableFlowApplyActionsCaseBuilder(SMS_ActionRegLoadNodesNodeTableFlowApplyActionsCase base) {
		this._smsRegLoad = base.getSMS_RegLoad();
		if (base instanceof SMS_ActionRegLoadNodesNodeTableFlowApplyActionsCaseImpl) {
			SMS_ActionRegLoadNodesNodeTableFlowApplyActionsCaseImpl impl = (SMS_ActionRegLoadNodesNodeTableFlowApplyActionsCaseImpl) base;
			if (!impl.augmentation.isEmpty()) {
				this.augmentation = new HashMap<>(impl.augmentation);
			}
		} else if (base instanceof AugmentationHolder) {
			@SuppressWarnings("unchecked")
			AugmentationHolder<SMS_ActionRegLoadNodesNodeTableFlowApplyActionsCase> casted =(AugmentationHolder<SMS_ActionRegLoadNodesNodeTableFlowApplyActionsCase>) base;
			if (!casted.augmentations().isEmpty()) {
				this.augmentation = new HashMap<>(casted.augmentations());
			}
		}
	}

    /**
     *Set fields from given grouping argument. Valid argument is instance of one of following types:
     * <ul>
     * <li>SMS_ActionRegLoadGrouping</li>
     * </ul>
     *
     * @param arg grouping object
     * @throws IllegalArgumentException if given argument is none of valid types
    */
	public void fieldsFrom(DataObject arg) {
		boolean isValidArg = false;
		if (arg instanceof SMS_ActionRegLoadGrouping) {
			this._smsRegLoad = ((SMS_ActionRegLoadGrouping) arg).getSMS_RegLoad();
			isValidArg = true;
		}
		if (!isValidArg) {
			throw new IllegalArgumentException(
				"expected one of: [SMS_ActionRegLoadGrouping] \n" +
				"but was: " + arg
			);
		}
	}

	public SMS_RegLoad getSMS_RegLoad() {
		return _smsRegLoad;
	}
    
    @SuppressWarnings("unchecked")
    public <E extends Augmentation<SMS_ActionRegLoadNodesNodeTableFlowApplyActionsCase>> E getAugmentation(java.lang.Class<E> augmentationType) {
		if (augmentationType == null) {
			throw new IllegalArgumentException("Augmentation Type reference cannot be NULL!");
		}
		return (E) augmentation.get(augmentationType);
	}

	public SMS_ActionRegLoadNodesNodeTableFlowApplyActionsCaseBuilder setSMS_RegLoad(SMS_RegLoad value) {
		this._smsRegLoad = value;
		return this;
	}
    
    public SMS_ActionRegLoadNodesNodeTableFlowApplyActionsCaseBuilder addAugmentation(java.lang.Class<? extends Augmentation<SMS_ActionRegLoadNodesNodeTableFlowApplyActionsCase>> augmentationType, Augmentation<SMS_ActionRegLoadNodesNodeTableFlowApplyActionsCase> augmentation) {
        if (augmentation == null) {
            return removeAugmentation(augmentationType);
        }
    
        if (!(this.augmentation instanceof HashMap)) {
            this.augmentation = new HashMap<>();
        }
    
        this.augmentation.put(augmentationType, augmentation);
        return this;
    }
    
	public SMS_ActionRegLoadNodesNodeTableFlowApplyActionsCaseBuilder removeAugmentation(java.lang.Class<? extends Augmentation<SMS_ActionRegLoadNodesNodeTableFlowApplyActionsCase>> augmentationType) {
		if (this.augmentation instanceof HashMap) {
			this.augmentation.remove(augmentationType);
		}
		return this;
	}

	public SMS_ActionRegLoadNodesNodeTableFlowApplyActionsCase build() {
		return new SMS_ActionRegLoadNodesNodeTableFlowApplyActionsCaseImpl(this);
	}

    private static final class SMS_ActionRegLoadNodesNodeTableFlowApplyActionsCaseImpl implements SMS_ActionRegLoadNodesNodeTableFlowApplyActionsCase {

		public java.lang.Class<SMS_ActionRegLoadNodesNodeTableFlowApplyActionsCase> getImplementedInterface() {
			return SMS_ActionRegLoadNodesNodeTableFlowApplyActionsCase.class;
		}

		private final SMS_RegLoad _smsRegLoad;

		private Map<java.lang.Class<? extends Augmentation<SMS_ActionRegLoadNodesNodeTableFlowApplyActionsCase>>, Augmentation<SMS_ActionRegLoadNodesNodeTableFlowApplyActionsCase>> augmentation = Collections.emptyMap();

		private SMS_ActionRegLoadNodesNodeTableFlowApplyActionsCaseImpl(SMS_ActionRegLoadNodesNodeTableFlowApplyActionsCaseBuilder base) {
            this._smsRegLoad = base.getSMS_RegLoad();
            switch (base.augmentation.size()) {
            case 0:
                this.augmentation = Collections.emptyMap();
                break;
            case 1:
                final Map.Entry<java.lang.Class<? extends Augmentation<SMS_ActionRegLoadNodesNodeTableFlowApplyActionsCase>>, Augmentation<SMS_ActionRegLoadNodesNodeTableFlowApplyActionsCase>> e = base.augmentation.entrySet().iterator().next();
                this.augmentation = Collections.<java.lang.Class<? extends Augmentation<SMS_ActionRegLoadNodesNodeTableFlowApplyActionsCase>>, Augmentation<SMS_ActionRegLoadNodesNodeTableFlowApplyActionsCase>>singletonMap(e.getKey(), e.getValue());
                break;
            default :
                this.augmentation = new HashMap<>(base.augmentation);
            }
        }
		
//		@Override
		public SMS_RegLoad getSMS_RegLoad() {
			return _smsRegLoad;
		}

		@SuppressWarnings("unchecked")
//		@Override
		public <E extends Augmentation<SMS_ActionRegLoadNodesNodeTableFlowApplyActionsCase>> E getAugmentation(java.lang.Class<E> augmentationType) {
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
			result = prime * result + ((_smsRegLoad == null) ? 0 : _smsRegLoad.hashCode());
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
			if (!SMS_ActionRegLoadNodesNodeTableFlowApplyActionsCase.class.equals(((DataObject)obj).getImplementedInterface())) {
                return false;
            }
			SMS_ActionRegLoadNodesNodeTableFlowApplyActionsCase other = (SMS_ActionRegLoadNodesNodeTableFlowApplyActionsCase)obj;
			if (_smsRegLoad == null) {
				if (other.getSMS_RegLoad() != null) {
					return false;
				}
			} else if (!_smsRegLoad.equals(other.getSMS_RegLoad())) {
				return false;
			}
			if (getClass() == obj.getClass()) {
				// Simple case: we are comparing against self
				SMS_ActionRegLoadNodesNodeTableFlowApplyActionsCaseImpl otherImpl = (SMS_ActionRegLoadNodesNodeTableFlowApplyActionsCaseImpl) obj;
				if (augmentation == null) {
					if (otherImpl.augmentation != null) {
						return false;
					}
				} else if (!augmentation.equals(otherImpl.augmentation)) {
					return false;
				}
			} else {
				// Hard case: compare our augments with presence there...
				for (Map.Entry<java.lang.Class<? extends Augmentation<SMS_ActionRegLoadNodesNodeTableFlowApplyActionsCase>>, Augmentation<SMS_ActionRegLoadNodesNodeTableFlowApplyActionsCase>> e : augmentation.entrySet()) {
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
			java.lang.StringBuilder builder = new java.lang.StringBuilder("SMS_ActionRegLoadNodesNodeTableFlowApplyActionsCase [");
			boolean first = true;

			if (_smsRegLoad != null) {
				if (first) {
					first = false;
				} else {
					builder.append(", ");
				}
				builder.append("_smsRegLoad=");
				builder.append(_smsRegLoad);
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
