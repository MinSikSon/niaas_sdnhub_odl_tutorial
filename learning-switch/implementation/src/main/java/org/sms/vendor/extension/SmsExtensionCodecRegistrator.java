package org.sms.vendor.extension;

import org.opendaylight.openflowjava.protocol.api.extensibility.OFDeserializer;
import org.opendaylight.openflowjava.protocol.api.extensibility.OFSerializer;
import org.opendaylight.openflowjava.protocol.api.keys.MatchEntryDeserializerKey;
import org.opendaylight.openflowjava.protocol.api.keys.MatchEntrySerializerKey;
//import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.list.Action;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.action.rev150203.actions.grouping.Action;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.MatchField;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.OxmClassBase;
//import org.opendaylight.openflowjava.nx.api.NiciraActionDeserializerKey;
//import org.opendaylight.openflowjava.nx.api.NiciraActionSerializerKey;
//import org.opendaylight.openflowjava.protocol.api.extensibility.OFDeserializer;
//import org.opendaylight.openflowjava.protocol.api.extensibility.OFSerializer;
//import org.opendaylight.openflowjava.protocol.api.keys.MatchEntryDeserializerKey;
//import org.opendaylight.openflowjava.protocol.api.keys.MatchEntrySerializerKey;
//import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.action.rev150203.actions.grouping.Action;
//import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.MatchField;
//import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.OxmClassBase;
//import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.match.entries.grouping.MatchEntry;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.match.entries.grouping.MatchEntry;

public interface SmsExtensionCodecRegistrator {
	void registerActionDeserializer(SmsActionDeserializerKey key, OFDeserializer<Action> deserializer);

    void unregisterActionDeserializer(SmsActionDeserializerKey key);

    void registerActionSerializer(SmsActionSerializerKey key, OFSerializer<Action> serializer);

    void unregisterActionSerializer(SmsActionSerializerKey key);

    void registerMatchEntryDeserializer(MatchEntryDeserializerKey key, OFDeserializer<MatchEntry> deserializer);

    void unregisterMatchEntryDeserializer(MatchEntryDeserializerKey key);

    void registerMatchEntrySerializer(MatchEntrySerializerKey<? extends OxmClassBase, ? extends MatchField> key,
            OFSerializer<MatchEntry> serializer);

    void unregisterMatchEntrySerializer(MatchEntrySerializerKey<? extends OxmClassBase, ? extends MatchField> key);
}
