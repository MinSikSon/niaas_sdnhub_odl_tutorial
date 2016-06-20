package org.sms.vendor.extension;

import org.opendaylight.openflowjava.protocol.api.extensibility.OFDeserializer;
import org.opendaylight.openflowjava.protocol.api.keys.ExperimenterActionDeserializerKey;
import org.opendaylight.openflowjava.protocol.api.util.EncodeConstants;
//import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.list.Action;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.action.rev150203.actions.grouping.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;

public class ActionDeserializer implements OFDeserializer<Action>{
	private static final Logger LOG = LoggerFactory.getLogger(ActionDeserializer.class);

	public static final ExperimenterActionDeserializerKey OF13_DESERIALIZER_KEY = new ExperimenterActionDeserializerKey(
			EncodeConstants.OF13_VERSION_ID, SmsConstants.SMS_VENDOR_ID);
	public static final ExperimenterActionDeserializerKey OF10_DESERIALIZER_KEY = new ExperimenterActionDeserializerKey(
			EncodeConstants.OF10_VERSION_ID, SmsConstants.SMS_VENDOR_ID);

	private final short version;
//	private short version;

    /**
     * @param version protocol wire version
     */
    public ActionDeserializer(short version) {
        this.version = version;
    }

    @Override
	public Action deserialize(ByteBuf message) {
		int startPossition = message.readerIndex();
		// size of experimenter type
		message.skipBytes(EncodeConstants.SIZE_OF_SHORT_IN_BYTES);
		// size of length
		message.skipBytes(EncodeConstants.SIZE_OF_SHORT_IN_BYTES);
		long experimenterId = message.readUnsignedInt();
		if (SmsConstants.SMS_VENDOR_ID != experimenterId) {
			throw new IllegalStateException("Experimenter ID is not Sms vendor id but is " + experimenterId);
		}
		int subtype = message.readUnsignedShort();
		SmsActionDeserializerKey key = new SmsActionDeserializerKey(version, subtype);
		OFDeserializer<Action> actionDeserializer = SmsExtensionCodecRegistratorImpl.getActionDeserializer(key);
		if (actionDeserializer == null) {
			LOG.info("No deserializer was found for key {}", key);
			return null;
		}
		message.readerIndex(startPossition);
		return actionDeserializer.deserialize(message);
	}

}
