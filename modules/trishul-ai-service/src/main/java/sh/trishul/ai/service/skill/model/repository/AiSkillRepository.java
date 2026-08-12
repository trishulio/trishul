package sh.trishul.ai.service.skill.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sh.trishul.ai.skill.model.AiSkill;
import sh.trishul.repo.jpa.repository.ExtendedRepository;

@Repository
public interface AiSkillRepository extends JpaRepository<AiSkill, Long>,
    JpaSpecificationExecutor<AiSkill>, ExtendedRepository<Long> {
  @Override
  @Query("select count(a) > 0 from ai_skill a where a.id in (:ids)")
  boolean existsByIds(Iterable<Long> ids);

  @Override
  @Modifying
  @Query("delete from ai_skill a where a.id in (:ids)")
  int deleteByIds(Iterable<Long> ids);
}
