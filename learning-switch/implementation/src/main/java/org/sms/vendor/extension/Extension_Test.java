package org.sms.vendor.extension;

import org.opendaylight.openflowjava.protocol.api.connection.ConnectionConfiguration;
import org.opendaylight.openflowjava.protocol.api.connection.SwitchConnectionHandler;
import org.opendaylight.openflowjava.protocol.api.extensibility.OFDeserializer;
import org.opendaylight.openflowjava.protocol.api.extensibility.OFGeneralDeserializer;
import org.opendaylight.openflowjava.protocol.api.extensibility.OFGeneralSerializer;
import org.opendaylight.openflowjava.protocol.api.extensibility.OFSerializer;
import org.opendaylight.openflowjava.protocol.api.keys.ActionSerializerKey;
import org.opendaylight.openflowjava.protocol.api.keys.ExperimenterActionDeserializerKey;
import org.opendaylight.openflowjava.protocol.api.keys.ExperimenterDeserializerKey;
import org.opendaylight.openflowjava.protocol.api.keys.ExperimenterIdDeserializerKey;
import org.opendaylight.openflowjava.protocol.api.keys.ExperimenterIdSerializerKey;
import org.opendaylight.openflowjava.protocol.api.keys.ExperimenterInstructionDeserializerKey;
import org.opendaylight.openflowjava.protocol.api.keys.ExperimenterSerializerKey;
import org.opendaylight.openflowjava.protocol.api.keys.InstructionSerializerKey;
import org.opendaylight.openflowjava.protocol.api.keys.MatchEntryDeserializerKey;
import org.opendaylight.openflowjava.protocol.api.keys.MatchEntrySerializerKey;
import org.opendaylight.openflowjava.protocol.spi.connection.SwitchConnectionProvider;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.MatchField;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.OxmClassBase;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.protocol.rev130731.ErrorMessage;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.protocol.rev130731.ExperimenterInput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.protocol.rev130731.ExperimenterMessage;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.protocol.rev130731.MultipartReplyMessage;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.protocol.rev130731.meter.band.header.meter.band.MeterBandExperimenterCase;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.protocol.rev130731.multipart.request.multipart.request.body.MultipartRequestExperimenterCase;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.protocol.rev130731.queue.property.header.QueueProperty;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.protocol.rev130731.table.features.properties.grouping.TableFeatureProperties;

import com.google.common.util.concurrent.ListenableFuture;

public class Extension_Test implements SwitchConnectionProvider {

	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean unregisterSerializer(ExperimenterSerializerKey key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void registerActionSerializer(ActionSerializerKey<?> key, OFGeneralSerializer serializer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerInstructionSerializer(InstructionSerializerKey<?> key, OFGeneralSerializer serializer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <C extends OxmClassBase, F extends MatchField> void registerMatchEntrySerializer(
			MatchEntrySerializerKey<C, F> key, OFGeneralSerializer serializer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerExperimenterMessageSerializer(ExperimenterIdSerializerKey<ExperimenterInput> key,
			OFSerializer<ExperimenterInput> serializer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerMultipartRequestSerializer(ExperimenterIdSerializerKey<MultipartRequestExperimenterCase> key,
			OFSerializer<MultipartRequestExperimenterCase> serializer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerMultipartRequestTFSerializer(ExperimenterIdSerializerKey<TableFeatureProperties> key,
			OFGeneralSerializer serializer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerMeterBandSerializer(ExperimenterIdSerializerKey<MeterBandExperimenterCase> key,
			OFSerializer<MeterBandExperimenterCase> serializer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean unregisterDeserializer(ExperimenterDeserializerKey key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void registerActionDeserializer(ExperimenterActionDeserializerKey key, OFGeneralDeserializer deserializer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerInstructionDeserializer(ExperimenterInstructionDeserializerKey key,
			OFGeneralDeserializer deserializer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerMatchEntryDeserializer(MatchEntryDeserializerKey key, OFGeneralDeserializer deserializer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerErrorDeserializer(ExperimenterIdDeserializerKey key,
			OFDeserializer<ErrorMessage> deserializer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerExperimenterMessageDeserializer(ExperimenterIdDeserializerKey key,
			OFDeserializer<ExperimenterMessage> deserializer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerMultipartReplyMessageDeserializer(ExperimenterIdDeserializerKey key,
			OFDeserializer<MultipartReplyMessage> deserializer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerMultipartReplyTFDeserializer(ExperimenterIdDeserializerKey key,
			OFGeneralDeserializer deserializer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerMeterBandDeserializer(ExperimenterIdDeserializerKey key,
			OFDeserializer<MeterBandExperimenterCase> deserializer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerQueuePropertyDeserializer(ExperimenterIdDeserializerKey key,
			OFDeserializer<QueueProperty> deserializer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setConfiguration(ConnectionConfiguration configuration) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ListenableFuture<Boolean> startup() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListenableFuture<Boolean> shutdown() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSwitchConnectionHandler(SwitchConnectionHandler switchConHandler) {
		// TODO Auto-generated method stub
		
	}
	
}
