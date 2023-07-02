package com.task.schoolservis.service.imp;


import com.task.schoolservis.dto.role.RoleDto;
import com.task.schoolservis.dto.role.RoleSaveDto;
import com.task.schoolservis.entity.Role;
import com.task.schoolservis.repository.RoleRepository;
import com.task.schoolservis.service.RoleService;
import com.task.schoolservis.util.MappingTool;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImp implements RoleService {


    private final RoleRepository roleRepository;
    private final MappingTool mapper;

    public RoleServiceImp(RoleRepository roleRepository, MappingTool mapper) {
        this.roleRepository = roleRepository;
        this.mapper = mapper;
    }


    @Override
    public List<RoleDto> getAllRoles() {
        return mapper.map( roleRepository.findAll(), RoleDto.class );
    }

    @Override
    public RoleDto createRole(RoleSaveDto roleSaveDto) {
        Role role = mapper.map( roleSaveDto, Role.class );
        Role saved = roleRepository.save( role );
        return mapper.map( saved, RoleDto.class );
    }
}
