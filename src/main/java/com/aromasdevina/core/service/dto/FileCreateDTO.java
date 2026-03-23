package com.aromasdevina.core.service.dto;

import java.io.Serializable;

public class FileCreateDTO implements Serializable {

    private final String contentType;

    public FileCreateDTO(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }
}
