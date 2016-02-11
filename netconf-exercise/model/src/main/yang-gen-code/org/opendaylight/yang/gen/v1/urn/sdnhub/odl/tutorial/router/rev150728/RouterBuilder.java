package org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728;
import org.opendaylight.yangtools.yang.binding.Augmentation;
import org.opendaylight.yangtools.yang.binding.AugmentationHolder;
import org.opendaylight.yangtools.yang.binding.DataObject;
import java.util.HashMap;
import org.opendaylight.yangtools.concepts.Builder;
import java.util.List;
import org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.Bgp;
import java.util.Collections;
import java.util.Map;
import org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.Ospf;


/**
 * Class that builds {@link org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.Router} instances.
 *
 * @see org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.Router
 *
 */
public class RouterBuilder implements Builder <org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.Router> {

    private List<Bgp> _bgp;
    private List<Ospf> _ospf;

    Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.Router>>, Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.Router>> augmentation = Collections.emptyMap();

    public RouterBuilder() {
    }

    public RouterBuilder(Router base) {
        this._bgp = base.getBgp();
        this._ospf = base.getOspf();
        if (base instanceof RouterImpl) {
            RouterImpl impl = (RouterImpl) base;
            if (!impl.augmentation.isEmpty()) {
                this.augmentation = new HashMap<>(impl.augmentation);
            }
        } else if (base instanceof AugmentationHolder) {
            @SuppressWarnings("unchecked")
            AugmentationHolder<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.Router> casted =(AugmentationHolder<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.Router>) base;
            if (!casted.augmentations().isEmpty()) {
                this.augmentation = new HashMap<>(casted.augmentations());
            }
        }
    }


    public List<Bgp> getBgp() {
        return _bgp;
    }
    
    public List<Ospf> getOspf() {
        return _ospf;
    }
    
    @SuppressWarnings("unchecked")
    public <E extends Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.Router>> E getAugmentation(java.lang.Class<E> augmentationType) {
        if (augmentationType == null) {
            throw new IllegalArgumentException("Augmentation Type reference cannot be NULL!");
        }
        return (E) augmentation.get(augmentationType);
    }

    public RouterBuilder setBgp(List<Bgp> value) {
        this._bgp = value;
        return this;
    }
    
    public RouterBuilder setOspf(List<Ospf> value) {
        this._ospf = value;
        return this;
    }
    
    public RouterBuilder addAugmentation(java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.Router>> augmentationType, Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.Router> augmentation) {
        if (augmentation == null) {
            return removeAugmentation(augmentationType);
        }
    
        if (!(this.augmentation instanceof HashMap)) {
            this.augmentation = new HashMap<>();
        }
    
        this.augmentation.put(augmentationType, augmentation);
        return this;
    }
    
    public RouterBuilder removeAugmentation(java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.Router>> augmentationType) {
        if (this.augmentation instanceof HashMap) {
            this.augmentation.remove(augmentationType);
        }
        return this;
    }

    public Router build() {
        return new RouterImpl(this);
    }

    private static final class RouterImpl implements Router {

        public java.lang.Class<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.Router> getImplementedInterface() {
            return org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.Router.class;
        }

        private final List<Bgp> _bgp;
        private final List<Ospf> _ospf;

        private Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.Router>>, Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.Router>> augmentation = Collections.emptyMap();

        private RouterImpl(RouterBuilder base) {
            this._bgp = base.getBgp();
            this._ospf = base.getOspf();
            switch (base.augmentation.size()) {
            case 0:
                this.augmentation = Collections.emptyMap();
                break;
            case 1:
                final Map.Entry<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.Router>>, Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.Router>> e = base.augmentation.entrySet().iterator().next();
                this.augmentation = Collections.<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.Router>>, Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.Router>>singletonMap(e.getKey(), e.getValue());
                break;
            default :
                this.augmentation = new HashMap<>(base.augmentation);
            }
        }

        @Override
        public List<Bgp> getBgp() {
            return _bgp;
        }
        
        @Override
        public List<Ospf> getOspf() {
            return _ospf;
        }
        
        @SuppressWarnings("unchecked")
        @Override
        public <E extends Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.Router>> E getAugmentation(java.lang.Class<E> augmentationType) {
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
            result = prime * result + ((_bgp == null) ? 0 : _bgp.hashCode());
            result = prime * result + ((_ospf == null) ? 0 : _ospf.hashCode());
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
            if (!org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.Router.class.equals(((DataObject)obj).getImplementedInterface())) {
                return false;
            }
            org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.Router other = (org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.Router)obj;
            if (_bgp == null) {
                if (other.getBgp() != null) {
                    return false;
                }
            } else if(!_bgp.equals(other.getBgp())) {
                return false;
            }
            if (_ospf == null) {
                if (other.getOspf() != null) {
                    return false;
                }
            } else if(!_ospf.equals(other.getOspf())) {
                return false;
            }
            if (getClass() == obj.getClass()) {
                // Simple case: we are comparing against self
                RouterImpl otherImpl = (RouterImpl) obj;
                if (augmentation == null) {
                    if (otherImpl.augmentation != null) {
                        return false;
                    }
                } else if(!augmentation.equals(otherImpl.augmentation)) {
                    return false;
                }
            } else {
                // Hard case: compare our augments with presence there...
                for (Map.Entry<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.Router>>, Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.Router>> e : augmentation.entrySet()) {
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
            java.lang.StringBuilder builder = new java.lang.StringBuilder ("Router [");
            boolean first = true;
        
            if (_bgp != null) {
                if (first) {
                    first = false;
                } else {
                    builder.append(", ");
                }
                builder.append("_bgp=");
                builder.append(_bgp);
             }
            if (_ospf != null) {
                if (first) {
                    first = false;
                } else {
                    builder.append(", ");
                }
                builder.append("_ospf=");
                builder.append(_ospf);
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
