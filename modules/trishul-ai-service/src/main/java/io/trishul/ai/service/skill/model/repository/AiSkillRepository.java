package io.trishul.ai.service.skill.model.repository;

import io.trishul.ai.skill.model.AiSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import io.trishul.repo.jpa.repository.ExtendedRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

@Repository
public interface AiSkillRepository extends JpaRepository<AiSkill, Long>,
    JpaSpecificationExecutor<AiSkill>, ExtendedRepository<Long> {
}
