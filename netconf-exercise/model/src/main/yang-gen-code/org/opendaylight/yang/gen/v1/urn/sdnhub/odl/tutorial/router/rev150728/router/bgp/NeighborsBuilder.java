package org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.bgp;
import org.opendaylight.yangtools.yang.binding.Augmentation;
import org.opendaylight.yangtools.yang.binding.AugmentationHolder;
import org.opendaylight.yangtools.yang.binding.DataObject;
import java.util.HashMap;
import org.opendaylight.yangtools.concepts.Builder;
import java.math.BigInteger;
import java.util.List;
import java.util.Collections;
import com.google.common.collect.Range;
import java.util.Map;


/**
 * Class that builds {@link org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.bgp.Neighbors} instances.
 *
 * @see org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.bgp.Neighbors
 *
 */
public class NeighborsBuilder implements Builder <org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.bgp.Neighbors> {

    private java.lang.Long _asNumber;
    private NeighborsKey _key;
    private java.lang.String _peerIp;

    Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.bgp.Neighbors>>, Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.bgp.Neighbors>> augmentation = Collections.emptyMap();

    public NeighborsBuilder() {
    }

    public NeighborsBuilder(Neighbors base) {
        if (base.getKey() == null) {
            this._key = new NeighborsKey(
                base.getAsNumber()
            );
            this._asNumber = base.getAsNumber();
        } else {
            this._key = base.getKey();
            this._asNumber = _key.getAsNumber();
        }
        this._peerIp = base.getPeerIp();
        if (base instanceof NeighborsImpl) {
            NeighborsImpl impl = (NeighborsImpl) base;
            if (!impl.augmentation.isEmpty()) {
                this.augmentation = new HashMap<>(impl.augmentation);
            }
        } else if (base instanceof AugmentationHolder) {
            @SuppressWarnings("unchecked")
            AugmentationHolder<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.bgp.Neighbors> casted =(AugmentationHolder<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.bgp.Neighbors>) base;
            if (!casted.augmentations().isEmpty()) {
                this.augmentation = new HashMap<>(casted.augmentations());
            }
        }
    }


    public java.lang.Long getAsNumber() {
        return _asNumber;
    }
    
    public NeighborsKey getKey() {
        return _key;
    }
    
    public java.lang.String getPeerIp() {
        return _peerIp;
    }
    
    @SuppressWarnings("unchecked")
    public <E extends Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.bgp.Neighbors>> E getAugmentation(java.lang.Class<E> augmentationType) {
        if (augmentationType == null) {
            throw new IllegalArgumentException("Augmentation Type reference cannot be NULL!");
        }
        return (E) augmentation.get(augmentationType);
    }

    private static void checkAsNumberRange(final long value) {
        if (value >= 0L && value <= 4294967295L) {
            return;
        }
        throw new IllegalArgumentException(String.format("Invalid range: %s, expected: [[0‥4294967295]].", value));
    }
    
    public NeighborsBuilder setAsNumber(java.lang.Long value) {
        if (value != null) {
            checkAsNumberRange(value);
        }
        this._asNumber = value;
        return this;
    }
    /**
     * @deprecated This method is slated for removal in a future release. See BUG-1485 for details.
     */
    @Deprecated
    public static List<Range<BigInteger>> _asNumber_range() {
        final List<Range<BigInteger>> ret = new java.util.ArrayList<>(1);
        ret.add(Range.closed(BigInteger.ZERO, BigInteger.valueOf(4294967295L)));
        return ret;
    }
    
    public NeighborsBuilder setKey(NeighborsKey value) {
        this._key = value;
        return this;
    }
    
    public NeighborsBuilder setPeerIp(java.lang.String value) {
        this._peerIp = value;
        return this;
    }
    
    public NeighborsBuilder addAugmentation(java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.bgp.Neighbors>> augmentationType, Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.bgp.Neighbors> augmentation) {
        if (augmentation == null) {
            return removeAugmentation(augmentationType);
        }
    
        if (!(this.augmentation instanceof HashMap)) {
            this.augmentation = new HashMap<>();
        }
    
        this.augmentation.put(augmentationType, augmentation);
        return this;
    }
    
    public NeighborsBuilder removeAugmentation(java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.bgp.Neighbors>> augmentationType) {
        if (this.augmentation instanceof HashMap) {
            this.augmentation.remove(augmentationType);
        }
        return this;
    }

    public Neighbors build() {
        return new NeighborsImpl(this);
    }

    private static final class NeighborsImpl implements Neighbors {

        public java.lang.Class<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.bgp.Neighbors> getImplementedInterface() {
            return org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.bgp.Neighbors.class;
        }

        private final java.lang.Long _asNumber;
        private final NeighborsKey _key;
        private final java.lang.String _peerIp;

        private Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.bgp.Neighbors>>, Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.bgp.Neighbors>> augmentation = Collections.emptyMap();

        private NeighborsImpl(NeighborsBuilder base) {
            if (base.getKey() == null) {
                this._key = new NeighborsKey(
                    base.getAsNumber()
                );
                this._asNumber = base.getAsNumber();
            } else {
                this._key = base.getKey();
                this._asNumber = _key.getAsNumber();
            }
            this._peerIp = base.getPeerIp();
            switch (base.augmentation.size()) {
            case 0:
                this.augmentation = Collections.emptyMap();
                break;
            case 1:
                final Map.Entry<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.bgp.Neighbors>>, Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.bgp.Neighbors>> e = base.augmentation.entrySet().iterator().next();
                this.augmentation = Collections.<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.bgp.Neighbors>>, Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.bgp.Neighbors>>singletonMap(e.getKey(), e.getValue());
                break;
            default :
                this.augmentation = new HashMap<>(base.augmentation);
            }
        }

        @Override
        public java.lang.Long getAsNumber() {
            return _asNumber;
        }
        
        @Override
        public NeighborsKey getKey() {
            return _key;
        }
        
        @Override
        public java.lang.String getPeerIp() {
            return _peerIp;
        }
        
        @SuppressWarnings("unchecked")
        @Override
        public <E extends Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.bgp.Neighbors>> E getAugmentation(java.lang.Class<E> augmentationType) {
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
            result = prime * result + ((_asNumber == null) ? 0 : _asNumber.hashCode());
            result = prime * result + ((_key == null) ? 0 : _key.hashCode());
            result = prime * result + ((_peerIp == null) ? 0 : _peerIp.hashCode());
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
            if (!org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.bgp.Neighbors.class.equals(((DataObject)obj).getImplementedInterface())) {
                return false;
            }
            org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.bgp.Neighbors other = (org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.bgp.Neighbors)obj;
            if (_asNumber == null) {
                if (other.getAsNumber() != null) {
                    return false;
                }
            } else if(!_asNumber.equals(other.getAsNumber())) {
                return false;
            }
            if (_key == null) {
                if (other.getKey() != null) {
                    return false;
                }
            } else if(!_key.equals(other.getKey())) {
                return false;
            }
            if (_peerIp == null) {
                if (other.getPeerIp() != null) {
                    return false;
                }
            } else if(!_peerIp.equals(other.getPeerIp())) {
                return false;
            }
            if (getClass() == obj.getClass()) {
                // Simple case: we are comparing against self
                NeighborsImpl otherImpl = (NeighborsImpl) obj;
                if (augmentation == null) {
                    if (otherImpl.augmentation != null) {
                        return false;
                    }
                } else if(!augmentation.equals(otherImpl.augmentation)) {
                    return false;
                }
            } else {
                // Hard case: compare our augments with presence there...
                for (Map.Entry<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.bgp.Neighbors>>, Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.bgp.Neighbors>> e : augmentation.entrySet()) {
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
            java.lang.StringBuilder builder = new java.lang.StringBuilder ("Neighbors [");
            boolean first = true;
        
            if (_asNumber != null) {
                if (first) {
                    first = false;
                } else {
                    builder.append(", ");
                }
                builder.append("_asNumber=");
                builder.append(_asNumber);
             }
            if (_key != null) {
                if (first) {
                    first = false;
                } else {
                    builder.append(", ");
                }
                builder.append("_key=");
                builder.append(_key);
             }
            if (_peerIp != null) {
                if (first) {
                    first = false;
                } else {
                    builder.append(", ");
                }
                builder.append("_peerIp=");
                builder.append(_peerIp);
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
