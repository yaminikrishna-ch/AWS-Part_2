package com.S3Download;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
@Service
public class s3_Listener {
	 private static final Logger LOGGER = LoggerFactory.getLogger(s3_Listener.class);
	@Autowired
	@Value("${cloud.aws.region.static}")
	private String region;
	
	@Value("${cloud.aws.credentials.endpointUrl}")
	private String endpointUrl;
	
	@Value("${cloud.aws.credentials.BucketName}")
	private String bucketName = "adp1234";
	
	String key;
	public void getKey(String key) {
		this.key = key;
	}
	public void Download() {
	 AWSCredentials credentials = null;
     try {
         credentials = new ProfileCredentialsProvider("default").getCredentials();
     } catch (Exception e) {
         throw new AmazonClientException(
                 "Cannot load the credentials from the credential profiles file. " +
                 "Please make sure that your credentials file is at the correct " +
                 "location (C:\\Users\\HP\\.aws\\credentials), and is in valid format.",
                 e);
     }

     AmazonS3 s3 = AmazonS3ClientBuilder.standard()
         .withCredentials(new AWSStaticCredentialsProvider(credentials))
         .withRegion("us-west-1")
         .build();

	    System.out.println("Downloading an object");
        S3Object object = s3.getObject(new GetObjectRequest(bucketName, key));
        System.out.println("Content-Type: "  + object.getObjectMetadata().getContentType());
  }
}