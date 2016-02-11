package org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.bgp;
import org.opendaylight.yangtools.yang.binding.Identifier;


public class NeighborsKey
 implements Identifier<Neighbors> {
    private static final long serialVersionUID = -3827002069658736140L;
    private final java.lang.Long _asNumber;


    public NeighborsKey(java.lang.Long _asNumber) {
    
    
        this._asNumber = _asNumber;
    }
    
    /**
     * Creates a copy from Source Object.
     *
     * @param source Source object
     */
    public NeighborsKey(NeighborsKey source) {
        this._asNumber = source._asNumber;
    }


    public java.lang.Long getAsNumber() {
        return _asNumber;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((_asNumber == null) ? 0 : _asNumber.hashCode());
        return result;
    }

    @Override
    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        NeighborsKey other = (NeighborsKey) obj;
        if (_asNumber == null) {
            if (other._asNumber != null) {
                return false;
            }
        } else if(!_asNumber.equals(other._asNumber)) {
            return false;
        }
        return true;
    }

    @Override
    public java.lang.String toString() {
        java.lang.StringBuilder builder = new java.lang.StringBuilder(org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.bgp.NeighborsKey.class.getSimpleName()).append(" [");
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
        return builder.append(']').toString();
    }



}

