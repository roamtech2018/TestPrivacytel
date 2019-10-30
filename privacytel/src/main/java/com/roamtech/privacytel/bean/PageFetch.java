package com.roamtech.privacytel.bean;

import java.io.Serializable;

public class PageFetch implements Serializable {
    private Long id;
    private Integer size;
    private Boolean greater;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Boolean getGreater() {
        return greater;
    }

    public void setGreater(Boolean greater) {
        this.greater = greater;
    }
}
