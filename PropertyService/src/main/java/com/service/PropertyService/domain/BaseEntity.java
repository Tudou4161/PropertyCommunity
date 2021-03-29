package com.service.PropertyService.domain;
import lombok.Getter;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.Date;

@MappedSuperclass
@Getter
public class BaseEntity {

    @Column(updatable = false)
    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        createDate = now;
        updateDate = now;

    }

    @PreUpdate
    public void perUpdate() {
        updateDate = LocalDateTime.now();
    }

}

