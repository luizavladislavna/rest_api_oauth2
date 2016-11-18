package com.digi.model.resource;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by tymoshenkol on 18-Nov-16.
 */
@Entity
@Table(name = "logger")
@Data
@NoArgsConstructor
public class Log extends IdEntity {
    @Basic(optional = false)
    @Column(name = "run_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date run_time;
}
