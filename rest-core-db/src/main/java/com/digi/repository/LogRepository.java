package com.digi.repository;

import com.digi.model.resource.Log;
import org.springframework.stereotype.Repository;

/**
 * Created by tymoshenkol on 26-Aug-16.
 */
@Repository
public interface LogRepository extends ResourcesSqlRepository<Log, Integer> {

}
