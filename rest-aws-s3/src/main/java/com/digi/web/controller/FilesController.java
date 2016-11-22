package com.digi.web.controller;


import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.transfer.model.UploadResult;
import com.amazonaws.services.waf.model.HTTPHeader;
import com.digi.util.StoredFileData;
import com.digi.web.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;

@Controller
public class FilesController {

    @Autowired
    FileService service;

    @RequestMapping(method = RequestMethod.GET, value = "/files/tmp")
    @ResponseBody
    public UploadResult uploadTemp() throws URISyntaxException, InterruptedException {
        return service.upload();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/files/all")
    @ResponseBody
    public ObjectListing showAll() throws URISyntaxException, InterruptedException {
        return service.show();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/files/upload")
    @ResponseBody
    public UploadResult upload(@RequestParam("file") MultipartFile file) throws IOException, InterruptedException {
        return service.upload(file);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/files/load/{fileUUID}")
    @ResponseBody
    public ResponseEntity<InputStreamResource> load( @PathVariable String fileUUID) throws IOException, InterruptedException {
        StoredFileData storedFile = service.load(fileUUID);

        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentType(MediaType.parseMediaType(storedFile.getData().getMimeType()));
        respHeaders.setContentDispositionFormData( "attachment", storedFile.getData().getFilename());

        return new ResponseEntity<>(new InputStreamResource(storedFile.getInputStream()), respHeaders, HttpStatus.OK);
    }
}