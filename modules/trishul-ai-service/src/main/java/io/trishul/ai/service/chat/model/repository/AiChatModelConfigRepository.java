package io.trishul.ai.service.chat.model.repository;

import io.trishul.ai.chat.model.AiChatModelConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import io.trishul.repo.jpa.repository.ExtendedRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

@Repository
public interface AiChatModelConfigRepository extends JpaRepository<AiChatModelConfig, Long>,
    JpaSpecificationExecutor<AiChatModelConfig>, ExtendedRepository<Long> {
}
