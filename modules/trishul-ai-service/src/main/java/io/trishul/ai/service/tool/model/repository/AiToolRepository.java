package io.trishul.ai.service.tool.model.repository;

import io.trishul.ai.tool.model.AiTool;
import org.springframework.data.jpa.repository.JpaRepository;
import io.trishul.repo.jpa.repository.ExtendedRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

@Repository
public interface AiToolRepository extends JpaRepository<AiTool, Long>,
    JpaSpecificationExecutor<AiTool>, ExtendedRepository<Long> {
}
