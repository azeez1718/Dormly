package com.example.Dormly.aws;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;



@Configuration
public class S3Config {

    @Value("${aws.region}")
    private String region;

    /**
     * builder method allows us to return an instance of an impl of s3Client
     * we then can define the properties of the object
     * we would only be able to call the functions present in the s3Client
     * returns an bean of the s3client in which spring invokes at startup
     */

    @Bean
    public S3Client s3Client(){
        S3Client client = S3Client.builder()
                .region(Region.of(region))
                .build();

        return client;

    }
}
