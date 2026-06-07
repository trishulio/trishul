package io.trishul.ai.service.agent.model.repository;

import io.trishul.ai.agent.model.AiAgentConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import io.trishul.repo.jpa.repository.ExtendedRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AiAgentConfigRepository extends JpaRepository<AiAgentConfig, Long>,
    org.springframework.data.jpa.repository.JpaSpecificationExecutor<AiAgentConfig>,
    ExtendedRepository<Long> {
}
