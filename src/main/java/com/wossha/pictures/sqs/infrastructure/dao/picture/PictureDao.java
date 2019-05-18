package com.wossha.pictures.sqs.infrastructure.dao.picture;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.IDBI;
import org.skife.jdbi.v2.Update;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import com.wossha.pictures.sqs.dto.PictureFileDTO;
import com.wossha.pictures.sqs.infrastructure.dao.BaseDao;

public abstract  class PictureDao {
	
	//SELECTS--------------------------------------------------------------------------------------------------------------------------------------
	
	//INSERTS--------------------------------------------------------------------------------------------------------------------------------------
    
	@RegisterMapper(PictureMapperJdbi.class)
    @SqlUpdate("Insert into TWSS_PICTURES (UUID,USERNAME,NAME,FILE_TYPE,TYPE,FILE_SIZE,VALUE) values (:picture.uuid, :picture.username, :picture.name, :picture.fileType, :picture.type, :picture.fileSize, :picture.value)")
    public abstract void add(@BindBean("picture") PictureFileDTO picture);
	
	//REMOVES--------------------------------------------------------------------------------------------------------------------------------------
	@SqlUpdate("DELETE TWSS_PICTURES WHERE UUID =:uuid")
    public abstract void removeByUuid(@Bind("uuid") String uuid);
	
	public void removeByUuids(IDBI dbi, List<String> uuids) {

		BaseDao baseDao = new BaseDao<>();
		String query = "DELETE TWSS_PICTURES WHERE UUID IN (<uuids>) ";

		Map<String, List<String>> typesBindMap = new HashMap<>();
		typesBindMap.put("uuids", uuids);
		query = baseDao.generateBingIdentifier(query, typesBindMap);

		Handle h = dbi.open();	
		Update q = h.createStatement(query);

		q = baseDao.addInClauseBindUpdate(q, typesBindMap);
		q.execute();
	}
}