package com.example.libraryservicemanager.repository;

import com.example.libraryservicemanager.model.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity,Long> {
    Optional<RoleEntity> findByNameIgnoreCase(String name);
}
