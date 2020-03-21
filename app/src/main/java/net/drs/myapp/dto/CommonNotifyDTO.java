package net.drs.myapp.dto;

import java.sql.Date;

public abstract class CommonNotifyDTO {

    
    private Date creationDate;

    private String createdBy;
    
    private Date updatedDate;

    private String updatedBy;

    public Date getCreationDate() {
        return creationDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public CommonNotifyDTO() {
        super();
        this.creationDate = new Date(System.currentTimeMillis());
        this.createdBy = "SYSTEM";
        this.updatedDate = new Date(System.currentTimeMillis());
        this.updatedBy = "SYSTEM";
    }
    
    
    
}
