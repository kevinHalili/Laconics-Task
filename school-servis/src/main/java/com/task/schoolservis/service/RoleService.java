package com.task.schoolservis.service;


import com.task.schoolservis.dto.role.RoleDto;
import com.task.schoolservis.dto.role.RoleSaveDto;

import java.util.List;

public interface RoleService {


    List<RoleDto> getAllRoles();

    RoleDto createRole(RoleSaveDto roleSaveDto);
}
