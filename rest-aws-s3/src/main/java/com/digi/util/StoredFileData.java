package com.digi.util;

import com.digi.model.resource.StoredFile;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.InputStream;

/**
 * Created by tymoshenkol on 22-Nov-16.
 */
@AllArgsConstructor
@Getter
public class StoredFileData {
    StoredFile data;
    InputStream inputStream;
}
