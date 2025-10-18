package com.project.resumesystem.Repository;

import com.project.resumesystem.Model.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FileEntityRepository extends JpaRepository<FileEntity, UUID> {

}
