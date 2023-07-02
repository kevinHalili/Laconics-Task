package com.task.schoolservis.controller;

import com.task.schoolservis.dto.role.RoleDto;
import com.task.schoolservis.dto.role.RoleSaveDto;
import com.task.schoolservis.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/api/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<RoleDto> createRole(@Valid @RequestBody RoleSaveDto roleSaveDto) {
        return new ResponseEntity<>( roleService.createRole( roleSaveDto ), HttpStatus.CREATED );

    }

    @GetMapping
    public ResponseEntity<List<RoleDto>> getAllRoles() {
        return new ResponseEntity<>( roleService.getAllRoles(), HttpStatus.OK );

    }


}
