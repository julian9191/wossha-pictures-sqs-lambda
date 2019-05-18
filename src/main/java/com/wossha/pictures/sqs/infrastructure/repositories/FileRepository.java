package com.wossha.pictures.sqs.infrastructure.repositories;

import java.util.List;
import org.skife.jdbi.v2.IDBI;

import com.google.inject.Inject;
import com.wossha.pictures.sqs.dto.PictureFileDTO;
import com.wossha.pictures.sqs.infrastructure.dao.picture.PictureDao;

public class FileRepository implements Repository<PictureFileDTO> {

	@Inject
	private IDBI dbi;
	
	private PictureDao pictureDao;
	
	@Override
	public void add(PictureFileDTO pic) {
		pictureDao = dbi.onDemand(PictureDao.class);
		pictureDao.add(pic);
	}
	
	
	
    @Override
    public void update(PictureFileDTO clothe) {

    }

    @Override
    public void remove(PictureFileDTO clothe) {
    	
    }

	public void removeByUuid(String uuidPictureToRemove) {
		pictureDao = dbi.onDemand(PictureDao.class);
		pictureDao.removeByUuid(uuidPictureToRemove);
		
	}
	
	public void removeByUuids(List<String> uuidPictureToRemove) {
		pictureDao = dbi.onDemand(PictureDao.class);
		pictureDao.removeByUuids(dbi, uuidPictureToRemove);
		
	}

	
}
