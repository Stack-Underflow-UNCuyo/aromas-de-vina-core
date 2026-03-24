package com.aromasdevina.core.service;

import com.aromasdevina.core.domain.*; // for static metamodels
import com.aromasdevina.core.domain.File;
import com.aromasdevina.core.repository.FileRepository;
import com.aromasdevina.core.service.criteria.FileCriteria;
import com.aromasdevina.core.service.dto.FileDTO;
import com.aromasdevina.core.service.mapper.FileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link File} entities in the database.
 * The main input is a {@link FileCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link FileDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FileQueryService extends QueryService<File> {

    private static final Logger LOG = LoggerFactory.getLogger(FileQueryService.class);

    private final FileRepository fileRepository;

    private final FileMapper fileMapper;

    public FileQueryService(FileRepository fileRepository, FileMapper fileMapper) {
        this.fileRepository = fileRepository;
        this.fileMapper = fileMapper;
    }

    /**
     * Return a {@link Page} of {@link FileDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FileDTO> findByCriteria(FileCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<File> specification = createSpecification(criteria);
        return fileRepository.findAll(specification, page).map(fileMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FileCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<File> specification = createSpecification(criteria);
        return fileRepository.count(specification);
    }

    /**
     * Function to convert {@link FileCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<File> createSpecification(FileCriteria criteria) {
        Specification<File> specification = Specification.unrestricted();
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : Specification.unrestricted(),
                buildSpecification(criteria.getId(), File_.id),
                buildSpecification(criteria.getVisibility(), File_.visibility)
            );
        }
        return specification;
    }
}
