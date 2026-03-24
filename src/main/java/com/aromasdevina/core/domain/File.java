package com.aromasdevina.core.domain;

import jakarta.persistence.*;
import java.util.Objects;
import java.util.UUID;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SoftDelete;

/**
 * A File.
 */
@Entity
@Table(name = "file")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SoftDelete
@SuppressWarnings("common-java:DuplicatedBlocks")
public class File extends AbstractAuditingEntity<UUID> {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "visibility", nullable = false)
    @Enumerated(EnumType.STRING)
    private FileVisibility visibility;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public File id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public FileVisibility getVisibility() {
        return this.visibility;
    }

    public File visibility(FileVisibility visibility) {
        this.setVisibility(visibility);
        return this;
    }

    public void setVisibility(FileVisibility visibility) {
        this.visibility = visibility;
    }

    public String getKey() {
        return visibility == FileVisibility.PUBLIC ? "public/" + this.id.toString() : this.id.toString();
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
    // setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof File)) {
            return false;
        }
        return getId() != null && getId().equals(((File) o).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    // prettier-ignore
    @Override
  public String toString() {
    return "File{" +
        "id=" + getId() +
        "}";
  }
}
