package com.pamc.api.util;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

public class IdListener {
    @PrePersist
    public void prePersist(IdEntity idEntity) {
        idEntity.setCreateTime(currentTime());
        idEntity.setLastUpdateTime(currentTime());
        idEntity.setCreateId(id());
        idEntity.setUpdateId(id());
    }
    @PreUpdate
    public void preUpdate(IdEntity idEntity) {
        idEntity.setUpdateId(id());
        idEntity.setLastUpdateTime(currentTime());
    }

    private Long id() {
        return  0L;
    }
    private Date  currentTime(){
        return new Date();
    }
}
