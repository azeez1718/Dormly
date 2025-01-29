package com.example.Dormly.aws;



import io.micrometer.common.lang.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;


@Configuration
public class S3Config {

    @Value("${aws.region}")
    private String region;

    @Value("${aws.access.key}")
    private String ACCESS_KEY;

    @Value("${aws.secret.key}")
    private String SECRET_KEY;

    /**
     * builder method allows us to return an instance of an impl of s3Client
     * we then can define the properties of the object
     * we would only be able to call the functions present in the s3Client
     * returns an bean of the s3client in which spring invokes at startup
     */

    @Bean
    public S3Client s3Client() {
        //credentials are required because the user will access the bucket based of our credentials
        //this is for the user accessing the s3 directly when we give presigned url
        AwsBasicCredentials credentials = AwsBasicCredentials.create(ACCESS_KEY, SECRET_KEY);
        S3Client client = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();

        return client;

    }

    @Bean
    public S3Presigner s3Presigner() {
        //essentially it will use this presigner implementation we return to create a presignrequest for our object

        Region awsRegion = Region.of(region);
        return S3Presigner.builder()
                .region(awsRegion)
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(ACCESS_KEY, SECRET_KEY)))
                .build();


    }

}
