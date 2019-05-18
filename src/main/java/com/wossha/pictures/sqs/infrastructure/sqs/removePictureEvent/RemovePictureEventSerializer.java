package com.wossha.pictures.sqs.infrastructure.sqs.removePictureEvent;

import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.wossha.json.events.events.api.EventProcessor;
import com.wossha.json.events.events.api.EventSerializer;
import com.wossha.json.events.events.pictures.RemovePictureEvent.RemovePictureEvent;
import com.wossha.pictures.sqs.infrastructure.PicturesModule;

public class RemovePictureEventSerializer implements EventSerializer {
	
	private Injector i = Guice.createInjector(new PicturesModule());
    private final ObjectMapper m = new ObjectMapper();
    
	private RemovePictureEventListener eventListener;

    @SuppressWarnings("rawtypes")
	@Override
    public EventProcessor deserialize(String json) throws IOException {
    	RemovePictureEvent event = m.readValue(json, RemovePictureEvent.class);
    	eventListener = this.i.getInstance(RemovePictureEventListener.class);
    	eventListener.setData(event);
        return eventListener;
    }
	
}
