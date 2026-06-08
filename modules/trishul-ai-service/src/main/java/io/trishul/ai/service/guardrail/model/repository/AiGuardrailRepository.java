package io.trishul.ai.service.guardrail.model.repository;

import io.trishul.ai.guardrail.model.AiGuardrail;
import org.springframework.data.jpa.repository.JpaRepository;
import io.trishul.repo.jpa.repository.ExtendedRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

@Repository
public interface AiGuardrailRepository extends JpaRepository<AiGuardrail, Long>,
    JpaSpecificationExecutor<AiGuardrail>, ExtendedRepository<Long> {
}
