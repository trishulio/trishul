package io.trishul.ai.service.skill.model.repository;

import io.trishul.ai.skill.model.AiSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import io.trishul.repo.jpa.repository.ExtendedRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AiSkillRepository extends JpaRepository<AiSkill, Long>,
    org.springframework.data.jpa.repository.JpaSpecificationExecutor<AiSkill>,
    ExtendedRepository<Long> {
}
