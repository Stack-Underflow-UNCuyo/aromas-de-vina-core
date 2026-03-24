package com.aromasdevina.core.service.dto;

import com.aromasdevina.core.domain.FileVisibility;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

public class FileCreateDTO implements Serializable {

    @NotBlank
    private final String contentType;

    @NotNull
    private final FileVisibility visibility;

    public FileCreateDTO(String contentType, FileVisibility visibility) {
        this.contentType = contentType;
        this.visibility = visibility;
    }

    public String getContentType() {
        return contentType;
    }

    public FileVisibility getVisibility() {
        return visibility;
    }
}
