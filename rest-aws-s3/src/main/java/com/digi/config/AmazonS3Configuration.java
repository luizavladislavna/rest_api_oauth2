package com.digi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by tymoshenkol on 21-Nov-16.
 */
@Configuration
@ConfigurationProperties(prefix = "amazon")
@PropertySource(value = "classpath:application-aws.properties")
@Data
public class AmazonS3Configuration {

    private String accessKeyId;
    private String accessKeySecret;
    private String s3DefaultBucket;
    private String s3Region;


}
