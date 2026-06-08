package io.trishul.ai.service.agent.model.repository;

import io.trishul.ai.agent.model.AiAgentConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import io.trishul.repo.jpa.repository.ExtendedRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

@Repository
public interface AiAgentConfigRepository extends JpaRepository<AiAgentConfig, Long>,
    JpaSpecificationExecutor<AiAgentConfig>, ExtendedRepository<Long> {
}
