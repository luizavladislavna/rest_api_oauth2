package com.digi.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.RegionUtils;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by tymoshenkol on 21-Nov-16.
 */
@Configuration
public class BeansConfiguration {
    @Autowired
    AmazonS3Configuration awsCfg;
    @Bean
    public AmazonS3 getAmazonS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(awsCfg.getAccessKeyId(), awsCfg.getAccessKeySecret());
        AmazonS3Client client = new AmazonS3Client(credentials);
        client.setRegion(RegionUtils.getRegion(awsCfg.getS3Region()));

        return client;
    }
}
