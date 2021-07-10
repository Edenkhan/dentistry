package com.youruan.dentistry.core.base.domain;

import java.io.Serializable;
import java.util.Date;

public abstract class BasicDomain implements Serializable {

    private static final long serialVersionUID = 1601015665542788732L;

    private Long id;
    private Date createdDate;
    private Date lastModifiedDate;
    private int version;

    public void setVersion(int version) {
        this.version = version;
    }

    public int getVersion() {
        return version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

}
