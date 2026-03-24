package com.aromasdevina.core.web.rest;

import com.aromasdevina.core.repository.FileRepository;
import com.aromasdevina.core.service.FileQueryService;
import com.aromasdevina.core.service.FileService;
import com.aromasdevina.core.service.criteria.FileCriteria;
import com.aromasdevina.core.service.dto.FileCreateDTO;
import com.aromasdevina.core.service.dto.FileDTO;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.aromasdevina.core.domain.File}.
 */
@RestController
@RequestMapping("/api/files")
public class FileResource {

    private static final Logger LOG = LoggerFactory.getLogger(FileResource.class);

    private static final String ENTITY_NAME = "file";

    @Value("${jhipster.clientApp.name:core}")
    private String applicationName;

    private final FileService fileService;

    private final FileQueryService fileQueryService;

    public FileResource(FileService fileService, FileRepository fileRepository, FileQueryService fileQueryService) {
        this.fileService = fileService;
        this.fileQueryService = fileQueryService;
    }

    /**
     * {@code POST  /files} : Create a new file.
     *
     * @param fileDTO the fileDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new fileDTO.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FileDTO> createFile(@RequestBody FileCreateDTO fileCreateDTO) throws URISyntaxException {
        LOG.debug("REST request to save File : {}", fileCreateDTO);
        FileDTO fileDTO = fileService.save(fileCreateDTO);
        return ResponseEntity.created(new URI("/api/files/" + fileDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, fileDTO.getId().toString()))
            .body(fileDTO);
    }

    /**
     * {@code GET  /files} : get all the Files.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of Files in body.
     */
    @GetMapping("")
    public ResponseEntity<List<FileDTO>> getAllFiles(
        FileCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get Files by criteria: {}", criteria);

        Page<FileDTO> page = fileQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /files/count} : count all the files.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
     *         in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countFiles(FileCriteria criteria) {
        LOG.debug("REST request to count Files by criteria: {}", criteria);
        return ResponseEntity.ok().body(fileQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /files/:id} : get the "id" file.
     *
     * @param id the id of the fileDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the fileDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FileDTO> getFile(@PathVariable("id") UUID id) {
        LOG.debug("REST request to get File : {}", id);
        Optional<FileDTO> fileDTO = fileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fileDTO);
    }

    /**
     * {@code DELETE  /files/:id} : delete the "id" file.
     *
     * @param id the id of the fileDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFile(@PathVariable("id") UUID id) {
        LOG.debug("REST request to delete File : {}", id);
        fileService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
