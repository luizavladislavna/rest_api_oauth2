package com.digi.model.resource;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by tymoshenkol on 22-Nov-16.
 */
@Entity
@Table(name = "s3_files")
@Getter @Setter @NoArgsConstructor
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class StoredFile extends IdEntity {
    @Basic(optional = false)
    @Column(name = "uuid", unique = true)
    @NonNull
    private String uuid;
    @Basic(optional = false)
    @Column(name = "filename")
    @NonNull
    private String filename;
    @Basic(optional = false)
    @Column(name = "type")
    @NonNull
    private String mimeType;
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    public static StoredFile of(String uuid, String filename, String type) {
        return new StoredFile(uuid, filename, type);
    }

    @PrePersist
    protected void onCreate() {
        created = new Date();
    }

}
