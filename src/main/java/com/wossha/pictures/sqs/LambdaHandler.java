package com.wossha.pictures.sqs;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.lambda.runtime.events.SQSEvent.SQSMessage;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wossha.json.events.events.api.Event;
import com.wossha.json.events.events.api.EventProcessor;
import com.wossha.json.events.events.api.EventSerializer;
import com.wossha.json.events.exceptions.RecoverableException;
import com.wossha.pictures.sqs.infrastructure.sqs.EventSerializers;
import com.wossha.pictures.sqs.infrastructure.sqs.removePictureEvent.RemovePictureEventSerializer;
import com.wossha.pictures.sqs.infrastructure.sqs.savePictureEvent.SavePictureEventSerializer;

public class LambdaHandler implements RequestHandler<SQSEvent, Void> {

	private final static Logger logger = LoggerFactory.getLogger(LambdaHandler.class);;
	private EventSerializers eventSerializers;
	
	@Override
	public Void handleRequest(SQSEvent sqsEvent, Context context) {

		context.getLogger().log("Recevied Event: " + sqsEvent);

		for (SQSMessage msg : sqsEvent.getRecords()) {
			try {
				this.processMessage(msg.getBody());
			} catch (Exception e) {
				context.getLogger().log("Error: " + e + " Details: " + e.getMessage());
			}
		}
		return null;
	}

	public void processMessage(String json) {
		initBeans();
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(json);

			logger.debug(
					"New Event received: " + root.path("name").asText());

			EventSerializer es = eventSerializers.get(root.path("name").asText());
			if (es != null) {
				EventProcessor eventProcessor = es.deserialize(json);
				List<Event> events = eventProcessor.execute();
				// publishEvents(events, "jms/PROTEC.INCAPACIDAD.SOLICITUD");
			} else {
				logger.debug("Event received without listener " + json);
			}
		} catch (RecoverableException e) {
			logger.error("RecoverableException: " + json, e);
			// publishErrorEvent(json,"jms/Q.SUBS.APP.SOLICITUD_INCAPACIDAD");
		} catch (Exception e) {
			logger.error("Uncontrolled error", e);
		}

	}

	private void initBeans() {
		if(eventSerializers == null) {
			eventSerializers = new EventSerializers();
			eventSerializers.setSavePictureEventSerializer((new SavePictureEventSerializer()));
			eventSerializers.setRemovePictureEventSerializer((new RemovePictureEventSerializer()));
			eventSerializers.initMapper();
		}
	}

}
