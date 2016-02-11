package org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.acl.rev150722.acl.spec;
import org.opendaylight.yangtools.yang.binding.Identifier;


public class AclKey
 implements Identifier<Acl> {
    private static final long serialVersionUID = 7028772418066354855L;
    private final java.lang.String _destination;


    public AclKey(java.lang.String _destination) {
    
    
        this._destination = _destination;
    }
    
    /**
     * Creates a copy from Source Object.
     *
     * @param source Source object
     */
    public AclKey(AclKey source) {
        this._destination = source._destination;
    }


    public java.lang.String getDestination() {
        return _destination;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((_destination == null) ? 0 : _destination.hashCode());
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
        AclKey other = (AclKey) obj;
        if (_destination == null) {
            if (other._destination != null) {
                return false;
            }
        } else if(!_destination.equals(other._destination)) {
            return false;
        }
        return true;
    }

    @Override
    public java.lang.String toString() {
        java.lang.StringBuilder builder = new java.lang.StringBuilder(org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.acl.rev150722.acl.spec.AclKey.class.getSimpleName()).append(" [");
        boolean first = true;
    
        if (_destination != null) {
            if (first) {
                first = false;
            } else {
                builder.append(", ");
            }
            builder.append("_destination=");
            builder.append(_destination);
         }
        return builder.append(']').toString();
    }



}

