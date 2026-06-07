package io.trishul.ai.service.chat.model.repository;

import io.trishul.ai.chat.model.AiChatModelConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import io.trishul.repo.jpa.repository.ExtendedRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AiChatModelConfigRepository extends JpaRepository<AiChatModelConfig, Long>,
    org.springframework.data.jpa.repository.JpaSpecificationExecutor<AiChatModelConfig>,
    ExtendedRepository<Long> {
}
