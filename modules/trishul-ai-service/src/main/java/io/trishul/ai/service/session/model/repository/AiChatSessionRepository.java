package io.trishul.ai.service.session.model.repository;

import io.trishul.ai.session.model.AiChatSession;
import org.springframework.data.jpa.repository.JpaRepository;
import io.trishul.repo.jpa.repository.ExtendedRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AiChatSessionRepository extends JpaRepository<AiChatSession, Long>,
    org.springframework.data.jpa.repository.JpaSpecificationExecutor<AiChatSession>,
    ExtendedRepository<Long> {
}
