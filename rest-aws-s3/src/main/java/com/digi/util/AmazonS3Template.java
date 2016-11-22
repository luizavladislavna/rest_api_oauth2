package com.digi.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.amazonaws.services.s3.transfer.model.UploadResult;
import com.digi.config.AmazonS3Configuration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;

/**
 * Created by tymoshenkol on 21-Nov-16.
 */
@Component
@Slf4j
public class AmazonS3Template {

    @Autowired
    AmazonS3Configuration awsCfg;
    @Autowired
    private AmazonS3 amazonS3;

    /**
     * Save a file using authenticated session credentials
     *
     * @param key  is the name of the file to save in the bucket
     * @param file is the file that will be saved
     * @return an instance of {@link PutObjectResult} containing the result of the save operation
     */
    public UploadResult save(String key, File file) throws InterruptedException {
        TransferManager transferManager = new TransferManager(amazonS3);
        Upload upload = transferManager.upload(awsCfg.getS3DefaultBucket(), key, file);
        UploadResult result = upload.waitForUploadResult();
        log.debug("result: {}, {}, {}, {}", result.getBucketName(), result.getKey(), result.getVersionId(), result.getETag());
        return result;
    }

    public UploadResult save(String key, InputStream input, ObjectMetadata metadata) throws InterruptedException {
        TransferManager transferManager = new TransferManager(amazonS3);
        PutObjectRequest request = new PutObjectRequest(awsCfg.getS3DefaultBucket(), key, input, metadata);
        Upload upload = transferManager.upload(request);
        UploadResult result = upload.waitForUploadResult();
        log.debug("result: {}, {}, {}, {}", result.getBucketName(), result.getKey(), result.getVersionId(), result.getETag());
        return result;
    }

    /**
     * Get a file using the authenticated session credentials
     *
     * @param key is the key of the file in the bucket that should be retrieved
     * @return an instance of {@link S3Object} containing the file from S3
     */
    public S3Object get(String key) {
        return amazonS3.getObject(awsCfg.getS3DefaultBucket(), key);
    }

    public ObjectListing getAll() {
        return amazonS3.listObjects(awsCfg.getS3DefaultBucket());
    }
}
