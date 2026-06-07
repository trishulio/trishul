package io.trishul.ai.service.tool.model.repository;

import io.trishul.ai.tool.model.AiTool;
import org.springframework.data.jpa.repository.JpaRepository;
import io.trishul.repo.jpa.repository.ExtendedRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AiToolRepository extends JpaRepository<AiTool, Long>,
    org.springframework.data.jpa.repository.JpaSpecificationExecutor<AiTool>,
    ExtendedRepository<Long> {
}
