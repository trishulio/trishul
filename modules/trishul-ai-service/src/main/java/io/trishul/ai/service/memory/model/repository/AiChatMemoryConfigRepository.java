package io.trishul.ai.service.memory.model.repository;

import io.trishul.ai.memory.model.AiChatMemoryConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import io.trishul.repo.jpa.repository.ExtendedRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AiChatMemoryConfigRepository extends JpaRepository<AiChatMemoryConfig, Long>,
    org.springframework.data.jpa.repository.JpaSpecificationExecutor<AiChatMemoryConfig>,
    ExtendedRepository<Long> {
}
