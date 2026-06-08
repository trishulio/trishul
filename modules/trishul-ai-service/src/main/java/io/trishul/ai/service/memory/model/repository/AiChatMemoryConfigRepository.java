package io.trishul.ai.service.memory.model.repository;

import io.trishul.ai.memory.model.AiChatMemoryConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import io.trishul.repo.jpa.repository.ExtendedRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

@Repository
public interface AiChatMemoryConfigRepository extends JpaRepository<AiChatMemoryConfig, Long>,
    JpaSpecificationExecutor<AiChatMemoryConfig>, ExtendedRepository<Long> {
}
