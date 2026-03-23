package com.aromasdevina.core.service.dto;

import java.net.URI;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.aromasdevina.core.domain.File} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FileDTO extends AbstractAuditingDTO {

    private UUID id;

    private URI url;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public URI getUrl() {
        return url;
    }

    public void setUrl(URI url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FileDTO)) {
            return false;
        }

        FileDTO fileDTO = (FileDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, fileDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
  public String toString() {
    return "FileDTO{" +
        "id='" + getId() + "'" +
        "}";
  }
}
