package com.wossha.pictures.sqs.infrastructure.dao.picture;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.wossha.pictures.sqs.dto.PictureFileDTO;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PictureMapperJdbi implements ResultSetMapper<PictureFileDTO> {
	
    @Override
    public PictureFileDTO map(int index, ResultSet r, StatementContext ctx) throws SQLException {
		return new PictureFileDTO(
				r.getInt("ID"),
                r.getString("UUID"),
                r.getString("USERNAME"),
                r.getString("NAME"),
                r.getString("FILE_TYPE"),
                r.getString("TYPE"),
                r.getInt("FILE_SIZE"),
                blobToBase64(r.getBlob("VALUE"), r.getString("FILE_TYPE"))
        );
        
    }

	@SuppressWarnings("restriction")
	private byte[] blobToBase64(Blob blob, String fileType) {
		byte[] blobAsBytes = null;
		try {
			int blobLength;
			blobLength = (int) blob.length();
			blobAsBytes = blob.getBytes(1, blobLength);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}  
		
		return blobAsBytes;
	}
    

}