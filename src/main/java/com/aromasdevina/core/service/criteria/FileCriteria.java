package com.aromasdevina.core.service.criteria;

import com.aromasdevina.core.domain.enumeration.FileVisibility;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.aromasdevina.core.domain.File} entity. This class is used
 * in {@link com.aromasdevina.core.web.rest.FileResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /files?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FileCriteria implements Serializable, Criteria {

    /**
     * Class for filtering FileVisibility
     */
    public static class FileVisibilityFilter extends Filter<FileVisibility> {

        public FileVisibilityFilter() {}

        public FileVisibilityFilter(FileVisibilityFilter filter) {
            super(filter);
        }

        @Override
        public FileVisibilityFilter copy() {
            return new FileVisibilityFilter(this);
        }
    }

    @Serial
    private static final long serialVersionUID = 1L;

    private UUIDFilter id;

    private FileVisibilityFilter visibility;

    private Boolean distinct;

    public FileCriteria() {}

    public FileCriteria(FileCriteria other) {
        this.id = other.optionalId().map(UUIDFilter::copy).orElse(null);
        this.visibility = other.optionalVisibility().map(FileVisibilityFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public FileCriteria copy() {
        return new FileCriteria(this);
    }

    public UUIDFilter getId() {
        return id;
    }

    public Optional<UUIDFilter> optionalId() {
        return Optional.ofNullable(id);
    }

    public UUIDFilter id() {
        if (id == null) {
            setId(new UUIDFilter());
        }
        return id;
    }

    public void setId(UUIDFilter id) {
        this.id = id;
    }

    public FileVisibilityFilter getVisibility() {
        return visibility;
    }

    public Optional<FileVisibilityFilter> optionalVisibility() {
        return Optional.ofNullable(visibility);
    }

    public FileVisibilityFilter visibility() {
        if (visibility == null) {
            setVisibility(new FileVisibilityFilter());
        }
        return visibility;
    }

    public void setVisibility(FileVisibilityFilter visibility) {
        this.visibility = visibility;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public Optional<Boolean> optionalDistinct() {
        return Optional.ofNullable(distinct);
    }

    public Boolean distinct() {
        if (distinct == null) {
            setDistinct(true);
        }
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FileCriteria that = (FileCriteria) o;
        return Objects.equals(id, that.id) && Objects.equals(visibility, that.visibility) && Objects.equals(distinct, that.distinct);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, visibility, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FileCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalVisibility().map(f -> "visibility=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
