package com.example.libraryservicemanager.repository;

import com.example.libraryservicemanager.model.CredentialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CredentialRepository extends JpaRepository<CredentialEntity, Long> {

    Optional<CredentialEntity> getCredentialsByUserEntityId(Long id);
}
