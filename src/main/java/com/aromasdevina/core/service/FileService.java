package com.aromasdevina.core.service;

import com.aromasdevina.core.service.dto.FileCreateDTO;
import com.aromasdevina.core.service.dto.FileDTO;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Interface for managing {@link com.aromasdevina.core.domain.File}.
 */
public interface FileService {
    /**
     * Save a file.
     *
     * @param fileCreateDTO the entity to save.
     * @return the persisted entity.
     */
    FileDTO save(FileCreateDTO fileCreateDTO);

    /**
     * Get the "id" file.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FileDTO> findOne(UUID id);

    /**
     * Delete the "id" file.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}
