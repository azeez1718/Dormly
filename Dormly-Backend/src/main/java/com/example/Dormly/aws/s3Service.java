package com.example.Dormly.aws;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class s3Service {

    /**
     * Api requests being made to my Aws s3 storage provider
     */

    //injecting our bean s3Client
    private final S3Client s3Client;

    public void putObject(String bucket, String key, byte[] file){
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

    public byte[] getObject(String bucket, String key){
        GetObjectRequest objectRequest = GetObjectRequest
                .builder()
                .bucket(bucket)
                .key(key)
                .build();
        ResponseInputStream<GetObjectResponse> res = s3Client.getObject(objectRequest);
        try {
            //the return type of the function is ResponseInputStream<T>
            // it allows us to return the file of the image in bytes
            byte[] bytes  = res.readAllBytes();
            return bytes;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
