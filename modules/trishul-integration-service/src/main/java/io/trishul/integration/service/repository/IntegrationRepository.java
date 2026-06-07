package io.trishul.integration.service.repository;

import io.trishul.integration.model.Integration;
import io.trishul.repo.jpa.repository.ExtendedRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IntegrationRepository extends JpaRepository<Integration, Long>,
    JpaSpecificationExecutor<Integration>, ExtendedRepository<Long> {
}
