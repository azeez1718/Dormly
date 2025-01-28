package com.example.Dormly.aws;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;

@Service
@RequiredArgsConstructor
public class s3Service {

    //injecting our bean s3Client
    private final S3Client s3Client;


}
