package com.wossha.pictures.sqs.infrastructure.sqs;

import java.util.HashMap;

import com.wossha.json.events.events.api.EventSerializer;
import com.wossha.pictures.sqs.infrastructure.sqs.removePictureEvent.RemovePictureEventSerializer;
import com.wossha.pictures.sqs.infrastructure.sqs.savePictureEvent.SavePictureEventSerializer;

public class EventSerializers {

private final HashMap<String, EventSerializer> listeners = new HashMap<>();
    
    private SavePictureEventSerializer savePictureEventSerializer;
    private RemovePictureEventSerializer removePictureEventSerializer;

    public void initMapper() {
        listeners.put("SAVE-PICTURE", savePictureEventSerializer);
        listeners.put("REMOVE-PICTURE", removePictureEventSerializer);
    }


    public EventSerializer get(String eventName) {
        return listeners.get(eventName);
    }


	public void setSavePictureEventSerializer(SavePictureEventSerializer savePictureEventSerializer) {
		this.savePictureEventSerializer = savePictureEventSerializer;
	}


	public void setRemovePictureEventSerializer(RemovePictureEventSerializer removePictureEventSerializer) {
		this.removePictureEventSerializer = removePictureEventSerializer;
	}

}