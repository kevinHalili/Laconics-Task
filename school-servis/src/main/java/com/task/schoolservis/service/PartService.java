package com.task.schoolservis.service;

import com.task.schoolservis.dto.partDto.PartDto;
import com.task.schoolservis.dto.partDto.PartSaveDto;
import com.task.schoolservis.dto.partDto.PartUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PartService {

    PartDto addPart(PartSaveDto partSaveDto);

    void deletePart(Long partId);

    PartDto updatePart(Long partId, PartUpdateDto partUpdateDto);

    Page<PartDto> getAllParts(Pageable pageable, String searchKeyword);

    PartDto getPart(Long partId);

}
