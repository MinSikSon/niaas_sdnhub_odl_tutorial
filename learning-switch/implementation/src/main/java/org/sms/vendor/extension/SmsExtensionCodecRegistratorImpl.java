package org.sms.vendor.extension;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.opendaylight.openflowjava.protocol.api.extensibility.OFDeserializer;
import org.opendaylight.openflowjava.protocol.api.extensibility.OFGeneralDeserializer;
import org.opendaylight.openflowjava.protocol.api.extensibility.OFGeneralSerializer;
import org.opendaylight.openflowjava.protocol.api.extensibility.OFSerializer;
import org.opendaylight.openflowjava.protocol.api.keys.ActionSerializerKey;
import org.opendaylight.openflowjava.protocol.api.keys.ExperimenterActionDeserializerKey;
import org.opendaylight.openflowjava.protocol.api.keys.ExperimenterDeserializerKey;
import org.opendaylight.openflowjava.protocol.api.keys.ExperimenterSerializerKey;
import org.opendaylight.openflowjava.protocol.api.keys.MatchEntryDeserializerKey;
import org.opendaylight.openflowjava.protocol.api.keys.MatchEntrySerializerKey;
import org.opendaylight.openflowjava.protocol.api.util.EncodeConstants;
import org.opendaylight.openflowjava.protocol.spi.connection.SwitchConnectionProvider;
//import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.list.Action;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.action.rev150203.actions.grouping.Action;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.MatchField;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.OxmClassBase;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.match.entries.grouping.MatchEntry;

public class SmsExtensionCodecRegistratorImpl implements SmsExtensionCodecRegistrator, AutoCloseable{

    private static final Map<SmsActionDeserializerKey, OFDeserializer<Action>> actionDeserializers = new ConcurrentHashMap<>();

    private final List<SwitchConnectionProvider> providers;

    /**
     * @param providers
     */
    public SmsExtensionCodecRegistratorImpl(List<SwitchConnectionProvider> providers) {
        this.providers = providers;
        ActionDeserializer of10ActionDeserializer = new ActionDeserializer(EncodeConstants.OF10_VERSION_ID);
        ActionDeserializer of13ActionDeserializer = new ActionDeserializer(EncodeConstants.OF13_VERSION_ID);
        registerActionDeserializer(ActionDeserializer.OF10_DESERIALIZER_KEY, of10ActionDeserializer);
        registerActionDeserializer(ActionDeserializer.OF13_DESERIALIZER_KEY, of13ActionDeserializer);
    }

    private void registerActionDeserializer(ExperimenterActionDeserializerKey key, OFGeneralDeserializer deserializer) {
        for (SwitchConnectionProvider provider : providers) {
            provider.registerActionDeserializer(key, deserializer);
        }
    }

    private void registerActionSerializer(ActionSerializerKey<?> key, OFGeneralSerializer serializer) {
        for (SwitchConnectionProvider provider : providers) {
            provider.registerActionSerializer(key, serializer);
        }
    }

    private void unregisterDeserializer(ExperimenterDeserializerKey key) {
        for (SwitchConnectionProvider provider : providers) {
            provider.unregisterDeserializer(key);
        }
    }

    private void unregisterSerializer(ExperimenterSerializerKey key) {
        for (SwitchConnectionProvider provider : providers) {
            provider.unregisterSerializer(key);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.opendaylight.openflow.extension.nicira.api.
     * NiciraExtensionCodecRegistrator
     * #registerActionDeserializer(org.opendaylight
     * .openflow.extension.nicira.api.NiciraActionDeserializerKey,
     * org.opendaylight.openflowjava.protocol.api.extensibility.OFDeserializer)
     */
    @Override
    public void registerActionDeserializer(SmsActionDeserializerKey key, OFDeserializer<Action> deserializer) {
        actionDeserializers.put(key, deserializer);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.opendaylight.openflow.extension.nicira.api.
     * NiciraExtensionCodecRegistrator
     * #unregisterActionDeserializer(org.opendaylight
     * .openflow.extension.nicira.api.NiciraActionDeserializerKey)
     */
    @Override
    public void unregisterActionDeserializer(SmsActionDeserializerKey key) {
        actionDeserializers.remove(key);
    }

    static OFDeserializer<Action> getActionDeserializer(SmsActionDeserializerKey key) {
        return actionDeserializers.get(key);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.opendaylight.openflow.extension.nicira.api.
     * NiciraExtensionCodecRegistrator
     * #registerActionSerializer(org.opendaylight.
     * openflow.extension.nicira.api.NiciraActionSerializerKey,
     * org.opendaylight.openflowjava.protocol.api.extensibility.OFSerializer)
     */
    @Override
    public void registerActionSerializer(SmsActionSerializerKey key, OFSerializer<Action> serializer) {
        registerActionSerializer(SmsUtil.createOfJavaKeyFrom(key), serializer);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.opendaylight.openflow.extension.nicira.api.
     * NiciraExtensionCodecRegistrator
     * #unregisterActionSerializer(org.opendaylight
     * .openflow.extension.nicira.api.NiciraActionSerializerKey)
     */
    @Override
    public void unregisterActionSerializer(SmsActionSerializerKey key) {
        unregisterSerializer(SmsUtil.createOfJavaKeyFrom(key));
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.opendaylight.openflow.extension.nicira.api.
     * NiciraExtensionCodecRegistrator
     * #registerMatchEntryDeserializer(org.opendaylight
     * .openflowjava.protocol.api.keys.MatchEntryDeserializerKey,
     * org.opendaylight.openflowjava.protocol.api.extensibility.OFDeserializer)
     */
    @Override
    public void registerMatchEntryDeserializer(MatchEntryDeserializerKey key, OFDeserializer<MatchEntry> deserializer) {
        for (SwitchConnectionProvider provider : providers) {
            provider.registerMatchEntryDeserializer(key, deserializer);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.opendaylight.openflow.extension.nicira.api.
     * NiciraExtensionCodecRegistrator
     * #unregisterMatchEntryDeserializer(org.opendaylight
     * .openflowjava.protocol.api.keys.MatchEntryDeserializerKey)
     */
    @Override
    public void unregisterMatchEntryDeserializer(MatchEntryDeserializerKey key) {
        unregisterDeserializer(key);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.opendaylight.openflow.extension.nicira.api.
     * NiciraExtensionCodecRegistrator
     * #registerMatchEntrySerializer(org.opendaylight
     * .openflowjava.protocol.api.keys.MatchEntrySerializerKey,
     * org.opendaylight.openflowjava.protocol.api.extensibility.OFSerializer)
     */
    @Override
    public void registerMatchEntrySerializer(MatchEntrySerializerKey<? extends OxmClassBase, ? extends MatchField> key,
            OFSerializer<MatchEntry> serializer) {
        for (SwitchConnectionProvider provider : providers) {
            provider.registerMatchEntrySerializer(key, serializer);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.opendaylight.openflow.extension.nicira.api.
     * NiciraExtensionCodecRegistrator
     * #unregisterMatchEntrySerializer(org.opendaylight
     * .openflowjava.protocol.api.keys.MatchEntrySerializerKey)
     */
    @Override
    public void unregisterMatchEntrySerializer(MatchEntrySerializerKey<? extends OxmClassBase, ? extends MatchField> key) {
        unregisterSerializer(key);
    }

    @Override
    public void close() throws Exception {
        // TODO Auto-generated method stub
        
    }

}
