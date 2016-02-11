package org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.acl.rev150722;
import org.opendaylight.yangtools.yang.binding.Augmentation;
import org.opendaylight.yangtools.yang.binding.AugmentationHolder;
import org.opendaylight.yangtools.yang.binding.DataObject;
import java.util.HashMap;
import org.opendaylight.yangtools.concepts.Builder;
import java.util.List;
import org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.acl.rev150722.acl.spec.Acl;
import java.util.Collections;
import java.util.Map;


/**
 * Class that builds {@link org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.acl.rev150722.AclSpec} instances.
 *
 * @see org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.acl.rev150722.AclSpec
 *
 */
public class AclSpecBuilder implements Builder <org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.acl.rev150722.AclSpec> {

    private List<Acl> _acl;

    Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.acl.rev150722.AclSpec>>, Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.acl.rev150722.AclSpec>> augmentation = Collections.emptyMap();

    public AclSpecBuilder() {
    }

    public AclSpecBuilder(AclSpec base) {
        this._acl = base.getAcl();
        if (base instanceof AclSpecImpl) {
            AclSpecImpl impl = (AclSpecImpl) base;
            if (!impl.augmentation.isEmpty()) {
                this.augmentation = new HashMap<>(impl.augmentation);
            }
        } else if (base instanceof AugmentationHolder) {
            @SuppressWarnings("unchecked")
            AugmentationHolder<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.acl.rev150722.AclSpec> casted =(AugmentationHolder<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.acl.rev150722.AclSpec>) base;
            if (!casted.augmentations().isEmpty()) {
                this.augmentation = new HashMap<>(casted.augmentations());
            }
        }
    }


    public List<Acl> getAcl() {
        return _acl;
    }
    
    @SuppressWarnings("unchecked")
    public <E extends Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.acl.rev150722.AclSpec>> E getAugmentation(java.lang.Class<E> augmentationType) {
        if (augmentationType == null) {
            throw new IllegalArgumentException("Augmentation Type reference cannot be NULL!");
        }
        return (E) augmentation.get(augmentationType);
    }

    public AclSpecBuilder setAcl(List<Acl> value) {
        this._acl = value;
        return this;
    }
    
    public AclSpecBuilder addAugmentation(java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.acl.rev150722.AclSpec>> augmentationType, Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.acl.rev150722.AclSpec> augmentation) {
        if (augmentation == null) {
            return removeAugmentation(augmentationType);
        }
    
        if (!(this.augmentation instanceof HashMap)) {
            this.augmentation = new HashMap<>();
        }
    
        this.augmentation.put(augmentationType, augmentation);
        return this;
    }
    
    public AclSpecBuilder removeAugmentation(java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.acl.rev150722.AclSpec>> augmentationType) {
        if (this.augmentation instanceof HashMap) {
            this.augmentation.remove(augmentationType);
        }
        return this;
    }

    public AclSpec build() {
        return new AclSpecImpl(this);
    }

    private static final class AclSpecImpl implements AclSpec {

        public java.lang.Class<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.acl.rev150722.AclSpec> getImplementedInterface() {
            return org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.acl.rev150722.AclSpec.class;
        }

        private final List<Acl> _acl;

        private Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.acl.rev150722.AclSpec>>, Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.acl.rev150722.AclSpec>> augmentation = Collections.emptyMap();

        private AclSpecImpl(AclSpecBuilder base) {
            this._acl = base.getAcl();
            switch (base.augmentation.size()) {
            case 0:
                this.augmentation = Collections.emptyMap();
                break;
            case 1:
                final Map.Entry<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.acl.rev150722.AclSpec>>, Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.acl.rev150722.AclSpec>> e = base.augmentation.entrySet().iterator().next();
                this.augmentation = Collections.<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.acl.rev150722.AclSpec>>, Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.acl.rev150722.AclSpec>>singletonMap(e.getKey(), e.getValue());
                break;
            default :
                this.augmentation = new HashMap<>(base.augmentation);
            }
        }

        @Override
        public List<Acl> getAcl() {
            return _acl;
        }
        
        @SuppressWarnings("unchecked")
        @Override
        public <E extends Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.acl.rev150722.AclSpec>> E getAugmentation(java.lang.Class<E> augmentationType) {
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
            result = prime * result + ((_acl == null) ? 0 : _acl.hashCode());
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
            if (!org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.acl.rev150722.AclSpec.class.equals(((DataObject)obj).getImplementedInterface())) {
                return false;
            }
            org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.acl.rev150722.AclSpec other = (org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.acl.rev150722.AclSpec)obj;
            if (_acl == null) {
                if (other.getAcl() != null) {
                    return false;
                }
            } else if(!_acl.equals(other.getAcl())) {
                return false;
            }
            if (getClass() == obj.getClass()) {
                // Simple case: we are comparing against self
                AclSpecImpl otherImpl = (AclSpecImpl) obj;
                if (augmentation == null) {
                    if (otherImpl.augmentation != null) {
                        return false;
                    }
                } else if(!augmentation.equals(otherImpl.augmentation)) {
                    return false;
                }
            } else {
                // Hard case: compare our augments with presence there...
                for (Map.Entry<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.acl.rev150722.AclSpec>>, Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.acl.rev150722.AclSpec>> e : augmentation.entrySet()) {
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
            java.lang.StringBuilder builder = new java.lang.StringBuilder ("AclSpec [");
            boolean first = true;
        
            if (_acl != null) {
                if (first) {
                    first = false;
                } else {
                    builder.append(", ");
                }
                builder.append("_acl=");
                builder.append(_acl);
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
