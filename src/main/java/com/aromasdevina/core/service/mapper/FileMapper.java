package com.aromasdevina.core.service.mapper;

import com.aromasdevina.core.domain.File;
import com.aromasdevina.core.service.dto.FileDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link File} and its DTO {@link FileDTO}.
 */
@Mapper(componentModel = "spring")
public interface FileMapper extends EntityMapper<FileDTO, File> {}
