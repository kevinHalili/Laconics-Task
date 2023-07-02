package com.task.schoolservis.service.imp;


import com.task.schoolservis.dto.partDto.PartDto;
import com.task.schoolservis.dto.partDto.PartSaveDto;
import com.task.schoolservis.dto.partDto.PartUpdateDto;
import com.task.schoolservis.entity.Part;
import com.task.schoolservis.exception.NotFoundException;
import com.task.schoolservis.repository.PartRepository;
import com.task.schoolservis.service.PartService;
import com.task.schoolservis.util.MappingTool;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartServiceImp implements PartService {


    private final PartRepository partRepository;
    private final MappingTool mappingTool;

    public PartServiceImp(PartRepository partRepository, MappingTool mappingTool) {
        this.partRepository = partRepository;
        this.mappingTool = mappingTool;
    }


    protected Specification<Part> searchPart(String searchKeyword, List<String> fields) {
        Specification<Part> ps = ((root, query, criteriaBuilder) -> null);
        for (String field : fields) {
            ps.and( (root, query, criteriaBuilder) -> criteriaBuilder.like( root.get( field ), "%" + searchKeyword + "%" ) );
        }

        return ps;
    }

    @Override
    public PartDto addPart(PartSaveDto partSaveDto) {
        Part part = mappingTool.map( partSaveDto, Part.class );
        Part created = partRepository.save( part );
        return mappingTool.map( created, PartDto.class );
    }

    @Override
    public void deletePart(Long partId) {
        partRepository.deleteById( partId );
    }


    @Override
    public PartDto getPart(Long partId) {
        Part part = partRepository.findById( partId ).orElseThrow( () -> new NotFoundException( "Part", "Id", partId.toString() ) );
        return mappingTool.map( part, PartDto.class );
    }

    @Override
    public PartDto updatePart(Long partId, PartUpdateDto partUpdateDto) {
        Part part = partRepository.findById( partId ).orElseThrow( () -> new NotFoundException( "Part", "Id", partId.toString() ) );
        mappingTool.map( partUpdateDto, part );
        Part updated = partRepository.save( part );
        return mappingTool.map( updated, PartDto.class );
    }

    @Override
    public Page<PartDto> getAllParts(Pageable pageable, String searchKeyword) {
        Specification<Part> ps = ((root, query, criteriaBuilder) -> null);

        if (searchKeyword != null && !searchKeyword.isEmpty()) {
            ps = searchPart( searchKeyword, List.of( "manufacturer", "name" ) );
        }
        Page<Part> partPage = partRepository.findAll( ps, pageable );

        return mappingTool.map( partPage, PartDto.class );
    }
}
