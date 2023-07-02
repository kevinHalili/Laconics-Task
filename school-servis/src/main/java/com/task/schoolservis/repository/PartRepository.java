package com.task.schoolservis.repository;

import com.task.schoolservis.entity.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PartRepository extends JpaRepository<Part, Long>, JpaSpecificationExecutor<Part> {
}
