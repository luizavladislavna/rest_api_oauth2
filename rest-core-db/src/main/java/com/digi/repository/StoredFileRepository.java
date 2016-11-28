package com.digi.repository;

import com.digi.model.resource.StoredFile;
import org.springframework.stereotype.Repository;

/**
 * Created by tymoshenkol on 26-Aug-16.
 */
@Repository
public interface StoredFileRepository extends ResourcesSqlRepository<StoredFile, Integer> {
    StoredFile findByUuid(String uuid);

}
