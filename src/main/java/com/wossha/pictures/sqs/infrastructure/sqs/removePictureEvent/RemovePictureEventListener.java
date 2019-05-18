package com.wossha.pictures.sqs.infrastructure.sqs.removePictureEvent;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.wossha.json.events.events.api.Event;
import com.wossha.json.events.events.api.EventProcessor;
import com.wossha.json.events.events.pictures.RemovePictureEvent.RemovePictureEvent;
import com.wossha.json.events.exceptions.RecoverableException;
import com.wossha.pictures.sqs.infrastructure.repositories.FileRepository;

public class RemovePictureEventListener implements EventProcessor<RemovePictureEvent>  {
    private RemovePictureEvent data;

    @Inject
    private FileRepository repo;
    
    @Override
    public String name() {
        return "AsesoriaRecibidaBPM";
    }
    @Override
    public RemovePictureEvent data() {
        return data;
    }
    @Override
    public void setData(RemovePictureEvent data) {
        this.data = data;
    }
    @Override
    public List<Event> execute() throws RecoverableException{
        List<Event> events = new ArrayList<>();
        
		repo.removeByUuids(data.getMessage().getUuidPictures());
		
        return events;
    }
}
