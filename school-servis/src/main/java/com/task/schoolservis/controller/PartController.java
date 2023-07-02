package com.task.schoolservis.controller;

import com.task.schoolservis.dto.partDto.PartDto;
import com.task.schoolservis.dto.partDto.PartSaveDto;
import com.task.schoolservis.dto.partDto.PartUpdateDto;
import com.task.schoolservis.service.PartService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/api/parts")
public class PartController {
    private final PartService partService;

    public PartController(PartService partService) {
        this.partService = partService;
    }

    @GetMapping("/all")
    public ResponseEntity<Page<PartDto>> getAllParts(Pageable pageable, String searchKeyword) {
        return new ResponseEntity<>( partService.getAllParts( pageable, searchKeyword ), HttpStatus.OK );
    }

    @PreAuthorize( "hasRole('ADMIN')" )
    @DeleteMapping("/{partId}")
    public ResponseEntity<Void> deletePart(@PathVariable Long partId) {
        partService.deletePart( partId );
        return ResponseEntity.ok().build();
    }

    @PostMapping("/new")
    public ResponseEntity<PartDto> addPart(@Valid @RequestBody PartSaveDto partSaveDto) {
        return new ResponseEntity<>( partService.addPart( partSaveDto ), HttpStatus.CREATED );
    }

    @GetMapping("/{partId}")
    public ResponseEntity<PartDto> getPart(@PathVariable Long partId) {
        return new ResponseEntity<>( partService.getPart( partId ), HttpStatus.OK );
    }

    @PutMapping("/{partId}")
    public ResponseEntity<PartDto> updatePart(@PathVariable Long partId, @Valid @RequestBody PartUpdateDto partUpdateDto) {
        return new ResponseEntity<>( partService.updatePart( partId, partUpdateDto ), HttpStatus.CREATED );
    }


}
