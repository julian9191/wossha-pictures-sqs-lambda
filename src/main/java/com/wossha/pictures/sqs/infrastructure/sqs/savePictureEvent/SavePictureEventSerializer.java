package com.wossha.pictures.sqs.infrastructure.sqs.savePictureEvent;

import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.wossha.json.events.events.api.EventProcessor;
import com.wossha.json.events.events.api.EventSerializer;
import com.wossha.json.events.events.pictures.SavePictureEvent.SavePictureEvent;
import com.wossha.pictures.sqs.infrastructure.PicturesModule;

public class SavePictureEventSerializer implements EventSerializer {
	
	private Injector i = Guice.createInjector(new PicturesModule());
    private final ObjectMapper m = new ObjectMapper();
    
	private SavePictureEventListener eventListener;

    @SuppressWarnings("rawtypes")
	@Override
    public EventProcessor deserialize(String json) throws IOException {
    	SavePictureEvent event = m.readValue(json, SavePictureEvent.class);
    	eventListener = this.i.getInstance(SavePictureEventListener.class);
    	eventListener.setData(event);
        return eventListener;
    }
	
}
