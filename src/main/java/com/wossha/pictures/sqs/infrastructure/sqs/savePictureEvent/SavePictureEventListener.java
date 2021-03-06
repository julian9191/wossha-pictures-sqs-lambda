package com.wossha.pictures.sqs.infrastructure.sqs.savePictureEvent;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import com.google.inject.Inject;
import com.wossha.json.events.events.api.Event;
import com.wossha.json.events.events.api.EventProcessor;
import com.wossha.json.events.events.pictures.SavePictureEvent.PictureInfo;
import com.wossha.json.events.events.pictures.SavePictureEvent.SavePictureEvent;
import com.wossha.json.events.exceptions.RecoverableException;
import com.wossha.pictures.sqs.dto.PictureFileDTO;
import com.wossha.pictures.sqs.infrastructure.repositories.FileRepository;
import com.wossha.pictures.sqs.infrastructure.s3.DeleteObject;

public class SavePictureEventListener implements EventProcessor<SavePictureEvent>  {
    private SavePictureEvent data;

    @Inject
   	private DeleteObject deleteObject;
    
    @Inject
    private FileRepository repo;
    
    @Override
    public String name() {
        return "AsesoriaRecibidaBPM";
    }
    @Override
    public SavePictureEvent data() {
        return data;
    }
    @Override
    public void setData(SavePictureEvent data) {
        this.data = data;
    }
    @Override
    public List<Event> execute() throws RecoverableException{
        List<Event> events = new ArrayList<>();

        for (PictureInfo picture : data.getMessage().getPictures()) {
			
        	PictureFileDTO dto = new PictureFileDTO();
        	dto.setUuid(picture.getUuidPicture());
        	dto.setUsername(data.getUsername());
        	dto.setName(picture.getName());
        	dto.setFileType(picture.getFileType());
        	dto.setType(picture.getType());
        	dto.setFileSize(picture.getFileSize());
    		repo.add(dto);
		}
        
        List<String> uuids = data.getMessage().getPictures().stream().map(PictureInfo::getUuidPictureToRemove).collect(Collectors.toList());
        
        if(uuids != null && !uuids.isEmpty()) {
        	deleteInS3(uuids);
			repo.removeByUuids(uuids);
		}

        return events;
    }
    
    private void deleteInS3(List<String> uuids) {
		try {
			for (String uuid : uuids) {
				deleteObject.delete(uuid);
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
