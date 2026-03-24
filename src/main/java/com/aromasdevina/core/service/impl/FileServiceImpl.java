package com.aromasdevina.core.service.impl;

import com.aromasdevina.core.domain.File;
import com.aromasdevina.core.domain.enumeration.FileVisibility;
import com.aromasdevina.core.repository.FileRepository;
import com.aromasdevina.core.service.FileService;
import com.aromasdevina.core.service.StorageService;
import com.aromasdevina.core.service.dto.FileCreateDTO;
import com.aromasdevina.core.service.dto.FileDTO;
import com.aromasdevina.core.service.mapper.FileMapper;
import java.time.Duration;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing
 * {@link com.aromasdevina.core.domain.File}.
 */
@Service
@Transactional
public class FileServiceImpl implements FileService {

    private static final Logger LOG = LoggerFactory.getLogger(FileServiceImpl.class);

    private final FileRepository fileRepository;

    private final FileMapper fileMapper;

    private final StorageService storageService;

    private static final Duration PRESIGNED_URL_EXPIRY = Duration.ofMinutes(15);

    public FileServiceImpl(FileRepository fileRepository, FileMapper fileMapper, StorageService storageService) {
        this.fileRepository = fileRepository;
        this.fileMapper = fileMapper;
        this.storageService = storageService;
    }

    @Override
    public FileDTO save(FileCreateDTO fileCreateDTO) {
        LOG.debug("Request to save File : {}", fileCreateDTO);
        File file = fileRepository.save(fileMapper.toEntity(fileCreateDTO));
        FileDTO dto = fileMapper.toDto(file);
        dto.setUrl(storageService.presignPut(file.getKey(), fileCreateDTO.getContentType(), PRESIGNED_URL_EXPIRY));
        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FileDTO> findOne(UUID id) {
        LOG.debug("Request to get File : {}", id);
        return fileRepository
            .findById(id)
            .map(file -> {
                FileDTO dto = fileMapper.toDto(file);
                if (file.getVisibility() == FileVisibility.PUBLIC) {
                    dto.setUrl(storageService.getPublicUrl(file.getKey()));
                } else {
                    dto.setUrl(storageService.presignGet(file.getKey(), PRESIGNED_URL_EXPIRY));
                }
                return dto;
            });
    }

    @Override
    public void delete(UUID id) {
        LOG.debug("Request to delete File : {}", id);
        fileRepository.deleteById(id);
    }
}
