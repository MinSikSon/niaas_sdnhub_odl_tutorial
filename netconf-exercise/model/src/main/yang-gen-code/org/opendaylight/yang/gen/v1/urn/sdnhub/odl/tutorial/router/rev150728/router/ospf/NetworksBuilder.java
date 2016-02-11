package org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.ospf;
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
 * Class that builds {@link org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.ospf.Networks} instances.
 *
 * @see org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.ospf.Networks
 *
 */
public class NetworksBuilder implements Builder <org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.ospf.Networks> {

    private java.lang.Long _areaId;
    private NetworksKey _key;
    private java.lang.String _subnetIp;

    Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.ospf.Networks>>, Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.ospf.Networks>> augmentation = Collections.emptyMap();

    public NetworksBuilder() {
    }

    public NetworksBuilder(Networks base) {
        if (base.getKey() == null) {
            this._key = new NetworksKey(
                base.getSubnetIp()
            );
            this._subnetIp = base.getSubnetIp();
        } else {
            this._key = base.getKey();
            this._subnetIp = _key.getSubnetIp();
        }
        this._areaId = base.getAreaId();
        if (base instanceof NetworksImpl) {
            NetworksImpl impl = (NetworksImpl) base;
            if (!impl.augmentation.isEmpty()) {
                this.augmentation = new HashMap<>(impl.augmentation);
            }
        } else if (base instanceof AugmentationHolder) {
            @SuppressWarnings("unchecked")
            AugmentationHolder<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.ospf.Networks> casted =(AugmentationHolder<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.ospf.Networks>) base;
            if (!casted.augmentations().isEmpty()) {
                this.augmentation = new HashMap<>(casted.augmentations());
            }
        }
    }


    public java.lang.Long getAreaId() {
        return _areaId;
    }
    
    public NetworksKey getKey() {
        return _key;
    }
    
    public java.lang.String getSubnetIp() {
        return _subnetIp;
    }
    
    @SuppressWarnings("unchecked")
    public <E extends Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.ospf.Networks>> E getAugmentation(java.lang.Class<E> augmentationType) {
        if (augmentationType == null) {
            throw new IllegalArgumentException("Augmentation Type reference cannot be NULL!");
        }
        return (E) augmentation.get(augmentationType);
    }

    private static void checkAreaIdRange(final long value) {
        if (value >= 0L && value <= 4294967295L) {
            return;
        }
        throw new IllegalArgumentException(String.format("Invalid range: %s, expected: [[0‥4294967295]].", value));
    }
    
    public NetworksBuilder setAreaId(java.lang.Long value) {
        if (value != null) {
            checkAreaIdRange(value);
        }
        this._areaId = value;
        return this;
    }
    /**
     * @deprecated This method is slated for removal in a future release. See BUG-1485 for details.
     */
    @Deprecated
    public static List<Range<BigInteger>> _areaId_range() {
        final List<Range<BigInteger>> ret = new java.util.ArrayList<>(1);
        ret.add(Range.closed(BigInteger.ZERO, BigInteger.valueOf(4294967295L)));
        return ret;
    }
    
    public NetworksBuilder setKey(NetworksKey value) {
        this._key = value;
        return this;
    }
    
    public NetworksBuilder setSubnetIp(java.lang.String value) {
        this._subnetIp = value;
        return this;
    }
    
    public NetworksBuilder addAugmentation(java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.ospf.Networks>> augmentationType, Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.ospf.Networks> augmentation) {
        if (augmentation == null) {
            return removeAugmentation(augmentationType);
        }
    
        if (!(this.augmentation instanceof HashMap)) {
            this.augmentation = new HashMap<>();
        }
    
        this.augmentation.put(augmentationType, augmentation);
        return this;
    }
    
    public NetworksBuilder removeAugmentation(java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.ospf.Networks>> augmentationType) {
        if (this.augmentation instanceof HashMap) {
            this.augmentation.remove(augmentationType);
        }
        return this;
    }

    public Networks build() {
        return new NetworksImpl(this);
    }

    private static final class NetworksImpl implements Networks {

        public java.lang.Class<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.ospf.Networks> getImplementedInterface() {
            return org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.ospf.Networks.class;
        }

        private final java.lang.Long _areaId;
        private final NetworksKey _key;
        private final java.lang.String _subnetIp;

        private Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.ospf.Networks>>, Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.ospf.Networks>> augmentation = Collections.emptyMap();

        private NetworksImpl(NetworksBuilder base) {
            if (base.getKey() == null) {
                this._key = new NetworksKey(
                    base.getSubnetIp()
                );
                this._subnetIp = base.getSubnetIp();
            } else {
                this._key = base.getKey();
                this._subnetIp = _key.getSubnetIp();
            }
            this._areaId = base.getAreaId();
            switch (base.augmentation.size()) {
            case 0:
                this.augmentation = Collections.emptyMap();
                break;
            case 1:
                final Map.Entry<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.ospf.Networks>>, Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.ospf.Networks>> e = base.augmentation.entrySet().iterator().next();
                this.augmentation = Collections.<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.ospf.Networks>>, Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.ospf.Networks>>singletonMap(e.getKey(), e.getValue());
                break;
            default :
                this.augmentation = new HashMap<>(base.augmentation);
            }
        }

        @Override
        public java.lang.Long getAreaId() {
            return _areaId;
        }
        
        @Override
        public NetworksKey getKey() {
            return _key;
        }
        
        @Override
        public java.lang.String getSubnetIp() {
            return _subnetIp;
        }
        
        @SuppressWarnings("unchecked")
        @Override
        public <E extends Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.ospf.Networks>> E getAugmentation(java.lang.Class<E> augmentationType) {
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
            result = prime * result + ((_areaId == null) ? 0 : _areaId.hashCode());
            result = prime * result + ((_key == null) ? 0 : _key.hashCode());
            result = prime * result + ((_subnetIp == null) ? 0 : _subnetIp.hashCode());
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
            if (!org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.ospf.Networks.class.equals(((DataObject)obj).getImplementedInterface())) {
                return false;
            }
            org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.ospf.Networks other = (org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.ospf.Networks)obj;
            if (_areaId == null) {
                if (other.getAreaId() != null) {
                    return false;
                }
            } else if(!_areaId.equals(other.getAreaId())) {
                return false;
            }
            if (_key == null) {
                if (other.getKey() != null) {
                    return false;
                }
            } else if(!_key.equals(other.getKey())) {
                return false;
            }
            if (_subnetIp == null) {
                if (other.getSubnetIp() != null) {
                    return false;
                }
            } else if(!_subnetIp.equals(other.getSubnetIp())) {
                return false;
            }
            if (getClass() == obj.getClass()) {
                // Simple case: we are comparing against self
                NetworksImpl otherImpl = (NetworksImpl) obj;
                if (augmentation == null) {
                    if (otherImpl.augmentation != null) {
                        return false;
                    }
                } else if(!augmentation.equals(otherImpl.augmentation)) {
                    return false;
                }
            } else {
                // Hard case: compare our augments with presence there...
                for (Map.Entry<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.ospf.Networks>>, Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.ospf.Networks>> e : augmentation.entrySet()) {
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
            java.lang.StringBuilder builder = new java.lang.StringBuilder ("Networks [");
            boolean first = true;
        
            if (_areaId != null) {
                if (first) {
                    first = false;
                } else {
                    builder.append(", ");
                }
                builder.append("_areaId=");
                builder.append(_areaId);
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
            if (_subnetIp != null) {
                if (first) {
                    first = false;
                } else {
                    builder.append(", ");
                }
                builder.append("_subnetIp=");
                builder.append(_subnetIp);
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
