package com.example.Dormly.aws;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.awscore.presigner.PresignedRequest;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3Service {

    /**
     * Api requests being made to my Aws s3 storage provider
     */

    //injecting our bean s3Client
    private final S3Client s3Client;
    private final S3Presigner s3Presigner;


    public void putObject(String bucket, String key, byte[] file) {
        //allows us to store our file as an object within our bucket
        //the key represents the unique file name
        //and the file is the file that is stored in the object as a form of bytes
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();


        s3Client.putObject(objectRequest, RequestBody.fromBytes(file));


    }

    /**
     * download the File as a byte array from the bucket
     */

    public GetObjectRequest getObject(String bucket, String key) {
        return GetObjectRequest
                .builder()
                .bucket(bucket)
                .key(key)
                .build();

        //this is our object that we use retrieve the file by passing in our bucket and key
    }

    public URL generatePreSignedUrls(String bucket, String key) {
        /**
         * use Presigned urls which are generated from aws that allow users to view their images directly from our bucket
         * however the credentials to our bucket are hidden
         * instead of returning bytes from the image recieved from AWS..
         * every time a user makes a request we generate a signed url, this means everytime they view their profile we refresh the url
         * url expiration time is set to x hours
         * This function will be called in our profile service, as we set the key path from profile id and UUID
         */

        GetObjectRequest objectRequest = getObject(bucket, key);
        // you can change expiration time here
        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(3600)) //24 hours
                .getObjectRequest(objectRequest)
                .build();

        //the presigner bean, uses an implementation to generate a url for our object request
        //this returns us a presigned object based on our request which includes the actual file and the expiration
        PresignedGetObjectRequest presigned = s3Presigner.presignGetObject(presignRequest);
        return presigned.url();

    }

    public void DeleteObject(String bucket, String key) {
        try {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .build();
            s3Client.deleteObject(deleteObjectRequest);

        } catch (AwsServiceException awsServiceException) {
            log.info("Exception occurred while deleting object: {}", String.valueOf(awsServiceException));
        }

    }

}
