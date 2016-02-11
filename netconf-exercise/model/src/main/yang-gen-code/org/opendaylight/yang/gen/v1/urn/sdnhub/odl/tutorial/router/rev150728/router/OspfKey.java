package org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router;
import org.opendaylight.yangtools.yang.binding.Identifier;


public class OspfKey
 implements Identifier<Ospf> {
    private static final long serialVersionUID = -875432294923788419L;
    private final java.lang.Long _processId;


    public OspfKey(java.lang.Long _processId) {
    
    
        this._processId = _processId;
    }
    
    /**
     * Creates a copy from Source Object.
     *
     * @param source Source object
     */
    public OspfKey(OspfKey source) {
        this._processId = source._processId;
    }


    public java.lang.Long getProcessId() {
        return _processId;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((_processId == null) ? 0 : _processId.hashCode());
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
        OspfKey other = (OspfKey) obj;
        if (_processId == null) {
            if (other._processId != null) {
                return false;
            }
        } else if(!_processId.equals(other._processId)) {
            return false;
        }
        return true;
    }

    @Override
    public java.lang.String toString() {
        java.lang.StringBuilder builder = new java.lang.StringBuilder(org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.router.rev150728.router.OspfKey.class.getSimpleName()).append(" [");
        boolean first = true;
    
        if (_processId != null) {
            if (first) {
                first = false;
            } else {
                builder.append(", ");
            }
            builder.append("_processId=");
            builder.append(_processId);
         }
        return builder.append(']').toString();
    }



}

