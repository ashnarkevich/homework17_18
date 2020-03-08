package com.gmail.petrikov05.service.model;

import javax.validation.constraints.Size;

public class DocumentDTO {

    private Long id;
    @Size(max = 100, message = "maximum line length 100 characters")
    private String description;
    private String uuid;

    public DocumentDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

}
