package com.digi.web.service;

import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.transfer.model.UploadResult;
import com.digi.util.StoredFileData;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by tymoshenkol on 21-Nov-16.
 */
public interface FileService {
    UploadResult upload(MultipartFile f) throws InterruptedException, IOException;

    UploadResult upload(File f, String originalName, String contentType) throws InterruptedException;

    UploadResult upload() throws URISyntaxException, InterruptedException;

    ObjectListing show();

    StoredFileData load(String fileUUID);
}
