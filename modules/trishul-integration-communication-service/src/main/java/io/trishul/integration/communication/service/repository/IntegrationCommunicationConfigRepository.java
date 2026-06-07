package io.trishul.integration.communication.service.repository;

import io.trishul.integration.communication.model.IntegrationCommunicationConfig;
import io.trishul.repo.jpa.repository.ExtendedRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IntegrationCommunicationConfigRepository
    extends JpaRepository<IntegrationCommunicationConfig, Long>,
    JpaSpecificationExecutor<IntegrationCommunicationConfig>, ExtendedRepository<Long> {
}
