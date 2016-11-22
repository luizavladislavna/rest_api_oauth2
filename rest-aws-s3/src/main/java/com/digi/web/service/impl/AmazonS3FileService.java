package com.digi.web.service.impl;

import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.transfer.model.UploadResult;
import com.digi.model.resource.StoredFile;
import com.digi.repository.StoredFileRepository;
import com.digi.util.AmazonS3Template;
import com.digi.util.StoredFileData;
import com.digi.web.service.FileService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.UUID;


/**
 * Created by tymoshenkol on 21-Nov-16.
 */
@Service
@Slf4j
public class AmazonS3FileService implements FileService {

    @Autowired
    StoredFileRepository storedFileRepository;
    @Autowired
    private AmazonS3Template amazonS3;

    @Override
    public UploadResult upload(MultipartFile f) throws InterruptedException, IOException {

        File scratchFile = File.createTempFile(f.getName(), f.getOriginalFilename());
        try {
            FileUtils.copyInputStreamToFile(f.getInputStream(), scratchFile);
            return upload(scratchFile, f.getOriginalFilename(), f.getContentType());
        } finally {
            if (scratchFile.exists()) {
                scratchFile.delete();
            }
        }
    }

    public UploadResult upload() throws URISyntaxException, InterruptedException {
        File f = new File(this.getClass().getClassLoader()
                .getResource("seal.png").toURI());
        return upload(f, f.getName(), "image/png");
    }

    public UploadResult upload(File f, String originalName, String contentType) throws InterruptedException {
        UploadResult result = amazonS3.save(UUID.randomUUID().toString(), f);
        storedFileRepository.save(StoredFile.of(result.getKey(), originalName, contentType));
        return result;
    }

    @Override
    public ObjectListing show() {
        return amazonS3.getAll();
    }

    @Override
    public StoredFileData load(String fileUUID) {
        StoredFile stored = storedFileRepository.findByUuid(fileUUID);
        if (stored != null) {
            S3Object s3 = amazonS3.get(stored.getUuid());
            s3.getObjectContent();
            return new StoredFileData(stored, s3.getObjectContent());
        }
        return null;
    }


}
