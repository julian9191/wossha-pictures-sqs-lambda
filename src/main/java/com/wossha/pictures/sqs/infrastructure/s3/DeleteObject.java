package com.wossha.pictures.sqs.infrastructure.s3;

import java.io.IOException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;

public class DeleteObject {

	private AmazonS3 s3client;
	private static final String S3_BUCKET = System.getenv("S3_BUCKET");

	public DeleteObject() {
		s3client = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
	}

	public void delete(String filename) throws IOException {
		
		try {
			s3client.deleteObject(new DeleteObjectRequest(S3_BUCKET, filename));

			System.out.println("picture deleted in s3: "+filename);
		} catch (AmazonServiceException e) {
			e.printStackTrace();
		} catch (SdkClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
